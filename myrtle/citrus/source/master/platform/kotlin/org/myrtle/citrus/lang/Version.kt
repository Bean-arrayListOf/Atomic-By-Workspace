package org.myrtle.citrus.lang

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class Version : Serializable {
	@Expose
	@SerializedName("Versions")
	private val versions = IntArray(4)

	companion object {
		@JvmStatic
		fun now(v1: Int, v2: Int, v3: Int, v4: Int): Version {
			return Version(v1, v2, v3, v4)
		}

		@JvmStatic
		fun now(version: String): Version {
			return Version(version)
		}

		@JvmStatic
		fun now(): Version {
			return Version()
		}
	}

	constructor(v1: Int, v2: Int, v3: Int, v4: Int) {
		add(v1, v2, v3, v4)
	}

	constructor(version: String) {
		add(version)
	}

	constructor() {
		add(SimpleDateFormat("yy.MM.dd.00").format(System.currentTimeMillis()))
	}

	fun add(index: Int, version: Int) {
		versions[index] = version
	}

	fun add(v1: Int, v2: Int, v3: Int, v4: Int) {
		versions[0] = v1
		versions[1] = v2
		versions[2] = v3
		versions[3] = v4
	}

	fun add(version: String) {
		val split = version.split(".")
		try {
			versions[0] = split[0].toInt()
			versions[1] = split[1].toInt()
			versions[2] = split[2].toInt()
			versions[3] = split[3].toInt()
		} catch (_: Exception) {
		}
	}

	override fun toString(): String {
		val list = arrayListOf<String>()
		versions.forEach {
			if (it != 0) {
				val s = it.toString()
				if (s.length==1) {
					list.add("0${s}")
				}else {
					list.add(s)
				}
			}
		}
		return list.joinToString(".")
	}

	fun toInt(): Int {
		val list = arrayListOf<String>()
		versions.forEach {
			val temp = it.toString()
			if (temp.length == 1) {
				list.add("0${temp}")
			} else {
				list.add(temp)
			}
		}
		return list.joinToString("").toInt()
	}

	fun toLong(): Long {
		val list = arrayListOf<String>()
		versions.forEach {
			val temp = it.toString()
			if (temp.length == 1) {
				list.add("0${temp}")
			} else {
				list.add(temp)
			}
		}
		return list.joinToString("").toLong()
	}

	fun equals(other: Version): Boolean {
		val v1 = this.versions.joinToString("").toLong()
		val v2 = other.versions.joinToString("").toLong()
		return v1 == v2
	}
}