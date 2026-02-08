package org.myrtle.citrus.log

import org.slf4j.event.Level
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class LogStorage {
	private val db: Connection

	enum class StorageType {
		sqlite
	}

	init {
		val logger = "1"

		if (System.getProperty("myrtle.log.storage").toBoolean()) {
			this.db = DriverManager.getConnection("jdbc:sqlite:${File(System.getProperty("myrtle.log.storage")).absolutePath+"/${System.currentTimeMillis()}.log.db"}")
		}else {
			this.db = DriverManager.getConnection("jdbc:sqlite:${File(System.getProperty("java.io.tmpdir")).absolutePath+"/${System.currentTimeMillis()}.log.db"}")

		}

	}

	fun put(message: String,level: Level,e: Throwable?,clazz: Class<*>) {
		Level.TRACE
	}
}