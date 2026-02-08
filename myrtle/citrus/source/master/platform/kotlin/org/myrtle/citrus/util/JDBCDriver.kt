package org.myrtle.citrus.util

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object JDBCDriver {
	enum class Driver(val clazz: String){
		DuckDB("org.duckdb.DuckDBDriver"),MySQL5x(""),MySQL("");
	}

	fun forName(driver: Driver){
		Class.forName(driver.clazz)
	}
}