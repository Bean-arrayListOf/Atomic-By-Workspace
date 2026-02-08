package org.myrtle.citrus.util

import org.myrtle.atomic.IOKit.shutdown
import org.myrtle.citrus.PRTS.Companion.neoByteArrayInputStream
import org.myrtle.citrus.PRTS.Companion.neoObjectInputStream
import org.myrtle.citrus.PRTS.Companion.neoObjectOutputStream
import org.myrtle.citrus.io.AutoCacheFilesAllot
import org.myrtle.citrus.lang.JvmArgs
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry
import org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream
import org.apache.commons.compress.archivers.cpio.CpioArchiveOutputStream
import org.apache.commons.lang3.ArrayUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.*
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object Citrus {

	/**
	 * log 属性，用于获取当前日志对象
	 */
	@JvmStatic
	private val log: Logger = LoggerFactory.getLogger(this::class.java)

	/**
	 * 程序临时文件流，用于管理当前程序的临时文件流
	 */
	@JvmStatic
	val temp = getTempFileStream()

	/**
	 * 用户目录，用于获取当前用户的目录
	 */
	@JvmStatic
	val userDir = File(System.getProperty("user.dir"))

	/**
	 * 获取当前Java的安装目录
	 */
	@JvmStatic
	val javaHome = File(System.getProperty("java.home"))

	/**
	 * 用户主目录，用于获取当前用户的主目录
	 */
	@JvmStatic
	val userHome = File(System.getProperty("user.home"))

	private val threadScheduler: ThreadScheduler = createThreadScheduler()

	/**
	 * Java版本，用于获取当前Java的版本
	 */
	@JvmStatic
	val javaVersion: String = System.getProperty("java.version")

	@JvmStatic
	private val autoCloseStream = arrayListOf<AutoCloseable>()

	@JvmStatic
	private val autoCloseThread = arrayListOf<Thread>()

	@JvmStatic
	val jvmCloseTask = JVMCloseTask(autoCloseStream, autoCloseThread)

	@JvmStatic
	val scanner = Scanner(System.`in`)

	@JvmStatic
	val jvmArgs = JvmArgs(System.getProperties())

	@JvmStatic
	lateinit var main: Class<*>

	init {
		Runtime.getRuntime().addShutdownHook(jvmCloseTask)
	}

	private fun createThreadScheduler():ThreadScheduler{
		val ts = ThreadScheduler()
		Runtime.getRuntime().addShutdownHook(Thread{
			run {
				ts.close()
			}
		})
		return ts
	}

	object IO{
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.existsMkdirs():Boolean{
			if (!this.exists()){
				val file = this.parentFile
				return file.mkdirs()
			}
			return false
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun exists(file: File): Boolean {
			return file.exists()
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun exists(file: String): Boolean {
			return File(file).exists()
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun exists(file: Path): Boolean {
			return file.exists()
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun mkdirs(file: String): Boolean {
			return File(file).mkdirs()
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun mkdirs(file: Path): Boolean {
			return file.createDirectories().exists()
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun mkdirs(file: File): Boolean {
			return file.mkdirs()
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun createNowFile(file: File): Boolean {
			return file.createNewFile()
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun createNewFile(file: String): Boolean {
			return File(file).createNewFile()
		}
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun createNewFile(file: Path): Boolean {
			return file.createFile().exists()
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun companion(block: ()->Unit){
		block()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> companion(block: ()->T):T{
		return block()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun autoJvmClose(vararg threads: Thread) {
		threads.forEach {
			Runtime.getRuntime().addShutdownHook(it)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun addJvmClose(vararg closeable: AutoCloseable) {
		closeable.forEach { close ->
			close.shutdown()
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun String.path(): Path {
		return Paths.get(this)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun String.file(): Path {
		return Paths.get(this)
	}

	private fun getTempFileStream(): AutoCacheFilesAllot {
		val ad: Boolean = try {
			System.getProperty("myrtle.citrus.acfa.org.myrtle.close.remove").toBoolean()
		}catch (_:NullPointerException){
			true
		}
		val temp = AutoCacheFilesAllot.now(
			File(System.getProperty("java.io.tmpdir")),
			ad,
			listOf(
				"Created by [${this::class.java.typeName}] and [${System.currentTimeMillis()}]",
				"The cache is deleted at the end of the JVM"
			)
		)
		val t = Thread {
			run {
				temp.close()
			}
		}
		t.name = "TempFileStream"
		Runtime.getRuntime().addShutdownHook(t)
		return temp
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun copy(input: InputStream, output: OutputStream) {
		input.copyTo(output)
	}

	@JvmStatic
	@Throws(IOException::class)
	@Suppress("NOTHING_TO_INLINE")
	inline fun getInputStream(file: String): InputStream {
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

	object Serializable {
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun serialize(obj: Any?) = JavaBinary.serialize(obj)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> deserialize(bytes: ByteArray) = JavaBinary.deserialize<T>(bytes)
	}

	object JavaBinary {
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun to(obj: Any) = serialize(obj)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun serialize(obj: Any?): ByteArray {
			ByteArrayOutputStream().use { baos ->
				baos.neoObjectOutputStream().use { oos ->
					oos.writeObject(obj)
					oos.flush()
				}
				return baos.toByteArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> form(bytes: ByteArray) = deserialize<T>(bytes)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> form(input: InputStream): T {
			ObjectInputStream(input).use { ois ->
				return ois.readObject() as T
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> form(file: Path): T {
			FileInputStream(file.toFile()).use { fis ->
				ObjectInputStream(fis).use { ois ->
					return ois.readObject() as T
				}
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> form(file: File): T {
			FileInputStream(file).use { fis ->
				ObjectInputStream(fis).use { ois ->
					return ois.readObject() as T
				}
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> deserialize(bytes: ByteArray): T {
			bytes.neoByteArrayInputStream().use { bais ->
				bais.neoObjectInputStream().use { ois ->
					return ois.readObject() as T
				}
			}
		}
	}

	object ZIP {
		@JvmStatic
		fun archive(input: String, output: String, comment: String) {
			val inputFile = File(input)
			val outputFile = File(output)
			if (!inputFile.exists()) {
				throw IOException("[${input}] 不存在")
			}
			if (!outputFile.exists()) {
				val parentFile: File = outputFile.getParentFile()
				parentFile.mkdirs()
				outputFile.createNewFile()
			}
			ZipOutputStream(FileOutputStream(outputFile)).use { out ->
				out.setComment(String(comment.toByteArray(Charset.defaultCharset()), Charset.defaultCharset()))
				zip(out, inputFile, inputFile.getName())
			}
		}

		@JvmStatic
		fun zip(zipOut: ZipOutputStream, inputFile: File, base: String) {
			val bts = ByteArray(1024)
			var num = 0
			if (inputFile.isFile()) {
				zipOut.putNextEntry(ZipEntry(base))
				val `in` = FileInputStream(inputFile)
				while (`in`.read(bts).also { num = it } != -1) {
					zipOut.write(bts, 0, num)
				}
				`in`.close()
			} else {
				val subFiles = inputFile.listFiles()!!
				var bases = base
				bases = if (bases.isEmpty()) "" else "$bases/"
				for (i in subFiles.indices) {
					zip(zipOut, subFiles[i], bases + subFiles[i].getName())
				}
			}
		}
	}

	object CPIO {
		@JvmStatic
		fun unpack(input: String, output: String) {
			try {
				//val input = FileInputStream("output/jadx.cpio")
				//val output = "input/jadx"
				val cais = CpioArchiveInputStream(FileInputStream(input))
				var cae: CpioArchiveEntry
				while (cais.nextCPIOEntry.also { cae = it } != null) {
					val name = cae.name
					val file = File(output, name)
					if (cae.isDirectory) {
						file.mkdir()
					} else {
						//FileUtils.copyToFile(cais, org.myrtle.file)
						file.setLastModified(cae.lastModifiedDate.time)
					}
				}
			} catch (_: EOFException) {
			}
		}

		@JvmStatic
		fun archive(input: String, output: String) {
			val caos = CpioArchiveOutputStream(FileOutputStream(output))
			CpioResoirce(caos, File(input), "")
		}

		@JvmStatic
		fun CpioResoirce(cpioStream: CpioArchiveOutputStream, srcFile: File, basePath: String) {
			if (srcFile.isDirectory) {
				val files = srcFile.listFiles()
				val nextBasePath = Paths.get(basePath, srcFile.name, "/").toFile().path
				if (ArrayUtils.isEmpty(files)) {
					val entry = CpioArchiveEntry(srcFile, nextBasePath)
					cpioStream.putArchiveEntry(entry)
					cpioStream.closeArchiveEntry()
				} else {
					files?.let {
						for (file in it) {
							CpioResoirce(cpioStream, file, nextBasePath)
						}
					}
				}
			} else {
				val entry = CpioArchiveEntry(srcFile, Paths.get(basePath, srcFile.name).toFile().path)
				cpioStream.putArchiveEntry(entry)
				//FileUtils.copyFile(srcFile, cpioStream)
				cpioStream.closeArchiveEntry()
			}
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun getFiles(file: File): List<File> {
		val files = ArrayList<File>()
		getAllFile(file, files)
		return files
	}

	@JvmStatic
	fun getAllFile(file: File, allFileList: ArrayList<File>) {
		val files = file.listFiles() ?: return
		for (f in files) {
			if (f.isDirectory) {
				getAllFile(f, allFileList)
			} else {
				if (f.name != ".DS_Store") {
					allFileList.add(f)
				}
			}
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun closes(vararg close: AutoCloseable) {
		close.forEach {
			it.close()
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun flush(vararg flush: Flushable) {
		flush.forEach {
			it.flush()
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun InputStream.reader(block: (Reader) -> Unit) {
		this.use {
			block(InputStreamReader(it, Charset.defaultCharset()))
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun InputStream.reads(bufferSize: Int, block: (ByteArray, Int) -> Unit) {
		this.use {
			val buffer = ByteArray(bufferSize)
			var n = -1
			while (this.read(buffer).also { n = it } != -1) {
				block(buffer, n)
			}
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun InputStream.reads(block: (ByteArray, Int) -> Unit) {
		this.use {
			val buffer = ByteArray(1024)
			var n = -1
			while (this.read(buffer).also { n = it } != -1) {
				block(buffer, n)
			}
		}
	}

}
