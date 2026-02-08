
/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus.lang

import org.myrtle.citrus.PRTS.Companion.neoFile
import java.util.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class TestInfo {
	val info = arrayListOf<String>()

	init {
		info.add("==================================================")
		info.add("　/l 、     ")
		info.add("（ﾟ､ ｡ ７   ")
		info.add("  l、~ ヽ   ")
		info.add("  ししと ）ノ")
		info.add("jvm >:: owo ::> Java Test ::> ${this::class.java.name}")
		info.add("classpath: ${this::class.java.name}")
		info.add("time: ${System.currentTimeMillis()}")
		info.add("UUID: ${UUID.randomUUID()}")
		info.add("hashCode: ${this.hashCode()}")
		info.add("java:")
		info.add("\tversion: ${System.getProperty("java.version")}")
		info.add("\thome: ${System.getProperty("java.home")}")
		info.add("jvm:")
		info.add("\tvm: ${System.getProperty("java.vm.name")}")
		info.add("\tversion: ${System.getProperty("java.vm.version")}")
		info.add("org.myrtle.os:")
		info.add("\tname: ${System.getProperty("org.myrtle.os.name")}")
		info.add("\tarchive: ${System.getProperty("org.myrtle.os.arch")}")
		info.add("\tversion ${System.getProperty("org.myrtle.os.version")}")
		info.add("user:")
		info.add("\tname: ${System.getProperty("user.name")}")
		info.add("\thome: ${System.getProperty("user.home")}")
		info.add("\tdir: ${System.getProperty("user.dir")}")
		info.add("jar:")
		val file = System.getProperty("user.dir").neoFile()
		file.listFiles()?.forEach {
			info.add("\t${it.absolutePath}")
		}
		info.add("org.myrtle.env:")
		info.add("\tshell: ${System.getenv("SHELL")}")
		info.add("==================================================")
		for (i in info) {
			println(i)
		}
	}
}