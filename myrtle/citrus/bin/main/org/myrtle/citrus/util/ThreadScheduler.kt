package org.myrtle.citrus.util

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class ThreadScheduler : AutoCloseable {

	private val threadTable: HashMap<String,Thread> = hashMapOf()

	fun push(t: Thread){

	}

	override fun close() {}
}