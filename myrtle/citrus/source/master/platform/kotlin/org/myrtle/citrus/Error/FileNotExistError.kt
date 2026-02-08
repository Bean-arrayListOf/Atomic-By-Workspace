package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class FileNotExistError : org.myrtle.citrus.Error.CoralUnknownError {
	constructor() : super("文件不存在")
	constructor(message: String) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}