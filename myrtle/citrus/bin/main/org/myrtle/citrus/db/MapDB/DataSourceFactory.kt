package org.myrtle.citrus.db.MapDB

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.File
import javax.sql.DataSource

object DataSourceFactory {
	fun getSqliteDataSource(file: File): DataSource {
		val config = HikariConfig().apply {
			this.jdbcUrl = "jdbc:sqlite:${file.absolutePath}"
			this.driverClassName = "org.sqlite.JDBC"
		}
		return HikariDataSource(config)
	}
}