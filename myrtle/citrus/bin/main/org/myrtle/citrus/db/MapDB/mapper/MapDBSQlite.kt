package org.myrtle.citrus.db.MapDB.mapper

import org.myrtle.citrus.db.MapDB.Pojo.sqlite_master
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MapDBSQlite {
	@Insert("create table \${tableName}(\"index\" integer primary key autoincrement ,\"key\" varchar(500) not null ,\"data\" blob,\"time\" timestamp default current_timestamp);")
	fun createTable(tableName: String)

	@Select("select * from sqlite_master where tbl_name = #{tbl_name} and type = #{type};")
	fun findTableName(sqliteMaster: sqlite_master): List<sqlite_master>
}