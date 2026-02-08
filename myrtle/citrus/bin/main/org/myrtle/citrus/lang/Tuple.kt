package org.myrtle.citrus.lang

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("功能已弃用,将会在下一个版本删除", level = DeprecationLevel.WARNING)
class Tuple : Iterable<Any?> {
	private val tupleTable = arrayListOf<Any?>()

	constructor(vararg values: Any?) {
		tupleTable.addAll(values)
	}

	constructor(tuple: Tuple) {
		tuple.forEach {
			tupleTable.add(it)
		}
	}

	constructor(collection: Collection<Any?>) {
		tupleTable.addAll(collection)
	}

	operator fun get(index: Int): Any? {
		return tupleTable[index]
	}

	override fun iterator(): Iterator<Any?> {
		return tupleTable.iterator()
	}
}