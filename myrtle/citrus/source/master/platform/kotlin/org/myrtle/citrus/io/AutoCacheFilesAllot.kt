package org.myrtle.citrus.io

import okio.IOException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.*
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.readBytes

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class AutoCacheFilesAllot : ACFA {

	private class MetadataWriter : Thread {
		private val metadata: ACFA.Metadata
		private val file: File

		constructor(file: File, metadata: ACFA.Metadata) {
			this.file = file
			this.metadata = metadata
		}

		override fun start() {
			val metaFile = Paths.get(file.absolutePath, "meta.dat")
			if (!metaFile.exists()) {
				metaFile.createFile()
			}
			FileOutputStream(metaFile.toFile()).use { out ->
				ObjectOutputStream(out).use {
					it.writeObject(metadata)
				}
			}
		}
	}

	companion object {
		@JvmStatic
		fun now(file: File, closeDelete: Boolean, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file, closeDelete, commit)
		}

		@JvmStatic
		fun now(file: File, closeDelete: Boolean): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file, closeDelete, listOf("[${this::class.java.typeName}] create"))
		}

		@JvmStatic
		fun now(file: File, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file, true, commit)
		}

		@JvmStatic
		fun now(file: File): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file, true, listOf("[${this::class.java.typeName}] create"))
		}

		@JvmStatic
		fun now(file: String, closeDelete: Boolean, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), closeDelete, commit)
		}

		@JvmStatic
		fun now(file: String, closeDelete: Boolean): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), closeDelete, listOf("[${this::class.java.typeName}] create"))
		}

		@JvmStatic
		fun now(file: String, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), true, commit)
		}

		@JvmStatic
		fun now(file: String): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), true, listOf("[${this::class.java.typeName}] create"))
		}

		@JvmStatic
		fun now(file: Path, closeDelete: Boolean, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file.toFile(), closeDelete, commit)
		}

		@JvmStatic
		fun now(file: Path, closeDelete: Boolean): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file.toFile(), closeDelete, listOf("[${this::class.java.typeName}] create"))
		}

		@JvmStatic
		fun now(file: Path, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file.toFile(), true, commit)
		}

		@JvmStatic
		fun now(file: Path): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(file.toFile(), true, listOf("[${this::class.java.typeName}] create"))
		}

		@JvmStatic
		fun now(file: URI, closeDelete: Boolean, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), closeDelete, commit)
		}

		@JvmStatic
		fun now(file: URI, closeDelete: Boolean): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), closeDelete, listOf("[${this::class.java.typeName}] create"))
		}

		@JvmStatic
		fun now(file: URI, commit: List<String>): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), true, commit)
		}

		@JvmStatic
		fun now(file: URI): AutoCacheFilesAllot {
			return AutoCacheFilesAllot(File(file), true, listOf("[${this::class.java.typeName}] create"))
		}
	}

	protected val log: Logger = LoggerFactory.getLogger(this::class.java)
	private val tempPath: File
	private val kvCache: HashMap<String, File> = hashMapOf()
	private var autoDelete: Boolean

	constructor(file: File, closeDelete: Boolean, commit: List<String>) {
		this.autoDelete = closeDelete
		val uid = UUID.randomUUID().toString().uppercase(Locale.getDefault())
		val tempPath = Paths.get(file.absolutePath, uid).toFile()
		log.info("Create: temp path {}", tempPath)
		if (!tempPath.exists()) {
			tempPath.mkdirs()
		}
		MetadataWriter(tempPath, ACFA.Metadata(uid, System.currentTimeMillis(), this::class.java.typeName, commit)).start()
		this.tempPath = tempPath
	}

	override fun nowTempFile(dir: String, fileName: String): File {
		val path = Paths.get(tempPath.absolutePath, dir, fileName)
		if (path.exists()) {
			throw IOException("Temp org.myrtle.file already exists!")
		}
		if (!path.parent.exists()) {
			path.parent.createDirectory()
		}
		path.createFile()
		return path.toFile()
	}

	override fun nowTempDir():File{
		val path = Paths.get(tempPath.absolutePath,
			UUID.randomUUID().toString().uppercase(Locale.getDefault()), UUID.randomUUID().toString()
				.uppercase(Locale.getDefault())
		).toFile()
		if (!path.exists()) {
			path.mkdirs()
		}
		return path
	}

	override fun nowTempFile(fileName: String): File {
		return nowTempFile(UUID.randomUUID().toString().uppercase(Locale.getDefault()), fileName)
	}

	override fun getMetadata(): ACFA.Metadata {
		Paths.get(tempPath.absolutePath, "meta.dat").readBytes().let { bytes ->
			ByteArrayInputStream(bytes).use {
				ObjectInputStream(it).use {
					return it.readObject() as ACFA.Metadata
				}
			}
		}
	}

	protected fun newFile(): File {
		val randomFile = Paths.get(
			tempPath.absolutePath,
			UUID.randomUUID().toString().uppercase(Locale.getDefault()),
			UUID.randomUUID().toString().uppercase(Locale.getDefault()),
			"meta.dat"
		).toFile()
		if (!randomFile.parentFile.exists()) {
			//randomFile.parent.createDirectory()
			randomFile.parentFile.mkdirs()
		}
		if (!randomFile.exists()) {
			randomFile.createNewFile()
		}
		return randomFile
	}

	override fun nowTempFile(): ACFA.TempSpace {
		val uuid = UUID.randomUUID().toString().uppercase(Locale.getDefault())
		kvCache[uuid] = newFile()
		return ACFA.TempSpace(uuid, kvCache[uuid]!!)
	}

	override fun getKVCache(key: String): File {
		val path = kvCache[key]
		if (path != null) {
			return path
		}
		kvCache[key] = newFile()
		return kvCache[key]!!
	}

	override fun existsKVCache(key: String): Boolean {
		val path = kvCache[key]
		if (path == null) {
			return false
		}
		if (!path.exists()) {
			if (!path.parentFile.exists()) {
				path.parentFile.mkdirs()
			}
			path.createNewFile()
		}
		return true
	}

	override fun deleteKVCache(key: String) {
		val path = kvCache[key]
		if (path != null) {
			path.delete()
			kvCache.remove(key)
		}
	}

	override fun keysKVCache(): List<String> {
		return kvCache.keys.sorted()
	}

	override fun mapKVCache(): Map<String, File> {
		val map = HashMap<String, File>()
		map.putAll(kvCache)
		return map
	}

	override fun getFile(): File {
		return tempPath
	}

	override fun getPath(): Path {
		return Paths.get(tempPath.absolutePath)
	}

	override fun getString(): String {
		return tempPath.absolutePath
	}

	override fun copyToFile(inputStream: InputStream): File {
		val tempFile = nowTempFile().toFile()
		inputStream.use { input ->
			tempFile.outputStream().use { output ->
				input.copyTo(output)
			}
		}
		return tempFile
	}

	override fun copyToFile(file: File): File {
		val tempFile = nowTempFile().toFile()
		FileInputStream(file).use { inputStream ->
			FileOutputStream(tempFile).use { outputStream ->
				inputStream.copyTo(outputStream)
			}
		}
		return tempFile
	}

	override fun copyToFile(file: String): File {
		val tempFile = nowTempFile().toFile()
		getInputStream(file).use { inputStream ->
			FileOutputStream(tempFile).use { outputStream ->
				inputStream.copyTo(outputStream)
			}
		}
		return tempFile
	}

	override fun copyToOutput(inputStream: InputStream): OutputStream {
		val tempFile = nowTempFile().toFile()
		val outputStream = FileOutputStream(tempFile)
		inputStream.use { input ->
			input.copyTo(outputStream)
		}
		return outputStream
	}

	override fun copyToOutput(inputStream: File): OutputStream {
		val tempFile = nowTempFile().toFile()
		val outputStream = FileOutputStream(tempFile)
		FileInputStream(inputStream).use { input ->
			input.copyTo(outputStream)
		}
		return outputStream
	}

	override fun copyToOutput(file: String): OutputStream {
		val tempFile = nowTempFile().toFile()
		val outputStream = FileOutputStream(tempFile)
		getInputStream(file).use { input ->
			input.copyTo(outputStream)
		}
		return outputStream
	}

	override fun copyToInput(stream: InputStream): InputStream {
		val tempFile = nowTempFile().toFile()
		FileOutputStream(tempFile).use {
			stream.copyTo(it)
		}
		return FileInputStream(tempFile)
	}

	private fun getInputStream(file: String): InputStream {
		val filej = file.split(":")
		when (filej[0].lowercase(Locale.getDefault())) {
			"cp", "class-path", "classpath" -> {
				try {
					return this::class.java.classLoader.getResourceAsStream(filej[1])!!
				} catch (e: Exception) {
					throw IOException("Resource内没有[${filej[1]}]这个文件", e)
				}
			}

			"file", "f" -> {
				try {
					return FileInputStream(filej[1])
				} catch (e: Exception) {
					throw IOException("没有[${filej[1]}]这个文件", e)
				}
			}

			else -> {
				return FileInputStream(filej[0])
			}
		}
	}

	override fun close() {
		if (autoDelete) {
			log.info("Close: temp path [{}]", tempPath)
			Files.walk(tempPath.toPath())
				.sorted(Comparator.reverseOrder())
				.forEach { path ->
					Files.delete(path)
				}
		} else {
			log.warn("Close: temp path [{}] w: Automatic cleaning is disabled", tempPath)
		}
	}

}