package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class RSAError : org.myrtle.citrus.Error.CoralUnknownError {
	constructor() : super("RSA加解密异常")
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}