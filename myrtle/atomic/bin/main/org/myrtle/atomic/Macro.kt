@file:JvmName("Macro")
package org.myrtle.atomic

import java.io.BufferedReader
import java.io.Reader

fun ByteArray.toHex():String{
	return ByteKit.toHex(this)
}

fun Reader.loop(block:(String)->Unit){
	val read = BufferedReader(this)
	var line: String? = null
	while (read.readLine().also { line = it } != null){
		block(line!!)
	}
}
