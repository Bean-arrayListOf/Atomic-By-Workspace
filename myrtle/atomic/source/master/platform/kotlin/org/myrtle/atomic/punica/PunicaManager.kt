package org.myrtle.atomic.punica

import com.zaxxer.hikari.HikariDataSource
import org.myrtle.atomic.SystemKit.config
import java.sql.Connection
import javax.sql.DataSource

class PunicaManager {
	private val dataSource: DataSource
	private val connection: Connection
	constructor(config: PunicaConfig) {
		this.dataSource = config.getDataSource()
		connection = config.getConnection()

	}
}