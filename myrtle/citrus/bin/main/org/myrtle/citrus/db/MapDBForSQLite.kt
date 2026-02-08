package org.myrtle.citrus.db

import org.myrtle.atomic.*
import org.myrtle.citrus.db.MapDB.Pojo.SliceDataView
import org.myrtle.citrus.util.MapDB
import java.io.File
import java.nio.charset.Charset

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class MapDBForSQLite : MapDB {

	private val connection: Connect

	constructor(file: String){
		this.connection = DManager.getConnection("jdbc:sqlite:${file}")
	}

	constructor(file: File){
		this.connection = DManager.getConnection("jdbc:sqlite:${file.absolutePath}")
	}

	override fun contains(db: String): Boolean {
		connection.prepareStatement("select count(*) from main.sqlite_master where tbl_name=? and type=?;").use { ps ->
			ps.setString(1,db)
			ps.setString(2,"table")
			ps.executeQuery().use { rs ->
				rs.next()
				return rs.getInt(1) != 0
			}
		}
	}

	override fun contains(db: String,key: String): Boolean{
		if (!contains(db)) {
			return false
		}

		connection.prepareStatement("select count(*) from \"${db}\" where \"key\"=?;").use { ps ->
			ps.setString(1,key)
			ps.executeQuery().use { rs ->
				rs.next()
				return rs.getInt(1) != 0
			}
		}
	}

	override fun put(db: String,map: Map<String,List<String>>){
		for (i in map){
			put(db,i.key,i.value)
		}
	}

	private fun updata(db:String,key: String,value: String){
		remove(db,key)
		put(db,key,value)
	}

	private fun nowDB(db: String){
		connection.prepareStatement("create table \"${db}\"(\"index\" integer primary key autoincrement ,\"key\" varchar(500) not null ,\"data\" blob,\"time\" timestamp default current_timestamp);").use {
			ps ->
			ps.executeUpdate()
		}
	}

	override fun put(db: String,key: String,values: List<String>){
		for (i in values) {
			add(db,key,i)
		}
	}

	private fun getView(db: String):List<SliceDataView>{
		connection.prepareStatement("select \"index\",\"key\",\"data\",\"time\" from \"${db}\";").use { ps ->
			ps.executeQuery().use { rs->
				val list = ArrayList<SliceDataView>()
				while (rs.next()) {
					list.add(SliceDataView(rs.getInt("index"),rs.getString("key"),rs.getBytes("data"),rs.getTimestamp("time").time))
				}
				return list
			}
		}
	}

	override fun getAll(db: String):List<SliceDataView>{
		return getView(db)
	}

	override fun keys(db: String):List<String>{
		val view = getView(db)
		val list = ArrayList<String>()
		for (i in view) {
			list.add(i.getKey())
		}
		return list
	}

	override fun put(db: String,key: String,value: String){
		if (contains(db,key)) {
			updata(db,key,value)
		}else {
			if (!contains(db)) {
				nowDB(db)
			}
			connection.prepareStatement("insert into \"${db}\"(\"key\", \"data\") values (?,?);").use { ps ->
				ps.setString(1,key)
				ps.setBytes(2,value.toByteArray(Charset.defaultCharset()))
				ps.executeUpdate()
			}
		}
	}

	override fun add(db: String,key: String,value: String){
		if (!contains(db)) {
			nowDB(db)
		}
		connection.prepareStatement("insert into \"${db}\"(\"key\", \"data\") values (?,?);").use { ps ->
			ps.setString(1,key)
			ps.setBytes(2,value.toByteArray(Charset.defaultCharset()))
			ps.executeUpdate()
		}
	}

	override fun remove(db:String,key: String){
		connection.prepareStatement("delete from \"${db}\" where \"key\"=?;").use { ps ->
			ps.setString(1,key)
			ps.executeUpdate()
		}
	}

	fun getView(db: String,key: String):List<SliceDataView>{
		connection.prepareStatement("select \"index\",\"key\",\"data\",\"time\" from \"${db}\" where \"key\"=?;").use { ps->
			ps.setString(1,key)
			ps.executeQuery().use { rs->
				val list = arrayListOf<SliceDataView>()
				while (rs.next()) {
					list.add(SliceDataView(rs.getInt("index"),rs.getString("key"),rs.getBytes("data"),rs.getTimestamp("time").time))
				}
				return list
			}
		}
	}

	override fun get(db: String,key: String): SliceDataView? {
		if (!contains(db,key)) {
			return null
		}

		return getView(db,key)[0]
	}

	override fun gets(db: String,key: String):List<SliceDataView>{
		return getView(db,key)
	}

	override fun close() {
		connection.close()
	}

}