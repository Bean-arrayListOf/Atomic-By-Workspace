package org.myrtle.citrus.io

import org.mapdb.BTreeMap
import java.io.InputStream

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class ResourceInputStream : InputStream {
	private val byteMap: BTreeMap<Long, ByteArray>
	private var currentKey = 1L
	private var currentByteIndex = 0

	companion object {
		@JvmStatic
		fun now(map: BTreeMap<Long, ByteArray>): ResourceInputStream {
			return ResourceInputStream(map)
		}
	}

	constructor(map: BTreeMap<Long, ByteArray>) {
		byteMap = map
	}

	override fun read(): Int {
		if (currentKey > byteMap.lastKey() || currentByteIndex >= byteMap[currentKey]!!.size) {
			close() // 关闭 MapDB 实例
			return -1 // 表示没有更多的数据可读
		}
		val b = byteMap[currentKey]!![currentByteIndex]
		currentByteIndex++
		if (currentByteIndex == byteMap[currentKey]!!.size) {
			currentKey++
			currentByteIndex = 0
		}
		return b.toInt() and 0xFF
	}

	override fun read(b: ByteArray): Int {
		return read(b, 0, b.size)
	}

	override fun read(b: ByteArray, off: Int, len: Int): Int {
		var off = off
		var len = len
		var bytesRead = 0
		while (len > 0 && currentKey <= byteMap.lastKey()) {
			val currentBytes = byteMap[currentKey]
			val bytesToRead = len.coerceAtMost(currentBytes!!.size - currentByteIndex)

			System.arraycopy(currentBytes, currentByteIndex, b, off, bytesToRead)

			bytesRead += bytesToRead
			len -= bytesToRead
			off += bytesToRead
			currentByteIndex += bytesToRead

			if (currentByteIndex == currentBytes.size) {
				currentKey++
				currentByteIndex = 0
			}
		}
		if (bytesRead == 0 && currentKey > byteMap.lastKey()) {
			//org.myrtle.close() // 关闭 MapDB 实例
			return -1 // 表示没有更多的数据可读
		}
		return bytesRead
	}

	override fun available(): Int {
		var totalBytesAvailable = 0
		for (key in currentKey..byteMap.lastKey()) {
			if (key == currentKey) {
				totalBytesAvailable += byteMap[key]!!.size - currentByteIndex
			} else {
				totalBytesAvailable += byteMap[key]!!.size
			}
		}
		return totalBytesAvailable
	}

	override fun close() {
		byteMap.close()
	}
}