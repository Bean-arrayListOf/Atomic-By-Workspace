package org.myrtle.citrus.io

import java.io.Closeable
import java.io.Flushable
import java.io.IOException
import java.io.OutputStream

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface IOStream: Flushable, Closeable {
	@Throws(IOException::class)
	fun read():Int

	@Throws(IOException::class)
	fun read(b: ByteArray): Int

	@Throws(IOException::class)
	fun read(b: ByteArray, off: Int, len: Int): Int

	@Throws(IOException::class)
	fun readAllBytes(): ByteArray

	@Throws(IOException::class)
	fun readNBytes(len: Int): ByteArray

	@Throws(IOException::class)
	fun readNBytes(b: ByteArray, off: Int, len: Int): Int

	@Throws(IOException::class)
	fun skip(n: Long): Long

	@Throws(IOException::class)
	fun skipNBytes(n: Long)

	@Throws(IOException::class)
	fun available(): Int

	fun mark(readlimit: Int)

	@Throws(IOException::class)
	fun reset()

	fun markSupported(): Boolean

	@Throws(IOException::class)
	fun transferTo(out: OutputStream): Long

	@Throws(IOException::class)
	fun write(b: Int)

	@Throws(IOException::class)
	fun write(b: ByteArray)

	@Throws(IOException::class)
	fun write(b: ByteArray, off: Int, len: Int)


}