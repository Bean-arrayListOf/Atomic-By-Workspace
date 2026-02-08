package org.myrtle.atomic

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object AutoCreateLog {

	fun now(): Logger{
		val a = Class.forName(TypeKit.upClassName(2))
		return LoggerFactory.getLogger(a)
	}
}