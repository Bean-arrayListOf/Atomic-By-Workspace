package org.myrtle.citrus.Error

import java.io.IOException

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class FileTypeError : IOException {
	constructor() : super("文件类型错误")
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}