package org.myrtle.citrus.io

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.Serializable
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface ACFA : AutoCloseable {

	data class TempSpace(
		val uid: String,
		private val path: File
	) : Serializable {
		fun toFile(): File {
			return path
		}

		fun toPath(): Path {
			return Paths.get(path.absolutePath)
		}

		fun toInputStream(): InputStream {
			return path.inputStream()
		}

		fun toOutputStream(): OutputStream {
			return path.outputStream()
		}

		override fun toString(): String {
			return path.absolutePath
		}
	}

	data class Metadata(
		val uid: String,
		val nowTimeMillis: Long,
		val nowClassPath: String,
		val commit: List<String>
	) : Serializable

	fun nowTempFile(dir: String, fileName: String): File

	fun nowTempDir():File

	fun nowTempFile(fileName: String): File

	fun getMetadata(): Metadata

	fun nowTempFile(): TempSpace

	fun getKVCache(key: String): File

	fun existsKVCache(key: String): Boolean

	fun deleteKVCache(key: String)

	fun keysKVCache(): List<String>

	fun mapKVCache(): Map<String, File>

	fun getFile(): File

	fun getPath(): Path

	fun getString(): String

	fun copyToFile(inputStream: InputStream): File

	fun copyToFile(file: File): File

	fun copyToFile(file: String): File

	fun copyToOutput(inputStream: InputStream): OutputStream

	fun copyToOutput(inputStream: File): OutputStream

	fun copyToOutput(file: String): OutputStream

	fun copyToInput(stream: InputStream): InputStream
}