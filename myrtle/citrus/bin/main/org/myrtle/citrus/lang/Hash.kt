package org.myrtle.citrus.lang

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Hash {
	companion object {
		@JvmStatic
		fun now(hash: ByteArray): Hash {
			return Hash(hash)
		}
	}

	constructor(hash: ByteArray) {

	}
}