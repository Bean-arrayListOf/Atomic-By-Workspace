package org.myrtle.atomic

object ByteKit {
	/**
	 * 将字节数组转换为Base64编码的字符串
	 *
	 * @return Base64编码的字符串
	 */
	@JvmStatic
	fun toBase64(hash: ByteArray): String {
		return java.util.Base64.getEncoder().encodeToString(hash)
	}

	/**
	 * 将字节数组转换为指定分隔符的十六进制字符串
	 *
	 * @param spl 分隔符字符
	 * @return 格式化的十六进制字符串
	 */
	@JvmStatic
	fun toFormatHex(hash: ByteArray,spl: Char): String {
		val hexString = StringBuilder()
		for (i in hash.indices) {
			var hex = Integer.toHexString(0xFF and hash[i].toInt())
			if (hex.length == 1) {
				hex = "0$hex"
			}
			hexString.append(hex)
			if (i < hash.size - 1) {
				hexString.append(spl)
			}
		}
		return hexString.toString()
	}

	/**
	 * 将字节数组转换为以":"为分隔符的十六进制字符串集合
	 *
	 * @return 十六进制字符串的集合
	 */
	fun toHexs(hash: ByteArray): Collection<String> {
		return toFormatHex(hash,":").split(":")
	}

	/**
	 * 将字节数组转换为指定分隔符的十六进制字符串
	 *
	 * @param spl 分隔符字符串
	 * @return 格式化的十六进制字符串
	 */
	fun toFormatHex(hash: ByteArray,spl: String): String {
		return toFormatHex(hash,spl[0])
	}

	/**
	 * 将字节数组转换为连续的十六进制字符串
	 *
	 * @return 连续的十六进制字符串
	 */
	fun toHex(hash: ByteArray): String {
		val hexString = java.lang.StringBuilder()
		for (b in hash) {
			val hex = Integer.toHexString(0xFF and b.toInt())
			if (hex.length == 1) {
				hexString.append('0')
			}
			hexString.append(hex)
		}
		return hexString.toString()
	}

	/**
	 * 重写 toString 方法，以十六进制字符串形式返回对象的字符串表示。
	 *
	 * @return 十六进制字符串表示的当前对象。
	 */
	fun toString(hash: ByteArray): String {
		return toHex(hash)
	}
}