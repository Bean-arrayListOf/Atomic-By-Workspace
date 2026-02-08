package org.myrtle.citrus.db.MapDBSQliteSpace.Dao

import org.myrtle.citrus.db.MapDB.Pojo.SliceDataView
import org.myrtle.citrus.db.MapDB.Pojo.sqlite_master

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface MapDBSqliteDao : AutoCloseable {
	fun findTableName(tableName: String,type: String): List<sqlite_master>
	fun createTable(tableName: String):Int
	fun insert(tableName: String,key: String,value:ByteArray):Int
	fun delete(tableName: String,key: String):Int
	fun selectAll(tableName: String): List<SliceDataView>
	fun selectKey(tableName: String,key: String): List<SliceDataView>
}