package org.myrtle.atomic

import java.io.InputStream
import java.io.OutputStream

interface Bytes {
	fun getBytes(): ByteArray
	fun getHex(): String
	fun getHexs(): List<String>
	fun getBase64(): String
	fun getSha128(): ByteArray
	fun getSha256(): ByteArray
	fun getSha384(): ByteArray
	fun getSha512(): ByteArray
	fun getIStream(): InputStream
	fun getOStream(): OutputStream
}