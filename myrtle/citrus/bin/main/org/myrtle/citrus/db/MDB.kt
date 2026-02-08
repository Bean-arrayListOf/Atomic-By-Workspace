package org.myrtle.citrus.db

import org.myrtle.atomic.FOStream
import org.myrtle.atomic.IStream
import java.io.File
import java.lang.AutoCloseable
import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import java.util.Locale.getDefault

/**
 * @author cat
 * @since 2025-08-11
 **/
class MDB : AutoCloseable{
	private val connect: Connection

	init {
		Class.forName("org.sqlite.JDBC")
	}

	constructor(dbFile: String){
		connect = DriverManager.getConnection("jdbc:sqlite:$dbFile")
	}

	constructor(dbFile: File){
		connect = DriverManager.getConnection("jdbc:sqlite:${dbFile.absolutePath}")
	}

	constructor(db: IStream){
		val tempDBFile = File.createTempFile(UUID.randomUUID().toString().uppercase(getDefault()),".tmp.dat")
		FOStream(tempDBFile).use { fos ->
			db.transferTo(fos)
		}
		connect = DriverManager.getConnection("jdbc:sqlite:${tempDBFile.absolutePath}")
	}

	constructor(dataSource: javax.sql.DataSource){
		connect = dataSource.connection
	}

	fun <K,V> openOrCreate(mapName: String, readOnly: Boolean = false): HMap<K, V> {
		return HMap(connect, mapName, readOnly)
	}

	fun <K,V> open(mapName: String): HMap<K,V>? {
		if (containsMap(mapName)) {
			return HMap(connect, mapName,true)
		}
		return null
	}

	fun containsMap(mapName: String): Boolean{
		connect.prepareStatement("select count(*) from \"sqlite_master\" where type=? and tbl_name=?;").use { pstat ->
			pstat.setString(1,"table")
			pstat.setString(2,mapName)
			 pstat.executeQuery().use { rs ->
				 return rs.next() && rs.getInt(1) > 0
			 }
		}
	}

	override fun close() {
		connect.close()
	}
}