package org.myrtle.citrus.db.MapDBSQliteSpace.Impl

import org.myrtle.citrus.db.MapDB.Pojo.SliceDataView
import org.myrtle.citrus.db.MapDB.Pojo.sqlite_master
import org.myrtle.citrus.db.MapDBSQliteSpace.Dao.MapDBSqliteDao
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class MapDBSqliteImpl : MapDBSqliteDao {

	private val connection: Connection

	constructor(file: File) {
		this.connection = DriverManager.getConnection("jdbc:sqlite:${file.absolutePath}")
	}

	override fun findTableName(tableName: String,type: String): List<sqlite_master> {
		connection.prepareStatement("select type,name,tbl_name,rootpage,sql from main.sqlite_master where tbl_name=? and type=?;").use { ps ->
			ps.setString(1,tableName)
			ps.setString(2,type)
			ps.executeQuery().use { rs ->
				val list = ArrayList<sqlite_master>()
				while (rs.next()) {
					list.add(sqlite_master(rs.getString("type"),rs.getString("name"),rs.getString("tbl_name"),rs.getInt("rootpage"),rs.getString("sql")))
				}
				return list
			}
		}
	}

	override fun createTable(tableName: String):Int{
		connection.prepareStatement("create table \"${tableName}\"(\"index\" integer primary key autoincrement ,\"key\" varchar(500) not null ,\"data\" blob,\"time\" timestamp default current_timestamp);").use { ps ->
			return ps.executeUpdate()
		}
	}

	override fun insert(tableName: String,key: String,value:ByteArray):Int{
		connection.prepareStatement("insert into \"${tableName}\"(\"key\", \"data\") values (?,?);").use { ps ->
			ps.setString(1,key)
			ps.setBytes(2,value)
			return ps.executeUpdate()
		}
	}

	override fun delete(tableName: String,key: String):Int{
		connection.prepareStatement("delete from \"${tableName}\" where \"key\"=?;").use { ps ->
			ps.setString(1,key)
			return ps.executeUpdate()
		}
	}

	override fun selectAll(tableName: String): List<SliceDataView> {
		connection.prepareStatement("select \"index\",\"key\",\"data\",\"time\" from \"${tableName}\";").use { ps ->
			return resultSetToSliceDataView(ps.executeQuery())
		}
	}

	override fun selectKey(tableName: String,key: String):List<SliceDataView>{
		connection.prepareStatement("select \"index\",\"key\",\"data\",\"time\" from \"${tableName}\" where \"key\"=?;").use { ps ->
			ps.setString(1,key)
			return resultSetToSliceDataView(ps.executeQuery())
		}
	}

	fun resultSetToSliceDataView(rs: ResultSet):List<SliceDataView>{
		val list = ArrayList<SliceDataView>()
		while (rs.next()) {
			list.add(SliceDataView(rs.getInt("index"),rs.getString("key"),rs.getBytes("data"),rs.getTimestamp("time").time))
		}
		return list
	}

	override fun close() {
		this.connection.close()
	}


}