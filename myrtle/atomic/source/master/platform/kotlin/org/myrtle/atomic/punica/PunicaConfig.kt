package org.myrtle.atomic.punica

import java.sql.Connection
import javax.sql.DataSource

interface PunicaConfig {
	fun getDataSource(): DataSource
	fun getConnection(): Connection
}