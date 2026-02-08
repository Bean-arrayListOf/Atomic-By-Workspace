package org.myrtle.citrus.Error

import java.io.IOException

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class FileOrFolderDoesNotExistError : IOException {
	constructor() : super("所需文件或文件夹不存在")
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}