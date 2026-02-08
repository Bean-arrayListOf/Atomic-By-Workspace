package org.myrtle.citrus.secure

import org.myrtle.atomic.HashKit
import org.myrtle.atomic.HashType
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object PasswordDB : Serializable {
	private var passwords = mutableMapOf<String,ByteArray>()
	private val defaultHashType: HashType = HashType.SHA512

	fun add(user: String, password: String) {
		passwords[user] = HashKit.get(defaultHashType, password)
	}

	fun verify(user: String, password: String): Boolean {
		val p = passwords[user] ?: return false
		val pa = HashKit.get(defaultHashType, password)
		return p.contentEquals(pa)
	}

	fun remove(user: String, password: String): Boolean {
		if (verify(user, password)) {
			passwords.remove(user)
			return true
		}
		return false
	}

	fun save(file: File) {
		FileOutputStream(file).use { output ->
			ObjectOutputStream(output).use {oos->
				oos.writeObject(passwords)
			}
		}
	}

}