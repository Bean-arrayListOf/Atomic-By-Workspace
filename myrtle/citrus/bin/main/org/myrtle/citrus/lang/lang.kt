package org.myrtle.citrus.lang

import java.util.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */

fun <T> stackArrayOf(): StackArray<T> = StackArray()
fun <T> stackArrayOf(list: List<T>): StackArray<T> {
	val array = stackArrayOf<T>()
	for (i in list.reversed()) {
		array.add(i)
	}
	return array
}

fun <T> stackArrayOf(list: Array<T>): StackArray<T> {
	val array = stackArrayOf<T>()
	for (i in list.reversed()) {
		array.add(i)
	}
	return array
}

fun <T> stackOf(): Stack<T> = Stack<T>()