package org.myrtle.atomic

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.PreparedStatement
import javax.sql.DataSource

object SQLKit {
	@Suppress("NOTHING_TO_INLINE")
	inline fun Connection.openPStat(sql: String, vararg args: Any?, block: (PreparedStatement) -> Unit) {
		this.prepareStatement(sql).use { ps ->
			args.forEachIndexed { i, arg ->
				ps.setObject(i + 1, arg)
			}
			block(ps)
		}
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun hikariConfig(codeBlock: (HikariConfig) -> Unit):HikariConfig{
		val config = HikariConfig()
		codeBlock(config)
		return config
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun hikariDataSource(codeBlock: (HikariConfig) -> Unit): DataSource {
		return HikariDataSource(hikariConfig(codeBlock))
	}



}