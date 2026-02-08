package org.myrtle.citrus.db.MapDB

import org.myrtle.citrus.db.MapDB.mapper.MapDBSQlite
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import javax.sql.DataSource


class MyBatisFactory(private val dataSource: DataSource) {
	private var sqlSessionFactory: SqlSessionFactory? = null

	fun getSqlSessionFactory(): SqlSessionFactory {
		if (sqlSessionFactory == null) {
			val config = Configuration()
			config.addMapper(MapDBSQlite::class.java)
			val env = Environment("org.myrtle.mapdb-sqlite", JdbcTransactionFactory(),dataSource)
			config.environment = env
			this.sqlSessionFactory = SqlSessionFactoryBuilder().build(config)
		}
		return sqlSessionFactory!!
	}

}