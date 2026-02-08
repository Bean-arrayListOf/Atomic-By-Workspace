package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Markers : RuntimeException {
	constructor() : super("Markers")

	//onstructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}