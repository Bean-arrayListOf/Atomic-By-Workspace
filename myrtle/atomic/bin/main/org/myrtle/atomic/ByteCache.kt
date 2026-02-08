package org.myrtle.atomic

import org.myrtle.atomic.IOKit.autoClose
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.vfs2.FileObject
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*

open class ByteCache : Bytes, AutoCloseable{
	private val bsFile: FileObject
	private val log = LoggerFactory.getLogger(this::class.java)

	constructor(){
		val file = createTempFile()
		this.bsFile = file
		autoClose(this)
		//log.debug("BC: ${file.absolutePath}")

	}
	constructor(bytes: ByteArray){
		val file = createTempFile()
		this.bsFile = file
		this.bsFile.content.outputStream.write(bytes)
		this.bsFile.content.outputStream.flush()
		autoClose(this)
		//log.debug("BC: ${file.absolutePath}")
	}
	constructor(file: File){
		val sfile = createTempFile()
		FileInputStream(file).use {
			it.transferTo(sfile.content.outputStream)
		}
		sfile.content.outputStream.flush()
		this.bsFile = sfile
		autoClose(this)
		//log.debug("BC: ${sfile.absolutePath}")
	}
	constructor(stream: InputStream){
		val file = createTempFile()
		this.bsFile = file
		stream.transferTo(this.bsFile.content.outputStream)
		autoClose(this)
		//log.debug("BC: ${file.absolutePath}")
	}

	companion object{
		fun new(): ByteCache{
			return ByteCache()
		}
		fun new(bytes: ByteArray): ByteCache{
			return ByteCache(bytes)
		}
		fun new(file: File): ByteCache{
			return ByteCache(file)
		}
		fun new(stream: InputStream): ByteCache{
			return ByteCache(stream)
		}
	}

	private fun createTempFile(): FileObject{
		val a = org.myrtle.atomic.VirtualFileSystem.getRam("${this::class.java.typeName}/${UUID.randomUUID().toString().uppercase()}.${SystemConfig.of.getString("org.myrtle.canary.ByteCache.createTempFile.suffixName",".bin")}")
		a.parent.createFolder()
		a.createFile()
		return a
	}

	override fun getBytes(): ByteArray {
		return this.bsFile.content.inputStream.readBytes()
	}

	override fun close() {
		this.bsFile.close()
		this.bsFile.delete()
	}

	override fun getBase64(): String {
		return Base64.getEncoder().encodeToString(getBytes())
	}

	override fun getHex(): String {
		val hexFormat = HexFormat.of()
		return hexFormat.formatHex(getBytes())
	}

	override fun getHexs(): List<String> {
		val hexList: java.util.ArrayList<String> = ArrayList()
		for (b in getBytes()) {
			val hex = String.format(SystemConfig.of.getString("org.myrtle.canary.ByteCache.getHexs.hexFormat","%02X"), b.toInt() and 0xFF)
			hexList.add(hex)
		}
		return hexList
	}

	override fun getSha128(): ByteArray {
		return DigestUtils.sha1(getBytes())
	}

	override fun getSha256(): ByteArray {
		return DigestUtils.sha256(getBytes())
	}

	override fun getSha384(): ByteArray {
		return DigestUtils.sha384(getBytes())
	}

	override fun getSha512(): ByteArray {
		return DigestUtils.sha512(getBytes())
	}

	override fun equals(other: Any?): Boolean {
		if (other == null){
			return false
		}
		if (other is ByteArray){
			return DigestUtils.sha512(getBytes()).contentEquals(DigestUtils.sha512(other))
		}
		if (other !is ByteCache) {
			return false
		}
		return DigestUtils.sha512(getBytes()).contentEquals(DigestUtils.sha512(other.getBytes()))
	}

	override fun hashCode(): Int {
		return DigestUtils.sha512(getBytes()).contentHashCode() * SystemConfig.of.getInt("org.myrtle.canary.ByteCache.hashCode.multiplier", 3)
	}

	override fun toString(): String {
		return String(getBytes())
	}

	override fun getIStream(): InputStream {
		return bsFile.content.inputStream
	}

	override fun getOStream(): OutputStream {
		return bsFile.content.outputStream
	}
}