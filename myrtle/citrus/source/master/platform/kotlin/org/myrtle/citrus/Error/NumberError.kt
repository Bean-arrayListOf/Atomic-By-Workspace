package org.myrtle.citrus.Error

import org.myrtle.citrus.PRTS.Companion.toJsonString

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class NumberError : RuntimeException {
	constructor() : super("Number值异常")
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)

	override fun toString(): String {
		return this.toJsonString()
	}
}