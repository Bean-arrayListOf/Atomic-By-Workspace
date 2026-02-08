package org.myrtle.citrus.db

/**
 * @author cat
 * @since 2025-08-11
 **/
class ReadOnly : RuntimeException {
	constructor() : super()
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}