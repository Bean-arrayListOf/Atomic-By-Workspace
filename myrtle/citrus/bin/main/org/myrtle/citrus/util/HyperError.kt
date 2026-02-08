package org.myrtle.citrus.util

import java.io.IOException

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object HyperError {
	private val errorTable: HashMap<Long,Class<*>> = hashMapOf()
	private val e: HyperException

	init {
		errorTable[0] = RuntimeException::class.java
		errorTable[1] = IOException::class.java
		errorTable[2] = NullPointerException::class.java
		this.e = HyperException(errorTable)
	}

	fun getHyper(): HyperException{
		return e
	}
}