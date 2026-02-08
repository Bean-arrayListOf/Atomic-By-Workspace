package org.myrtle.citrus.lang

import org.myrtle.atomic.HashType
import java.io.Serializable

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class Id(byteArray: ByteArray) : Serializable {
	private var hashCode: ByteArray = ByteArray(64)

	companion object{
		@JvmStatic
		fun nowRandom():Id{
			return Id(StochasticThread().nextHash(HashType.SHA512))
		}
	}

	init {
		println(byteArray.size)
		if (byteArray.size != 64) {
			throw NumberFormatException("Byte[] 长度不是 64")
		}
		for (byte in byteArray.indices) {
			hashCode[byte] = byteArray[byte]
		}
	}
}
