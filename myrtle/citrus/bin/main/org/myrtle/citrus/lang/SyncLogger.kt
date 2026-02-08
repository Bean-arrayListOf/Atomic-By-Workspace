package org.myrtle.citrus.lang

import java.io.Serializable

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface SyncLogger {
	enum class Level:Serializable{
		ERROR,
		WARN,
		INFO,
		DEBUG,
		TRACE
	}

	fun info(s1: String)
	fun info(s1: String,e: Throwable)

	fun trace(s1: String)
	fun trace(s1: String,e: Throwable)

	fun debug(s1: String)
	fun debug(s1: String,e: Throwable)

	fun warn(s1: String)
	fun warn(s1: String,e: Throwable)

	fun error(s1: String)
	fun error(s1: String,e: Throwable)

	fun log(level: Level, s1: String)
	fun log(level: Level, s1: String,e: Throwable)

}