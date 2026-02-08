package org.myrtle.orcas

import com.typesafe.config.ConfigFactory
import org.myrtle.atomic.Console.Companion.println

object Master {
	val config = ConfigFactory.load(this::class.java.classLoader, "config/app.conf")
	@JvmStatic
	fun main(args: Array<String>) {
		config.getString("app.name").println()
	}


}