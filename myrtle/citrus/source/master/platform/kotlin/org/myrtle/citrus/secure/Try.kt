package org.myrtle.citrus.secure

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Try {
	fun <T> plugCatch(block: () -> T): T? {
		return try {
			block()
		} catch (e: Exception) {
			e.printStackTrace()
			null
		}
	}
}