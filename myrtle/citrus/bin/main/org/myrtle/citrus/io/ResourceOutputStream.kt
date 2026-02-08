package org.myrtle.citrus.io

import org.mapdb.BTreeMap
import java.io.IOException
import java.io.OutputStream

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class ResourceOutputStream : OutputStream {
	private val byteMap: BTreeMap<Long, ByteArray>
	private var currentKey = 1L
	private var currentBuffer: ByteArray? = null
	private var currentBufferSize = 0

	companion object {
		@JvmStatic
		fun now(map: BTreeMap<Long, ByteArray>): ResourceOutputStream {
			return ResourceOutputStream(map)
		}
	}

	constructor(map: BTreeMap<Long, ByteArray>) {
		byteMap = map
	}

	private fun flushCurrentBuffer() {
		if (currentBufferSize > 0) {
			// 创建一个新的 org.myrtle.byte[] 以存储当前缓冲区的内容
			val tempBuffer = ByteArray(currentBufferSize)
			System.arraycopy(currentBuffer, 0, tempBuffer, 0, currentBufferSize)

			// 将 tempBuffer 写入到 MapDB
			byteMap[currentKey++] = tempBuffer

			currentBufferSize = 0
		}
	}

	@Throws(IOException::class)
	override fun flush() {
		if (currentBuffer != null && currentBufferSize > 0) {
			flushCurrentBuffer()
		}
	}

	@Throws(IOException::class)
	override fun write(b: Int) {
		if (currentBuffer == null) {
			currentBuffer = ByteArray(1024) // 初始缓冲区大小
			currentBufferSize = 0
		}

		if (currentBufferSize == currentBuffer!!.size) {
			flushCurrentBuffer()
			currentBufferSize = 0
		}

		currentBuffer!![currentBufferSize++] = b.toByte()
	}

	@Throws(IOException::class)
	override fun write(b: ByteArray) {
		write(b, 0, b.size)
	}

	@Throws(IOException::class)
	override fun write(b: ByteArray, off: Int, len: Int) {
		var off = off
		var len = len
		if (currentBuffer == null) {
			currentBuffer = ByteArray(1024) // 初始缓冲区大小
			currentBufferSize = 0
		}

		while (len > 0) {
			val bytesToCopy = len.coerceAtMost(currentBuffer!!.size - currentBufferSize)
			System.arraycopy(b, off, currentBuffer, currentBufferSize, bytesToCopy)

			off += bytesToCopy
			len -= bytesToCopy
			currentBufferSize += bytesToCopy

			if (currentBufferSize == currentBuffer!!.size) {
				flushCurrentBuffer()
				currentBufferSize = 0
			}
		}
	}

	override fun close() {
		byteMap.close()
	}
}