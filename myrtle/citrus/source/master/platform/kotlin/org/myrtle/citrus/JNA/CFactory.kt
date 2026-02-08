package org.myrtle.citrus.JNA

import com.sun.jna.Native
import com.sun.jna.Platform

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object CFactory {
	val ansiC: org.myrtle.citrus.JNA.ANSIC = if (Platform.isWindows()) {
		Native.load(try{System.getProperty("myrtle.citrus.ansi-c")}catch (_: Exception){
			"msvcrt"}, ANSIC::class.java)
	} else {
		Native.load("c", ANSIC::class.java)
	}
}