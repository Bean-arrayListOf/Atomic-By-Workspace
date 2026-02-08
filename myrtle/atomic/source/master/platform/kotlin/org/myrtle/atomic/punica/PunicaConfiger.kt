package org.myrtle.atomic.punica

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties
import javax.sql.DataSource

/**
 * Punica 配置
 */
data class PunicaConfiger(
	// 数据库JDBC
	var driverClass: String = "org.myrtle.atomic.PunicaDriver",
	// 用户名
	var userName: String = "root",
	// 密码
	var password: String = "root",
	// 数据库文件路径
	var path: String = "punica.db",
	// 是否内存模式
	var aTMem: Boolean = false,
	// 是否只读
	var readOnly: Boolean = false,
){
	// 构建配置接口
	fun build(): PunicaConfig {
		return Builder(this)
	}

	/**
	 * Connection 工厂
	 */
	class Builder(private val punicaConfig: PunicaConfiger): PunicaConfig {

		override fun getDataSource(): DataSource {
			val hc = HikariConfig()
			hc.driverClassName = punicaConfig.driverClass
			hc.username = punicaConfig.userName
			hc.password = punicaConfig.password
			if (punicaConfig.aTMem){
				hc.jdbcUrl = "jdbc:duckdb:"
			}else{
				hc.jdbcUrl = "jdbc:duckdb:${File(punicaConfig.path).absolutePath}"
			}

			if (punicaConfig.readOnly){
				val env = Properties()
				env.setProperty("duckdb.read_only","true")
				hc.dataSourceProperties = env
			}

			return HikariDataSource(hc)
		}

		override fun getConnection(): Connection {
			Class.forName(punicaConfig.driverClass)

			val url = if (punicaConfig.aTMem){
				"jdbc:duckdb:"
			}else{
				"jdbc:duckdb:${File(punicaConfig.path).absolutePath}"
			}

			val env = Properties()
			if (punicaConfig.aTMem){
				env.setProperty("duckdb.read_only","true")
			}

			return DriverManager.getConnection(url, env)
		}

	}
}