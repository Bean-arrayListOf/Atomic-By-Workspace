package org.myrtle.citrus.util

import java.util.*


/**
 * @author cat
 * @since 2025-08-23
 **/
class MacroHashCode: HashCode {

	private val bytes: ByteArray
	constructor(bytes: ByteArray){
		this.bytes = bytes
	}
	override fun getBytes(): ByteArray {
		return bytes
	}

	override fun getHex(): String {
		val hexFormat = HexFormat.of()
		return hexFormat.formatHex(bytes)
	}

	override fun getHexs(): List<String> {
		val hexList: ArrayList<String> = ArrayList()
		for (b in bytes) {
			val hex = String.format("%02X", b.toInt() and 0xFF)
			hexList.add(hex)
		}
		return hexList
	}

	override fun getBase64(): String {
		return Base64.getEncoder().encodeToString(bytes)
	}

	override fun toString(): String {
		return getHexs().joinToString(":")
	}

	override fun equals(other: Any?): Boolean {
		return when (other) {
			is MacroHashCode -> bytes.contentEquals(other.bytes)
			else -> false
		}
	}

	override fun hashCode(): Int {
		return bytes.contentHashCode() + bytes.size * 3
	}
}