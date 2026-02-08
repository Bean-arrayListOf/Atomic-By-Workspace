package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class ReadOnly: RuntimeException {
	constructor(message: String) : super("Read only: $message")
	constructor(message: String, cause: Throwable) : super(message, cause)
	constructor(cause: Throwable) : super(cause)
	constructor():super("Read only")
}