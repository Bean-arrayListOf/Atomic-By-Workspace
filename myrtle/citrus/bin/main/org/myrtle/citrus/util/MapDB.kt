package org.myrtle.citrus.util

import org.myrtle.citrus.db.MapDB.Pojo.SliceDataView

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface MapDB : AutoCloseable {
	fun contains(db: String): Boolean
	fun contains(db: String,key: String): Boolean
	fun put(db: String,key: String,values: List<String>)
	fun put(db: String,key: String,value: String)
	fun add(db: String,key: String,value: String)
	fun remove(db:String,key: String)
	fun get(db: String,key: String): SliceDataView?
	fun gets(db: String,key: String):List<SliceDataView>
	fun put(db: String,map: Map<String,List<String>>)
	fun keys(db: String):List<String>
	fun getAll(db: String):List<SliceDataView>
}