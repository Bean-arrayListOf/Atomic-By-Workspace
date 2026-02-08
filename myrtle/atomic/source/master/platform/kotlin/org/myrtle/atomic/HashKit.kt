package org.myrtle.atomic

import org.myrtle.atomic.IO.openIStream
import org.apache.commons.codec.digest.DigestUtils
import java.io.File
import java.io.InputStream
import java.util.*

object HashKit {
	@JvmStatic
	fun sha128(string: String): Bytes{
		return ByteCache(DigestUtils.sha1(string))
	}

	@JvmStatic
	fun sha128(bytes: ByteArray): Bytes{
		return ByteCache(DigestUtils.sha1(bytes))
	}

	@JvmStatic
	fun sha128(stream: InputStream): Bytes{
		return ByteCache(DigestUtils.sha1(stream))
	}

	@JvmStatic
	fun sha128(file: File): Bytes{
		return file.openIStream().use { ByteCache(DigestUtils.sha1(it)) }
	}

	@JvmStatic
	fun sha256(string: String): Bytes{
		return ByteCache(DigestUtils.sha256(string))
	}

	@JvmStatic
	fun sha256(bytes: ByteArray): Bytes{
		return ByteCache(DigestUtils.sha256(bytes))
	}

	@JvmStatic
	fun sha256(stream: InputStream): Bytes{
		return ByteCache(DigestUtils.sha256(stream))
	}

	@JvmStatic
	fun sha256(file: File): Bytes{
		return file.openIStream().use { ByteCache(DigestUtils.sha256(it)) }
	}

	@JvmStatic
	fun sha384(string: String): Bytes{
		return ByteCache(DigestUtils.sha384(string))
	}

	@JvmStatic
	fun sha384(bytes: ByteArray): Bytes{
		return ByteCache(DigestUtils.sha384(bytes))
	}

	@JvmStatic
	fun sha384(stream: InputStream): Bytes{
		return ByteCache(DigestUtils.sha384(stream))
	}

	@JvmStatic
	fun sha384(file: File): Bytes{
		return file.openIStream().use { ByteCache(DigestUtils.sha384(it)) }
	}

	@JvmStatic
	fun sha512(string: String): Bytes{
		return ByteCache(DigestUtils.sha512(string))
	}

	@JvmStatic
	fun sha512(file: File): Bytes{
		return file.openIStream().use { ByteCache(DigestUtils.sha512(it)) }
	}

	@JvmStatic
	fun sha512(bytes: ByteArray): Bytes{
		return ByteCache(DigestUtils.sha512(bytes))
	}

	@JvmStatic
	fun sha512(stream: InputStream): Bytes{
		return ByteCache(DigestUtils.sha512(stream))
	}

	@JvmStatic
	fun md5(string: String): Bytes{
		return ByteCache(DigestUtils.md5(string))
	}

	@JvmStatic
	fun md5(bytes: ByteArray): Bytes{
		return ByteCache(DigestUtils.md5(bytes))
	}

	@JvmStatic
	fun md5(stream: InputStream): Bytes{
		return ByteCache(DigestUtils.md5(stream))
	}


	@JvmStatic
	val uuid: String
		get() = UUID.randomUUID().toString()

	@JvmStatic
	fun getUUID(separator: String): String{
		return UUID.fromString(separator).toString()
	}

	@JvmStatic
	operator fun get(type: HashType, source: ByteArray): ByteArray{
		return when(type){
			HashType.MD2 -> DigestUtils.md2(source)
			HashType.MD5 -> DigestUtils.md5(source)
			HashType.SHA1 -> DigestUtils.sha1(source)
			HashType.SHA256 -> DigestUtils.sha256(source)
			HashType.SHA3_224 -> DigestUtils.sha3_224(source)
			HashType.SHA3_256 -> DigestUtils.sha3_256(source)
			HashType.SHA3_384 -> DigestUtils.sha3_384(source)
			HashType.SHA3_512 -> DigestUtils.sha3_512(source)
			HashType.SHA384 -> DigestUtils.sha384(source)
			HashType.SHA512 -> DigestUtils.sha512(source)
			HashType.SHA512_224 -> DigestUtils.sha512_224(source)
			HashType.SHA512_256 -> DigestUtils.sha512_256(source)
			HashType.SHAKE128_256 -> DigestUtils.shake128_256(source)
			HashType.SHAKE256_512 -> DigestUtils.shake256_512(source)
		}
	}

