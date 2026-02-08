package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class AccessDenied : RuntimeException{
	constructor() : super("访问拒绝")
	constructor(message: String?) : super("访问拒绝: [${message}]")
	constructor(message: String?, cause: Throwable?) : super("访问拒绝: [${message}]", cause)
	constructor(cause: Throwable?) : super("访问拒绝: [${cause?.message}]",cause)
}
