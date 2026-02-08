package org.myrtle.citrus.db

import java.io.File
import java.nio.file.Path
import java.sql.DriverManager

class StrataBlobData: AutoCloseable {
	private val connect: SQLConnect
	private var rootFile: File? = null
	private var delete: Boolean = false

	constructor(connect: SQLConnect){
		this.connect = connect
	}

	constructor(jdbcURL: String){
		connect = DriverManager.getConnection(jdbcURL)
	}

	constructor(file: File,delete: Boolean){
		connect = DriverManager.getConnection("jdbc:sqlite://${file.absolutePath}")
		this.rootFile = file
		this.delete = delete
	}

	constructor(file: Path,delete: Boolean){
		connect = DriverManager.getConnection("jdbc:sqlite://${file.toFile().absolutePath}")
		this.rootFile = file.toFile()
		this.delete = delete
	}

//	fun createIndexTable(): Boolean{
//		val ps = connect.prepareStatement(
//			"""
//			create table "\$STRATABLOB_INDEX"(
//				"V_INDEX" integer primary key autoincrement,
//				"V_DATABASE" blob not null,
//				"V_KEY" blob not null,
//				"D_DATA_TABLE" varchar(500) not null ,
//				"D_TIMESTAMP" timestamp default current_timestamp
//			);
//		""".trimIndent()
//		).use { ps ->
//			ps.executeUpdate()
//		}
//		return ps < 0
//	}

	override fun close() {
		if (!connect.isClosed){
			connect.close()
		}
		if (!delete){
			return
		}
		if (rootFile!=null){
			rootFile!!.deleteOnExit()
		}
	}
}