	@JvmStatic
	fun getBC(type: HashType, source: ByteArray): ByteCache{
		return ByteCache(get(type,source))
	}

	@JvmStatic
	fun getHex(type: HashType, source: ByteArray): String{
		return when(type){
			HashType.MD2 -> DigestUtils.md2Hex(source)
			HashType.MD5 -> DigestUtils.md5Hex(source)
			HashType.SHA1 -> DigestUtils.sha1Hex(source)
			HashType.SHA256 -> DigestUtils.sha256Hex(source)
			HashType.SHA3_224 -> DigestUtils.sha3_224Hex(source)
			HashType.SHA3_256 -> DigestUtils.sha3_256Hex(source)
			HashType.SHA3_384 -> DigestUtils.sha3_384Hex(source)
			HashType.SHA3_512 -> DigestUtils.sha3_512Hex(source)
			HashType.SHA384 -> DigestUtils.sha384Hex(source)
			HashType.SHA512 -> DigestUtils.sha512Hex(source)
			HashType.SHA512_224 -> DigestUtils.sha512_224Hex(source)
			HashType.SHA512_256 -> DigestUtils.sha512_256Hex(source)
			HashType.SHAKE128_256 -> DigestUtils.shake128_256Hex(source)
			HashType.SHAKE256_512 -> DigestUtils.shake256_512Hex(source)
		}
	}

	@JvmStatic
	fun getHex(type: HashType,source: String): String{
		return getHex(type,source.toByteArray(SystemKit.encode))
 	}

	@JvmStatic
	operator fun get(type: HashType,source: InputStream): ByteArray{
		return when(type){
			HashType.MD2 -> DigestUtils.md2(source)
			HashType.MD5 -> DigestUtils.md5(source)
			HashType.SHA1 -> DigestUtils.sha1(source)
			HashType.SHA256 -> DigestUtils.sha256(source)
			HashType.SHA3_224 -> DigestUtils.sha3_224(source)
			HashType.SHA3_256 -> DigestUtils.sha3_256(source)
			HashType.SHA3_384 -> DigestUtils.sha3_384(source)
			HashType.SHA3_512 -> DigestUtils.sha3_512(source)
			HashType.SHA384 -> DigestUtils.sha384(source)
			HashType.SHA512 -> DigestUtils.sha512(source)
			HashType.SHA512_224 -> DigestUtils.sha512_224(source)
			HashType.SHA512_256 -> DigestUtils.sha512_256(source)
			HashType.SHAKE128_256 -> DigestUtils.shake128_256(source)
			HashType.SHAKE256_512 -> DigestUtils.shake256_512(source)
		}
	}

	@JvmStatic
	fun getBC(type: HashType,source: InputStream): ByteCache{
		return ByteCache(get(type,source))
	}

	@JvmStatic
	fun getHex(type: HashType,source: InputStream): String{
		return when(type){
			HashType.MD2 -> DigestUtils.md2Hex(source)
			HashType.MD5 -> DigestUtils.md5Hex(source)
			HashType.SHA1 -> DigestUtils.sha1Hex(source)
			HashType.SHA256 -> DigestUtils.sha256Hex(source)
			HashType.SHA3_224 -> DigestUtils.sha3_224Hex(source)
			HashType.SHA3_256 -> DigestUtils.sha3_256Hex(source)
			HashType.SHA3_384 -> DigestUtils.sha3_384Hex(source)
			HashType.SHA3_512 -> DigestUtils.sha3_512Hex(source)
			HashType.SHA384 -> DigestUtils.sha384Hex(source)
			HashType.SHA512 -> DigestUtils.sha512Hex(source)
			HashType.SHA512_224 -> DigestUtils.sha512_224Hex(source)
			HashType.SHA512_256 -> DigestUtils.sha512_256Hex(source)
			HashType.SHAKE128_256 -> DigestUtils.shake128_256Hex(source)
			HashType.SHAKE256_512 -> DigestUtils.shake256_512Hex(source)
		}
	}

	@JvmStatic
	operator fun get(type: HashType,source: String): ByteArray{
		return get(type,source.toByteArray(SystemKit.encode))
	}

	@JvmStatic
	fun getBC(type: HashType,source: String): ByteCache{
		return ByteCache(get(type,source.toByteArray(SystemKit.encode)))
	}
}