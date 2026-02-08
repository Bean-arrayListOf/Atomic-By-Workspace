package org.myrtle.atomic

import org.myrtle.atomic.TypeKit.forName
import org.myrtle.atomic.TypeKit.type
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LoggerKit{
	@JvmStatic
	fun newLogger(obj: Class<*>): Logger = LoggerFactory.getLogger(obj)

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun loggerOf(obj: Any): Logger = newLogger(obj.type())

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun Any.logger(): Logger = newLogger(this.type())

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun loggerOf(): Logger = newLogger(TypeKit.upClassName(1).forName())
}