package org.myrtle.citrus

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import org.myrtle.atomic.SystemKit
import org.myrtle.citrus.JNA.CFactory
import org.myrtle.citrus.lang.IResultSet
import org.myrtle.citrus.lang.StringKit
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.yaml.snakeyaml.Yaml
import java.io.*
import java.nio.charset.Charset
import java.nio.file.Path
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.io.path.name

/**
 * 简化拓展类
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
open class PRTS {
	companion object {

		/**
		 * 使用 @JvmStatic 注解使得一个非静态方法或属性能够在 Java 代码中通过类名直接调用，而无需实例化对象。
		 * ThreadLocalRandom 是为多线程设计的随机数生成器，每个线程都有独立的随机数生成器，避免了多线程中的共享状态问题。
		 * 将 ThreadLocalRandom.current() 保存为静态属性，避免在每次需要时都调用 .current() 方法，提高性能。
 		 */
		@JvmStatic
		val random = ThreadLocalRandom.current()

		/**
		 * 创建并返回一个空的Properties对象
		 *
		 * @return Properties 返回的Properties对象，用于存储键值对
		 */
		@JvmStatic
		fun propertiesOf() = Properties()

		/**
		 * 根据指定的初始容量创建并返回一个新的Properties对象。
		 *
		 * @param initialCapacity 该Properties对象的初始容量。
		 * @return 一个新的Properties对象，其初始容量由initialCapacity参数指定。
		 */
		@JvmStatic
		fun propertiesOf(initialCapacity: Int) = Properties(initialCapacity)

		/**
		 * 创建一个新的 Properties 对象，并将其初始化为指定默认属性的副本。
		 *
		 * @param defaults The defaults, a Properties object containing default properties.
		 * @return A new Properties object initialized as a org.myrtle.copy of the specified default properties.
		 */
		@JvmStatic
		fun propertiesOf(defaults: Properties) = Properties(defaults)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.getBytes(): ByteArray {
			return this.toByteArray(SystemKit.encode)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.isAscii(): Boolean {
			return StringKit.isAscii(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> T.code(block: (T) -> Unit) {
			return block(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> T.rode(block: (T) -> T): T {
			return block(this)
		}

		@Suppress("NOTHING_TO_INLINE")
		inline fun nowLog(javaClass: Class<*>):Logger{
			return LoggerFactory.getLogger(javaClass)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Any.toJsonString() = Gson().toJson(this)
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Any.toFormatJsonString() = GsonBuilder().setPrettyPrinting().create().toJson(this)
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> String.fromJson(clazz: Class<*>) = GsonBuilder().create().fromJson(this,clazz) as T
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> String.fromToml(clazz: Class<*>) = Toml().read(this).to(clazz) as T
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> String.fromYaml() = Yaml().load<T>(this)
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Any.toYamlString() = Yaml().dump(this)
		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Any.toTomlString() = TomlWriter().write(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.getBytes(charset: Charset): ByteArray {
			return this.toByteArray(charset)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.getString(): String {
			return this.getString(SystemKit.encode)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.getString(charset: Charset): String {
			return String(this, charset)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun connectionOf(url: String) = DriverManager.getConnection(url)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun connectionOf(url: String, user: String, password: String) = DriverManager.getConnection(url, user, password)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun connectionOf(url: String, info: Properties) = DriverManager.getConnection(url, info)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun loggerOf(clazz: Class<*>) = LoggerFactory.getLogger(clazz)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun loggerOf(name: String) = LoggerFactory.getLogger(name)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun bufferedInputStreamOf(inputStream: InputStream): BufferedInputStream {
			return BufferedInputStream(inputStream)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun bufferedInputStreamOf(inputStream: InputStream, size: Int): BufferedInputStream {
			return BufferedInputStream(inputStream, size)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun bufferedOutputStreamOf(out: OutputStream): BufferedOutputStream {
			return BufferedOutputStream(out)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun bufferedOutputStreamOf(out: OutputStream, size: Int): BufferedOutputStream {
			return BufferedOutputStream(out, size)
		}

		@JvmStatic
		@Throws(FileNotFoundException::class)
		@Suppress("NOTHING_TO_INLINE")
		inline fun inputStreamOf(file: String): InputStream {
			val spl = file.split(':')
			if (spl.size == 1) {
				val inputStream = this::class.java.classLoader.getResourceAsStream(file)
				if (inputStream != null) {
					return inputStream
				}
				return FileInputStream(file)
			}
			return when (spl[0].lowercase(Locale.getDefault())) {
				"classpath", "cp" -> {
					this::class.java.classLoader.getResourceAsStream(spl[1])
						?: throw FileNotFoundException("${spl[1]} (No such org.myrtle.file or directory)")
				}

				"file", "f" -> {
					FileInputStream(spl[1])
				}

				else -> {
					FileInputStream(spl[1])
				}
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun byteArrayOf(size: Int): ByteArray = ByteArray(size)

		@JvmStatic
		@Throws(FileNotFoundException::class)
		@Suppress("NOTHING_TO_INLINE")
		inline fun inputStreamOf(file: File): InputStream {
			return FileInputStream(file)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun inputStreamOf(file: FileDescriptor): InputStream {
			return FileInputStream(file)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun byteArrayInputStreamOf(buf: ByteArray) = ByteArrayInputStream(buf)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun byteArrayInputStreamOf(buf: ByteArray, offset: Int, length: Int) = ByteArrayInputStream(buf, offset, length)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun byteArrayOutputStreamOf(size: Int) = ByteArrayOutputStream(size)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun byteArrayOutputStreamOf() = ByteArrayOutputStream()

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun gsonBuilderOf() = GsonBuilder()

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun InputStream.neoBufferedInputStream() = bufferedInputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun OutputStream.neoBufferedOutputStream() = bufferedOutputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.neoInputStream(): InputStream = fileInputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun fileInputStreamOf(it: String): InputStream = FileInputStream(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.neoFile() = fileOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun fileOf(it: String): File = File(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.neoInputStream(): InputStream = fileInputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun fileInputStreamOf(it:File):InputStream = FileInputStream(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun FileDescriptor.neoInputStream(): InputStream = fileInputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun fileInputStreamOf(it:FileDescriptor):InputStream = FileInputStream(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.neoByteArrayInputStream() = byteArrayInputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.neoByteArrayInputStream(offset: Int, length: Int) = byteArrayInputStreamOf(this, offset, length)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.neoFileWriter() = fileWriterOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun fileWriterOf(it:String):FileWriter = FileWriter(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.neoFileWriter() = fileWriterOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun fileWriterOf(it:File):FileWriter = FileWriter(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Path.neoFileWriter() = fileWriterOf(this.toFile())

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun fileWriterOf(it:Path):FileWriter = FileWriter(it.toFile())

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Writer.neoBufferedWriter() = bufferedWriterOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun bufferedWriterOf(it: Writer): BufferedWriter = BufferedWriter(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun OutputStream.neoOutputStreamWriter() = outputStreamWriterOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun outputStreamWriterOf(it: OutputStream): OutputStreamWriter = OutputStreamWriter(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun java.io.Reader.neoBufferedReader() = bufferedReaderOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun bufferedReaderOf(it: java.io.Reader): BufferedReader = BufferedReader(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun InputStream.neoInputStreamReader() = inputStreamReaderOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun inputStreamReaderOf(it: InputStream): InputStreamReader = InputStreamReader(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArrayOutputStream.neoObjectOutputStream() = objectOutputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun objectOutputStreamOf(it: ByteArrayOutputStream): ObjectOutputStream = ObjectOutputStream(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArrayInputStream.neoObjectInputStream() = objectInputStreamOf(this)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun objectInputStreamOf(it: ByteArrayInputStream): ObjectInputStream = ObjectInputStream(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToAbsolutePathString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.absolutePath)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToCanonicalPathString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.canonicalPath)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToParentFilePathString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.parentFile.path)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToNameString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.name)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToExtensionString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.extension)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToParentString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.parent)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToInvariantSeparatorsPathString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.invariantSeparatorsPath)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.listFilesToNameWithoutExtensionString(): Array<String> {
			return synchronized(this) {
				val list = arrayListOf<String>()
				this.listFiles()?.forEach {
					list.add(it.nameWithoutExtension)
				}
				list.toTypedArray()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.suffixName(): String {
			return synchronized(this) {
				val split = arrayListOf<String>()
				split.addAll(this.split("."))
				split.removeAt(0)
				split.joinToString(".")
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.suffixName(): String {
			return synchronized(this) {
				this.name.suffixName()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Path.suffixName(): String {
			return synchronized(this) {
				this.name.suffixName()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.neoFileIsMkdirs() {
			synchronized(this) {
				if (!this.exists()) {
					this.parentFile.mkdirs()
					this.createNewFile()
				}
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.execute(): Int {
			return synchronized(this) {
				//Cli.exec(this)
				CFactory.ansiC.system(this)
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ArrayList<String>.execute(): Int {
			return synchronized(this) {
				//Cli.exec(this.joinToString(" "))
				CFactory.ansiC.system(this.joinToString(" "))
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun InputStream.neoReader(): Reader {
			return synchronized(this) {
				this.neoInputStreamReader()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun InputStream.readLines(): List<String>{
			return this.neoReader().readLines()
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun InputStream.readText(): String {
			return this.neoReader().readText()
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> T.rync(block: (T) -> T): T {
			return block.invoke(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> T.sync(block: (T) -> Unit) {
			return block.invoke(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun OutputStream.neoWriter(): Writer {
			return synchronized(this) {
				this.neoOutputStreamWriter()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ArrayList<Closeable>.closes() {
			synchronized(this) {
				this.forEach {
					it.close()
				}
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.neoInputStreamIsResource(): InputStream? {
			return synchronized(this) {
				this::class.java.classLoader.getResourceAsStream(this)
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun File.neoInputStreamIsResource(): InputStream? {
			return synchronized(this) {
				this.path.neoInputStreamIsResource()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.toChars(char: String): String {
			val s1 = StringBuilder()
			for (i in this.indices) {
				s1.append(char)
			}
			return s1.toString()
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Int.formString(char: String): String {
			val str = StringBuilder()
			for (i in 0 until this) {
				str.append(char)
			}
			return str.toString()
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Int.formString(char: Char): String {
			val str = StringBuilder()
			for (i in 0 until this) {
				str.append(char)
			}
			return str.toString()
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> Array<T>.toArrayList(): ArrayList<T> {
			return if (this.isNotEmpty()) {
				val a = kotlin.collections.arrayListOf<T>()
				a.addAll(this)
				a
			} else {
				kotlin.collections.arrayListOf()
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> List<T>.toArrayList(): ArrayList<T> {
			return if (this.isNotEmpty()) {
				val a = kotlin.collections.arrayListOf<T>()
				a.addAll(this)
				a
			} else {
				kotlin.collections.arrayListOf()
			}
		}

//	@JvmStatic
//	fun <T> Collection<T>.toMap(): Map<Int, T> {
//		val map = hashMapOf<Int, T>()
//		val array = arrayListOf<T>()
//		array.addAll(this)
//		for (i in 0 until array.size) {
//			map[i] = array[i]
//		}
//		return map
//	}

//	@JvmStatic
//	@Synchronized
//	fun <T> Array<T>.toMap(): Map<Int, T> {
//		return synchronized(this) {
//			val map = hashMapOf<Int, T>()
//			val array = arrayListOf<T>()
//			array.addAll(this)
//			for (i in 0 until array.size) {
//				map[i] = array[i]
//			}
//			map
//		}
//	}

//	@JvmStatic
//	inline fun <T> Collection<T>.org.myrtle.loop(block: (Map.Entry<Int, T>) -> Unit) {
//		synchronized(this) {
//			for (i in this.toMap()) block(i)
//		}
//	}

//	@JvmStatic
//	inline fun <T> Array<T>.org.myrtle.loop(block: (Map.Entry<Int, T>) -> Unit) {
//		synchronized(this) {
//			for (i in this.toMap()) block(i)
//		}
//	}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ResultSet.loop(block: (ResultSet) -> Unit) {
			this.use {
				while (it.next()) {
					block(it)
				}
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Int.loop(block: (Int) -> Unit) {
			for (i in 0 until this) {
				block(i)
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun UInt.loop(block: (UInt) -> Unit) {
			for (i in 0 until this.toInt()) {
				block(i.toUInt())
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Long.loop(block: (Long) -> Unit) {
			for (i in 0 until this) {
				block(i)
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ULong.loop(block: (ULong) -> Unit) {
			for (i in 0 until this.toLong()) {
				block(i.toULong())
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> Iterable<T>.loop(block: (T) -> Unit) {
			val iterator = this.iterator()
			while (iterator.hasNext()) {
				block(iterator.next())
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> Iterable<T>.loop(block: (Int, T) -> Unit) {
			val iterator = this.iterator()
			var i = 0
			while (iterator.hasNext()) {
				block(i++, iterator.next())
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> Array<T>.arrayLoop(block: (Int, T) -> Unit) {
			for (i in this.indices) {
				block(i, this[i])
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> ArrayList<T>.arrayLoop(block: (Int, T) -> Unit) {
			for (i in this.indices) {
				block(i, this[i])
			}
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> List<T>.listLoop(block: (Int, T) -> Unit) {
			for (i in this.indices) {
				block(i, this[i])
			}
		}

//	@JvmStatic
//	inline fun <T> List<T>.org.myrtle.loop(block: (Map.Entry<Int, T>) -> Unit) {
//		for (i in this.toMap()) block(i)
//	}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> Iterable<T>.toMap(): Map<Int, T> {
			val iterator = this.iterator()
			val map = kotlin.collections.hashMapOf<Int, T>()
			var i = 0
			while (iterator.hasNext()) {
				map[i] = iterator.next()
				i++
			}
			return map
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> Array<T>.toMap(): Map<Int, T> {
			val iterator = this.iterator()
			val map = kotlin.collections.hashMapOf<Int, T>()
			var i = 0
			while (iterator.hasNext()) {
				map[i] = iterator.next()
				i++
			}
			return map
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun <T> Iterator<T>.toMap(): Map<Int, T> {
			val map = kotlin.collections.hashMapOf<Int, T>()
			var i = 0
			while (this.hasNext()) {
				map[i] = this.next()
				i++
			}
			return map
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun Char.code():Int{
			return this.code
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ResultSet.toIResultSet(): IResultSet {
			return iResultSetOf(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun iResultSetOf(it: ResultSet): IResultSet = IResultSet(it)

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.cipherBase64(): ByteArray {
			return Base64.getEncoder().encode(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.cipherBase64ToString(): String {
			return Base64.getEncoder().encodeToString(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.decryptBase64(): ByteArray {
			return Base64.getDecoder().decode(this)
		}

//		@JvmStatic
//		@Suppress("NOTHING_TO_INLINE")
//		inline fun String.cipherHexString(): String {
//			//return HexFormat.of().formatHex(this.toByteArray(Cache.charset))
//			return ByteKit.Hex.to(this)
//		}

//		@JvmStatic
//		@Suppress("NOTHING_TO_INLINE")
//		inline fun ByteArray.cipherHex(): String {
//			//return HexFormat.of().formatHex(this)
//			return ByteKit.Hex.to(this)
//		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun String.decryptHex(): ByteArray {
			return HexFormat.of().parseHex(this)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.formString(charset: Charset): String {
			return String(this, charset)
		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.formString(): String {
			return String(this, Charset.defaultCharset())
		}

//		@JvmStatic
//		@Suppress("NOTHING_TO_INLINE")
//		inline fun String.cipherHexStings(): List<String> {
//			val chars = this.toCharArray()
//			val array = kotlin.collections.arrayListOf<String>()
//			for (i in chars) {
//				array.add(i.toString().cipherHexString())
//			}
//			return array
//		}

//		@JvmStatic
//		@Suppress("NOTHING_TO_INLINE")
//		inline fun List<String>.decryptHexStrings(): String {
//			val array = kotlin.collections.arrayListOf<String>()
//			for (i in this) {
//				array.add(i.decryptHexString())
//			}
//			return array.joinToString("")
//		}

//		@JvmStatic
//		@Suppress("NOTHING_TO_INLINE")
//		inline fun String.decryptHexString(): String {
//			//return String(HexFormat.of().parseHex(this), Cache.charset)
//			return ByteKit.Hex.fromString(this)
//		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun ByteArray.reString(): String {
			return String(this, Charset.defaultCharset())
		}

//		@JvmStatic
//		@Suppress("NOTHING_TO_INLINE")
//		inline fun String.cipherBinary(): List<String> {
//			val array = kotlin.collections.arrayListOf<String>()
//			for (i in this.toCharArray()) {
//				val charAsInt = i.toInt()
//				var bin = Int.toBinaryString(charAsInt)
//				while (bin.length < 8) {
//					bin = "0$bin"
//				}
//				array.add(bin)
//			}
//			return array
//		}

		@JvmStatic
		@Suppress("NOTHING_TO_INLINE")
		inline fun List<String>.decryptBinary(): String {
			val result = StringBuilder()
			var i = 0
			while (i < this[0].length) {
				// 每8位一组
				if (i + 8 > this[0].length) {
					break // 如果剩余不足8位，结束循环
				}
				val eightBits = this[0].substring(i, i + 8)
				val decimalValue = eightBits.toInt(2) // 转换为十进制
				val character = decimalValue.toChar() // 转换为字符
				result.append(character)
				i += 8
			}
			return result.toString()
		}
	}
}