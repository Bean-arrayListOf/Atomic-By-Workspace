package org.myrtle.citrus

import org.myrtle.atomic.Console.Companion.println
import org.myrtle.atomic.StringKit.encString
import org.myrtle.atomic.annotation.CloseableOnExit
import org.myrtle.atomic.annotation.Env
import org.myrtle.atomic.annotation.OpenStream
import org.myrtle.atomic.annotation.Processor
import org.myrtle.atomic.annotation.SConfig
import org.myrtle.atomic.annotation.Timestamp
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStream

object Master1 {

	init{
		Processor.invoke(this)
	}

	private val log = LoggerFactory.getLogger(this::class.java)

	@CloseableOnExit
	private val text: InputStream = FileInputStream("/Users/cat/Desktop/PhotoImporter.scpt")

	@JvmStatic
	fun main(args: Array<String>) {

	}

}