package org.myrtle.citrus.Error

import java.sql.SQLException

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class JDBCError : SQLException {
	constructor() : super("JDBC异常")
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}