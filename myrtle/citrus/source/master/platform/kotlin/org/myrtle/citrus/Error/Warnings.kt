package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Warnings:RuntimeException{
	constructor() : super("警告")
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}