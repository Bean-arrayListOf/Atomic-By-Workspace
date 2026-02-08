package org.myrtle.citrus.log

import java.sql.Connection
import java.sql.DriverManager

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class HyperLogger {
	private val connection: Connection
	constructor(file: String){
		this.connection = DriverManager.getConnection("jdbc:sqlite:${file}")
	}
}