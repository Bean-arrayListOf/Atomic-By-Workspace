@file:JvmName("Macro")
package org.myrtle.atomic

import java.io.BufferedReader
import java.io.InputStream
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

@Suppress("NOTHING_TO_INLINE")
inline fun InputStream.loop(bufferSize: Int,autoClose: Boolean,loop:(b: ByteArray,off: i32,len: i32)-> Unit) {
	val buffer = ByteArray(bufferSize)
	var n = -1
	while (this.read(buffer).also { n = it } != -1){
		loop(buffer,0,n)
	}
	if (autoClose) {
		this.close()
	}
}
