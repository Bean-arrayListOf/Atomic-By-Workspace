package org.myrtle.citrus.db

import org.myrtle.citrus.db.MapDB.Pojo.SliceDataView
import java.io.File
import java.nio.charset.Charset

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class MapDBHyper : AutoCloseable {
	private var dbName: String = "main"
	private val db: MapDBSQLite
	constructor(file: String){
		db = MapDBSQLite(file)
	}
	constructor(file: File){
		db = MapDBSQLite(file)
	}

	fun contains(key: String): Boolean{
		return db.contains(dbName,key)
	}

	fun put(key: String, value: String){
		db.put(dbName,key,value)
	}

	fun put(key: String, value: ByteArray){
		db.put(dbName,key,value)
	}

	fun remove(key: String){
		db.remove(dbName,key)
	}

	fun get(key: String): String?{
		return db.get(dbName,key)?.getDataFormat(Charset.defaultCharset())
	}

	fun gets(key: String): List<String>{
		val set = db.gets(dbName,key)
		if(set.isEmpty()){
			return listOf()
		}
		val list = ArrayList<String>()
		for(i in set){
			list.add(i.getDataFormat(Charset.defaultCharset()))
		}
		return list
	}

	fun add(key: String, value: String){
		db.add(dbName,key,value)
	}

	fun add(key: String, value: ByteArray){
		db.add(dbName,key,value)
	}

	fun use(db: String){
		this.dbName = db
	}

	override fun close() {
		db.close()
	}

	fun map(db:String):Map<String, SliceDataView>{
		return DBMap(this.db,db)
	}

}