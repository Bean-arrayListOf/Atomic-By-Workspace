package org.myrtle.citrus.lang

import java.io.File
import java.sql.Connection

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface TempDB:AutoCloseable {
	fun getTemp(): File
	fun copy(out:File)
	fun getConnection(): Connection
	fun createTemp(file: File)
}