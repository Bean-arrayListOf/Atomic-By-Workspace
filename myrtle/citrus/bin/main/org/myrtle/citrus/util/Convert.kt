package org.myrtle.citrus.util


/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object Convert {
	fun toInt(all: Any):Int {
		return all.toString().toInt()
	}

	fun toUInt(all: Any):UInt {
		return all.toString().toUInt()
	}

	fun toLong(all: Any):Long {
		return all.toString().toLong()
	}

	fun toULong(all: Any):ULong {
		return all.toString().toULong()
	}

	fun toDouble(all: Double):Double{
		return all.toString().toDouble()
	}

	fun toFloat(all: Any):Float{
		return all.toString().toFloat()
	}

	fun toString(all: Any):String{
		return all.toString()
	}

	fun toBoolean(all: Any):Boolean{
		return all.toString().toBoolean()
	}

	fun toShort(all: Any):Short{
		return all.toString().toShort()
	}

	fun toUShort(all: Any):UShort{
		return all.toString().toUShort()
	}

	fun toByte(all: Any):Byte{
		return all.toString().toByte()
	}

	fun toUByte(all: Any):UByte{
		return all.toString().toUByte()
	}

	fun toUnicode(str: String): String {
		val sb = StringBuffer()
		val c = str.toCharArray()
		for (i in c.indices) {
			sb.append("\\u" + Integer.toHexString(c[i].code))
		}
		return sb.toString()
	}


}