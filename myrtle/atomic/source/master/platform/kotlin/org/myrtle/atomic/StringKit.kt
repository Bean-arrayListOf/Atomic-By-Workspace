package org.myrtle.atomic

object StringKit {
	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun String.toUpperCase(): String {
		return this.uppercase(SystemKit.locale)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun String.toLowerCase(): String {
		return this.lowercase(SystemKit.locale)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun ByteArray.encString(): String {
		return String(this, SystemKit.encode)
	}


}