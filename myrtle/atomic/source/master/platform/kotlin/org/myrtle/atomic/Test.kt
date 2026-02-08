package org.myrtle.atomic

import org.myrtle.atomic.Console.Companion.println
import org.myrtle.atomic.StringKit.encString

class Test {
	companion object {
		private val log1 = AutoLog
		private val log2 = AutoCreateLog.now()
		@JvmStatic
		fun main(vararg args: String) {
			IO.openStream("https://github.com/Guardsquare/proguard/blob/master/build.gradle").use { stream ->
				stream.readAllBytes().encString().println()
			}

		}
	}
}