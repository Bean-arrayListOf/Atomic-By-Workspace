package org.myrtle.citrus.db

import java.nio.charset.Charset
import java.sql.Connection
import java.sql.PreparedStatement
import java.util.*

class MapStoragePostgreSQL : MapStorage {

	private val connect: Connection

	constructor(connection: Connection) {
		this.connect = connection
		newIndex()
	}

	private fun newIndex(){
		connect.prepareStatement("create table if not exists \"map_index_table\"(\"index\" serial primary key,\"db\" varchar(500) not null ,\"key\" varchar(500) not null ,\"table\" varchar(500) not null unique ,\"time\" timestamp default current_timestamp);").use { ps ->
			ps.executeUpdate()
		}
	}

	private fun addIndex(db: String,key: String,dataTable: String){
		this.connect.prepareStatement("insert into \"map_index_table\"(\"db\", \"key\", \"table\") VALUES (?,?,?);").use { ps ->
			ps.setString(1, db)
			ps.setString(2, key)
			ps.setString(3, dataTable)
			ps.executeUpdate()
		}
	}

	private fun newRandomUUID(): String {
		return UUID.randomUUID().toString().uppercase(Locale.getDefault())
	}

	private fun nowRandomTable(db: String,key: String): PreparedStatement {
		val randomUUID = newRandomUUID()
		this.connect.prepareStatement("create table \"${randomUUID}\"(\"index\" serial primary key ,\"data\" bytea);").use { ps ->
			ps.executeUpdate()
		}
		addIndex(db, key, randomUUID)
		return this.connect.prepareStatement("insert into \"${randomUUID}\"(\"data\") values (?);")
	}

	override fun contains(db: String): Boolean {
		this.connect.prepareStatement("select count(*) from \"map_index_table\" where \"db\"=?;").use { ps ->
			ps.setString(1, db)
			ps.executeQuery().use { rs ->
				rs.next()
				return rs.getInt(1) != 0
			}
		}
	}

	override fun contains(db: String, key: String): Boolean {
		this.connect.prepareStatement("select count(*) from \"map_index_table\" where \"db\"=? and \"key\"=?;").use { ps ->
			ps.setString(1, db)
			ps.setString(2, key)
			ps.executeQuery().use { rs ->
				rs.next()
				return rs.getInt(1) != 0
			}
		}
	}

	private fun getDataTable(db: String,key: String): String{
		this.connect.prepareStatement("select \"table\" from \"map_index_table\" where \"db\"=? and \"key\"=?;").use { ps ->
			ps.setString(1, db)
			ps.setString(2, key)
			ps.executeQuery().use { rs ->
				rs.next()
				return rs.getString("table")
			}
		}
	}

	override fun remove(db: String, key: String) {
		val tableName = getDataTable(db,key)
		this.connect.prepareStatement("delete from \"map_index_table\" where \"db\"=? and \"key\"=?;").use { ps ->
			ps.setString(1, db)
			ps.setString(2, key)
			ps.executeUpdate()
		}
		this.connect.prepareStatement("drop table \"${tableName}\";").use { ps ->
			ps.executeUpdate()
		}
	}

	override fun get(db: String, key: String): ByteArray? {
		val table = getDataTable(db, key) ?: return null
		this.connect.prepareStatement("select * from \"${table}\";").use { ps ->
			ps.executeQuery().use { rs ->
				rs.next()
				return rs.getBytes(2)
			}
		}
	}

	override fun getString(db: String, key: String): String? {
		val table = getDataTable(db, key) ?: return null
		this.connect.prepareStatement("select * from \"${table}\";").use { ps ->
			ps.executeQuery().use { rs ->
				rs.next()
				val bytes =  rs.getBytes(2) ?: return null
				return String(bytes, Charset.defaultCharset())
			}
		}
	}

	override fun getStrings(db: String, key: String): List<String> {
		val list = ArrayList<String>()
		val table = getDataTable(db, key) ?: list
		this.connect.prepareStatement("select * from \"${table}\";").use { ps ->
			ps.executeQuery().use { rs ->
				while (rs.next()) {
					val bytes = rs.getBytes(2) ?: return list
					list.add(String(bytes,Charset.defaultCharset()))
				}
			}
		}
		return list
	}

	override fun gets(db: String, key: String): List<ByteArray> {
		val list = ArrayList<ByteArray>()
		val table = getDataTable(db, key) ?: list
		this.connect.prepareStatement("select * from \"${table}\";").use { ps ->
			ps.executeQuery().use { rs ->
				while (rs.next()) {
					list.add(rs.getBytes(2))
				}
			}
		}
		return list
	}

	override fun getKeys(db: String): List<String> {
		val list = ArrayList<String>()
		this.connect.prepareStatement("select \"key\" from \"map_index_table\" where \"db\"=?;").use { ps ->
			ps.setString(1, db)
			ps.executeQuery().use { rs ->
				while (rs.next()) {
					list.add(rs.getString(1))
				}
			}
		}
		return list
	}

	private fun update(db: String,key: String):PreparedStatement{
		remove(db,key)
		return nowRandomTable(db, key)
	}

	override fun put(db: String, key: String, value: String) {
		if (contains(db, key)){
			update(db, key)
		}else {
			nowRandomTable(db, key)
		}.use { ps ->
			ps.setBytes(1, value.toByteArray(Charset.defaultCharset()))
			ps.executeUpdate()
		}
	}

	override fun put(db: String, key: String, values: List<String>) {
		if (contains(db, key)){
			update(db, key)
		}else {
			nowRandomTable(db, key)
		}.use { ps ->
			for (value in values) {
				ps.setBytes(1, value.toByteArray(Charset.defaultCharset()))
				ps.executeUpdate()
			}
		}
	}

	override fun put(db: String, key: String, value: ByteArray) {
		if (contains(db, key)){
			update(db, key)
		}else {
			nowRandomTable(db, key)
		}.use { ps ->
			ps.setBytes(1, value)
			ps.executeUpdate()
		}
	}

	override fun add(db: String, key: String, value: String) {
		if (contains(db, key)){
			val dataTable = getDataTable(db, key)
			this.connect.prepareStatement("insert into \"${dataTable}\"(\"data\") values (?);").use { ps ->
				ps.setBytes(1, value.toByteArray(Charset.defaultCharset()))
				ps.executeUpdate()
			}
		}else {
			nowRandomTable(db, key).use { ps ->
				ps.setBytes(1, value.toByteArray(Charset.defaultCharset()))
				ps.executeUpdate()
			}
		}
	}

	override fun add(db: String, key: String, value: ByteArray) {
		if (contains(db, key)){
			val dataTable = getDataTable(db, key)
			this.connect.prepareStatement("insert into \"${dataTable}\" (\"data\") values (?);").use { ps ->
				ps.setBytes(1, value)
				ps.executeUpdate()
			}
		}else {
			nowRandomTable(db, key).use { ps ->
				ps.setBytes(1, value)
				ps.executeUpdate()
			}
		}
	}

	override fun puts(db: String, key: String, values: List<ByteArray>) {
		if (contains(db, key)){
			update(db, key)
		}else {
			nowRandomTable(db, key)
		}.use { ps ->
			for (value in values) {
				ps.setBytes(1, value)
				ps.executeUpdate()
			}
		}
	}

	override fun close() {
		this.connect.close()
	}
}