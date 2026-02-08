package org.myrtle.citrus.lang

import java.util.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("功能已弃用,将会在下一个版本删除", level = DeprecationLevel.WARNING)
class TestKit {
	companion object {
		@JvmStatic
		fun <T> results(v1: T, v2: T) {
			if (v1 != v2) {
				throw org.myrtle.citrus.Error.TestError()
			}
		}

		@JvmStatic
		fun randomIntArray(arraySize: Int, bound: Int): Array<Int> {
			val random = Random()
			val arr = arrayListOf<Int>()
			for (i in 0 until arraySize) {
				arr.add(random.nextInt(bound))
			}
			return arr.toTypedArray()
		}
	}
}