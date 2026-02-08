package org.myrtle.citrus.lang

import java.io.Serializable
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object CipherKit {
	data class RSACipherSpec(
		val enc: Cipher,
		val dec: Cipher
	): Serializable
	@JvmStatic
	fun nowRSA(keySize: Int): KeyPair {
		val kpg = KeyPairGenerator.getInstance("RSA")
		kpg.initialize(keySize)
		return kpg.generateKeyPair()
	}
	@JvmStatic
	fun neoRSACipher(keyPair: KeyPair): RSACipherSpec {
		val enc = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
		enc.init(Cipher.ENCRYPT_MODE, keyPair.public)

		val dec = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
		dec.init(Cipher.DECRYPT_MODE, keyPair.private)

		return RSACipherSpec(enc, dec)

	}
}