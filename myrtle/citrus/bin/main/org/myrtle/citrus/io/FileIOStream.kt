package org.myrtle.citrus.io

import java.io.*
import java.nio.file.Path

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class FileIOStream : IOStream {
	private val input: InputStream
	private val output: OutputStream

	companion object {
		@JvmStatic
		fun now(file: String): FileIOStream {
			return FileIOStream(file)
		}

		@JvmStatic
		fun now(file: File): FileIOStream {
			return FileIOStream(file)
		}

		@JvmStatic
		fun now(file: Path): FileIOStream {
			return FileIOStream(file)
		}
	}

	constructor(file: String) {
		input = FileInputStream(file)
		output = FileOutputStream(file)
	}

	constructor(file: File) {
		input = FileInputStream(file)
		output = FileOutputStream(file)
	}

	constructor(path: Path) {
		input = path.toFile().inputStream()
		output = path.toFile().outputStream()
	}

	override fun read(): Int = input.read()

	override fun read(b: ByteArray): Int = input.read(b)

	override fun read(b: ByteArray, off: Int, len: Int): Int = input.read(b, off, len)

	override fun readAllBytes(): ByteArray = input.readAllBytes()

	override fun readNBytes(len: Int): ByteArray = input.readNBytes(len)

	override fun readNBytes(b: ByteArray, off: Int, len: Int): Int = input.readNBytes(b, off, len)

	override fun skip(n: Long): Long = input.skip(n)

	override fun skipNBytes(n: Long) = input.skipNBytes(n)

	override fun available(): Int = input.available()

	override fun mark(readlimit: Int) = input.mark(readlimit)

	override fun reset() = input.reset()

	override fun markSupported(): Boolean = input.markSupported()

	override fun transferTo(out: OutputStream): Long = input.transferTo(out)

	override fun write(b: Int) = output.write(b)

	override fun write(b: ByteArray) = output.write(b)

	override fun write(b: ByteArray, off: Int, len: Int) = output.write(b, off, len)

	override fun flush() = output.flush()

	override fun close() {
		input.close()
		output.close()
	}
}