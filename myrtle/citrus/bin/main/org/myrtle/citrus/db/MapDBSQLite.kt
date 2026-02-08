package org.myrtle.citrus.db

import org.myrtle.citrus.db.MapDB.Pojo.SliceDataView
import org.myrtle.citrus.db.MapDBSQliteSpace.Dao.MapDBSqliteDao
import org.myrtle.citrus.db.MapDBSQliteSpace.Impl.MapDBSqliteImpl
import java.io.File
import java.nio.charset.Charset

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class MapDBSQLite : AutoCloseable {

	private val mapper: MapDBSqliteDao

	constructor(file: String){
		this.mapper = MapDBSqliteImpl(File(file))
	}

	constructor(file: File){
		this.mapper = MapDBSqliteImpl(file)
	}

	fun contains(db: String): Boolean {
		val resultSet = mapper.findTableName(db,"table")
		return resultSet.size > 0
	}

	fun contains(db: String,key: String): Boolean{
		if (!contains(db)) {
			return false
		}

		val resultSet = mapper.selectKey(db, key)
		return resultSet.isNotEmpty()
	}

	fun put(db: String,map: kotlin.collections.Map<String,List<String>>){
		for (i in map){
			put(db,i.key,i.value)
		}
	}

	private fun updata(db:String,key: String,value: String){
		remove(db,key)
		put(db,key,value)
	}

	private fun updata(db:String,key: String,value: ByteArray){
		remove(db,key)
		put(db,key,value)
	}

	private fun nowDB(db: String){
		mapper.createTable(db)
	}

	fun put(db: String,key: String,values: List<String>){
		for (i in values) {
			add(db,key,i)
		}
	}

	private fun getView(db: String):List<SliceDataView>{
		return mapper.selectAll(db)
	}

	fun getAll(db: String):List<SliceDataView>{
		return getView(db)
	}

	fun keys(db: String):List<String>{
		val view = getView(db)
		val list = ArrayList<String>()
		for (i in view) {
			list.add(i.getKey())
		}
		return list
	}

	fun put(db: String,key: String,value: String){
		if (contains(db,key)) {
			updata(db,key,value)
		}else {
			if (!contains(db)) {
				nowDB(db)
			}
			mapper.insert(db,key,value.toByteArray(Charset.defaultCharset()))
		}
	}

	fun put(db: String,key: String,value: ByteArray){
		if (contains(db,key)) {
			updata(db,key,value)
		}else {
			if (!contains(db)) {
				nowDB(db)
			}
			mapper.insert(db,key,value)
		}
	}

	fun add(db: String,key: String,value: String){
		if (!contains(db)) {
			nowDB(db)
		}
		mapper.insert(db,key,value.toByteArray(Charset.defaultCharset()))
	}

	fun add(db: String,key: String,value: ByteArray){
		if (!contains(db)) {
			nowDB(db)
		}
		mapper.insert(db,key,value)
	}

	fun remove(db:String,key: String) {
		mapper.delete(db,key)
	}

	fun getView(db: String,key: String):List<SliceDataView>{
		return mapper.selectKey(db,key)
	}

	fun get(db: String,key: String): SliceDataView? {
		if (!contains(db,key)) {
			return null
		}

		return getView(db,key)[0]
	}

	fun gets(db: String,key: String):List<SliceDataView>{
		return getView(db,key)
	}

	override fun close() {
		mapper.close()
	}

}