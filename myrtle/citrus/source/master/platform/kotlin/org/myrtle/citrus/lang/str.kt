package org.myrtle.citrus.lang

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("API过于老旧")
object str {
	var keywords = "{{arg}}"
	var arg = "arg"

	@JvmStatic
	fun printJoin(s1: String, args: Array<out Any?>): String {
		var temp = s1
		for (i in args.indices) {
			val tempString: String = args[i].toString()
			val key = keywords.replace("{${arg}}", (i + 1).toString()) + ']'
			temp = temp.replace(key, tempString)
		}
		temp = temp.replace(keywords.replace("{${arg}}", "n"), "\n")
		temp = temp.replace(keywords.replace("{${arg}}", "t"), "\t")
		temp = temp.replace(keywords.replace("{${arg}}", "b"), "\b")
		temp = temp.replace(keywords.replace("{${arg}}", "s"), " ")
		return temp
	}

	fun getStrList(inputString: String, length: Int): ArrayList<String> {
		var size = inputString.length / length
		if (inputString.length % length != 0) {
			size += 1
		}
		return getStrList(inputString, length, size)
	}

	fun getStrList(
		inputString: String, length: Int,
		size: Int
	): ArrayList<String> {
		val list = arrayListOf<String>()
		for (index in 0 until size) {
			val childStr: String? = substring(
				inputString, index * length,
				(index + 1) * length
			)
			childStr?.let { list.add(it) }
		}
		return list
	}

	fun substring(str: String, f: Int, t: Int): String? {
		if (f > str.length) return null
		return if (t > str.length) {
			str.substring(f, str.length)
		} else {
			str.substring(f, t)
		}
	}
}