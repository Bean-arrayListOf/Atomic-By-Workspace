package org.myrtle.citrus.util

/**
 * @author cat
 * @since 2025-08-23
 **/
interface HashCode {
	fun getBytes(): ByteArray
	fun getHex(): String
	fun getHexs(): List<String>
	fun getBase64(): String
}