package org.myrtle.atomic

import org.myrtle.atomic.io.FileSearchResult
import org.myrtle.atomic.io.FileSort
import org.myrtle.atomic.io.FindFilesOptions
import java.io.*
import java.net.URI
import java.net.URL
import java.util.*
import java.util.Locale.getDefault
import java.util.regex.Pattern

object IOKit {

	@JvmStatic
	val defaultBufferSize = SystemConfig.of.getInt("org.myrtle.canary.IOKit.defBufferSize",1024)

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun InputStream.copy(output: OutputStream) {
		this.transferTo(output)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun Reader.copy(output: Writer) {
		this.transferTo(output)
	}

	/**
	 * 为输入流中的数据执行给定操作的内联函数
	 *
	 * 此函数通过提供的输入流读取数据，并使用指定大小的缓冲区进行读取
	 * 对于缓冲区中的每个数据块，它将调用提供的[block]函数进行处理
	 * 这种方式适合于处理大量数据，因为它允许逐块处理数据，而不需要一次性加载所有数据到内存中
	 *
	 * @param inputStream 要读取的输入流
	 * @param bufferSize 缓冲区的大小，必须大于0
	 * @param block 处理缓冲区数据的函数，接收读取的字节数和缓冲区数组作为参数
	 * @throws IllegalArgumentException 如果缓冲区大小小于或等于0
	 */
	@Suppress("NOTHING_TO_INLINE")
	inline fun forStream(inputStream: InputStream, bufferSize: Int, block: (Int, ByteArray) -> Unit) {
		// 确保缓冲区大小为正数
		require(bufferSize > 0) { "Buffer size must be positive" }

		// 创建指定大小的缓冲区
		val buffer = ByteArray(bufferSize)
		// 从输入流中读取数据到缓冲区
		var n = inputStream.read(buffer)
		// 当读取到数据时（即n不为-1），继续处理
		while (n != -1) {
			// 调用提供的block函数处理缓冲区中的数据
			block(n, buffer)
			// 继续读取下一块数据
			n = inputStream.read(buffer)
		}
	}


	@Suppress("NOTHING_TO_INLINE")
	inline fun forStream(inputStream: InputStream, block: (Int, ByteArray) -> Unit) {
		forStream(inputStream, defaultBufferSize, block)
	}

	/**
	 * 从输入流中按块读取数据，并对每个数据块执行指定操作。
	 *
	 * @param inputStream 输入流，必须非空
	 * @param bufferSize 每次读取的缓冲区大小，必须大于 0
	 * @param block 对每个读取到的数据块执行的操作
	 */
	@Suppress("NOTHING_TO_INLINE")
	inline fun forStream(inputStream: InputStream, bufferSize: Int, block: (ByteArray) -> Unit) {
		// 确保缓冲区大小大于 0，否则抛出异常
		require(bufferSize > 0) { "Buffer size must be greater than 0" }

		// 创建一个指定大小的缓冲区
		val buffer = ByteArray(bufferSize)
		// 用于存储每次读取的字节数
		var readBytes: Int
		// 循环读取输入流中的数据，直到没有更多数据
		while (inputStream.read(buffer).also { readBytes = it } != -1) {
			// 如果读取到的数据大于 0，则执行指定操作
			if (readBytes > 0) {
				block(buffer)
			}
		}
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun forStream(inputStream: InputStream, block: (ByteArray) -> Unit) {
		forStream(inputStream, defaultBufferSize, block)
	}




	@Suppress("NOTHING_TO_INLINE")
	inline fun AutoCloseable.shutdown(){
		SystemKit.runtime.addShutdownHook(Thread( {
			run{
				try {
					//log.info("T!-ACSH")
					this.close()
				}catch (e: Exception){
					e.printStackTrace()
				}
			}
		},"ACSH-${this::class.java.typeName}-${UUID.randomUUID().toString().uppercase(getDefault())}"))
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun toBinary(clazz: Any,output: OutputStream){
		if (clazz is Serializable) {
			ObjectOutputStream(output).use {
				it.writeObject(clazz)
			}
		}else {
			throw NotSerializableException("Not Serializable")
		}
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun toBinary(clazz: Any): ByteArray{
		ByteArrayOutputStream().use {
			toBinary(clazz,it)
			return it.toByteArray()
		}
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun <T:Any> fromBinary(input: InputStream): T{
		ObjectInputStream(input).use {
			return it.readObject() as T
		}
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun <T:Any> fromBinary(bytes: ByteArray): T{
		return fromBinary(ByteArrayInputStream(bytes))
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun OutputStream.openWriter(): Writer{
		return OutputStreamWriter(this)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun InputStream.openReader(): Reader{
		return InputStreamReader(this)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun File.openWriter(): Writer{
		return FileWriter(this)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun File.openReader(): Reader{
		return FileReader(this)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun File.openIStream(): InputStream{
		return FileInputStream(this)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun File.openOStream(): OutputStream{
		return FileOutputStream(this)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun URL.openIStream(): InputStream{
		return this.openStream()
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun URI.openIStream(): InputStream{
		return this.toURL().openStream()
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun String.openRes():URL?{
		return SystemKit.classLoader.getResource(this)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun String.openResStream(): InputStream?{
		return this.openRes()?.openStream()
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun Reader.next(close: Boolean = true,block: (line: String) -> Unit){
		val br = BufferedReader(this)
		var line: String? = null
		while (br.readLine().also { line = it } != null){
			block(line!!)
		}
		if (close){
			this.close()
		}
	}

	/**
	 * 从Reader中获取指定行的文本。
	 * 此函数通过BufferedReader逐行读取文本，并在到达指定行号时返回该行的文本。
	 * 如果指定的行号超过文件行数，则返回null。
	 *
	 * @param line 需要获取的行号，从1开始计数。
	 * @return 指定行的文本，如果不存在则返回null。
	 */
	@Suppress("NOTHING_TO_INLINE")
	inline fun Reader.getLine(line: Int): String? {
		require(line > 0) { return null }

		var currentLine = 0
		BufferedReader(this).use { reader ->
			var currentText: String?
			while (reader.readLine().also { currentText = it } != null) {
				currentLine++
				if (currentLine == line) {
					return currentText
				}
			}
		}
		return null
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun autoClose(close: AutoCloseable){
		Runtime.getRuntime().addShutdownHook(Thread{
			run { try {
				close.close()
			}catch (e: Throwable){
				e.printStackTrace()
			} }
		})
	}

//	@Suppress("NOTHING_TO_INLINE")
//	inline fun File.getFiles(
//		pattern: Pattern? = null,
//		subfolder: Boolean = false,
//		includeHidden: Boolean = false,
//		sortBy: FileSort = FileSort.NAME
//	): List<File> {
//		return getFiles(this,pattern,subfolder, includeHidden,sortBy)
//	}

//	@Suppress("NOTHING_TO_INLINE")
//	inline fun File.getFiles(
//		pattern: Pattern? = null,
//		subfolder: Boolean = false,
//		includeHidden: Boolean = false,
//		noinline codeBlock: (File) -> Unit
//	) {
//		getFiles(this,pattern,subfolder,includeHidden,codeBlock)
//	}

//	@JvmStatic
//	@Suppress("NOTHING_TO_INLINE")
//	inline fun File.findFiles(options: FindFilesOptions): List<File>{
//		return findFiles(this,options)
//	}

	@JvmStatic
	fun getFiles(sourceFile: File, pattern: Pattern? = null, subfolder: Boolean = false, includeHidden: Boolean = false, sortBy : FileSort = FileSort.NAME
	): List<File> {
		val result = arrayListOf<File>()

		getFiles(sourceFile, pattern, subfolder, includeHidden) { file ->
			result.add(file)
		}

		// 根据指定方式排序
		return sortFiles(result,sortBy)
	}

	@JvmStatic
	fun getFiles(sourceFile: File,
	             pattern: Pattern? = null,
	             subfolder: Boolean = false,
	             includeHidden: Boolean = false,
	             codeBlock: (File) -> Unit
	) {
		// 安全检查
		if (!sourceFile.exists()) {
			throw IllegalArgumentException("目录不存在: ${sourceFile.absolutePath}")
		}

		if (!sourceFile.isDirectory) {
			return
		}

		// 使用队列进行广度优先搜索
		val queue: Queue<File> = LinkedList()
		queue.offer(sourceFile)

		while (queue.isNotEmpty()) {
			val currentDir = queue.poll()

			try {
				val files = currentDir.listFiles() ?: emptyArray()

				for (file in files) {
					// 跳过隐藏文件（如果需要）
					if (!includeHidden && file.isHidden) {
						continue
					}

					if (file.isFile) {
						// 检查文件名是否匹配正则表达式
						val matchesPattern = pattern?.matcher(file.name)?.find() ?: true

						if (matchesPattern) {
							codeBlock(file)
						}
					} else if (file.isDirectory && subfolder) {
						queue.offer(file)
					}
				}
			} catch (e: SecurityException) {
				// 如果没有权限访问目录，跳过并继续处理其他目录
				println("警告: 无法访问目录 ${currentDir.absolutePath}: ${e.message}")
				continue
			}
		}
	}

	@JvmStatic
	fun findFiles(directory: File, options: FindFilesOptions): List<File> {
		val result = arrayListOf<File>()
			findFiles(directory, options) { file ->
			result.add(file)
		}
		// 排序结果
		return sortFiles(result, options.sortBy)
	}

	@JvmStatic
	fun findFiles(directory: File, options: FindFilesOptions, macro: (File)-> Unit) {
		// 验证目录
		if (!directory.exists() || !directory.isDirectory) {
			return
		}
		val queue: Queue<File> = LinkedList()
		queue.offer(directory)

		// 使用队列进行广度优先搜索
		while (queue.isNotEmpty()) {
			val currentDir = queue.poll()

			try {
				val files = currentDir.listFiles() ?: emptyArray()

				for (file in files) {
					when {
						// 处理目录
						file.isDirectory && options.subfolder -> {
							queue.offer(file)
						}
						// 处理文件
						file.isFile -> {
							if (matchesCriteria(file, options)) {
								macro(file)
							}
						}
					}
				}
			} catch (e: SecurityException) {
				// 跳过无权限访问的目录
				println("警告: 无权限访问目录: ${currentDir.absolutePath}")
				continue
			}
		}
	}

	private fun matchesCriteria(file: File, options: FindFilesOptions): Boolean {
		// 检查隐藏文件
		if (!options.includeHidden && (file.isHidden || file.name.startsWith("."))) {
			return false
		}

		// 检查文件名模式
		if (!matchesPattern(file, options.pattern, options.patternExclusion)) {
			return false
		}

		// 检查文件大小
		if (!matchesSize(file, options.minSize, options.maxSize)) {
			return false
		}

		// 检查修改时间
		if (!matchesModifiedTime(file, options.modifiedAfter, options.modifiedBefore)) {
			return false
		}

		return true
	}

	/**
	 * 检查文件名是否匹配正则表达式模式
	 */
	private fun matchesPattern(file: File, pattern: Pattern?, patternExclusion: Boolean): Boolean {
		if (pattern == null) {
			return true
		}

		val matches = pattern.matcher(file.name).find()
		return if (patternExclusion) !matches else matches
	}

	/**
	 * 检查文件大小是否在指定范围内
	 */
	private fun matchesSize(file: File, minSize: Long, maxSize: Long): Boolean {
		val fileSize = file.length()

		if (minSize != -1L && fileSize < minSize) {
			return false
		}

		if (maxSize != -1L && fileSize > maxSize) {
			return false
		}

		return true
	}

	/**
	 * 检查文件修改时间是否在指定范围内
	 */
	private fun matchesModifiedTime(file: File, modifiedAfter: Long, modifiedBefore: Long): Boolean {
		val lastModified = file.lastModified()

		if (modifiedAfter != -1L && lastModified < modifiedAfter) {
			return false
		}

		if (modifiedBefore != -1L && lastModified > modifiedBefore) {
			return false
		}

		return true
	}

	/**
	 * 对文件列表进行排序
	 */
	private fun sortFiles(files: List<File>, sortBy: FileSort): List<File> {
		return when (sortBy) {
			FileSort.NAME -> files.sortedBy { it.name }
			FileSort.SIZE -> files.sortedBy { it.length() }
			FileSort.MODIFIED -> files.sortedBy { it.lastModified() }
			FileSort.PATH -> files.sortedBy { it.absolutePath }
			FileSort.EXTENSION -> files.sortedBy {
				it.extension.lowercase(Locale.getDefault())
			}
		}
	}

	/**
	 * 带详细结果的查找函数
	 */
	fun findFilesWithDetails(directory: File, options: FindFilesOptions): FileSearchResult {
		val startTime = System.currentTimeMillis()
		val files = findFiles(directory, options)
		val duration = System.currentTimeMillis() - startTime

		// 计算搜索的目录数量（估算）
		val dirCount = if (options.subfolder) {
			countDirectories(directory)
		} else {
			1
		}

		return FileSearchResult(
			files = files,
			totalFilesFound = files.size,
			totalDirectoriesSearched = dirCount,
			searchDuration = duration,
			options = options
		)
	}

	/**
	 * 统计目录数量
	 */
	private fun countDirectories(directory: File): Int {
		var count = 0
		val queue: Queue<File> = LinkedList()
		queue.offer(directory)

		while (queue.isNotEmpty()) {
			val currentDir = queue.poll()
			count++

			try {
				currentDir.listFiles()?.forEach { file ->
					if (file.isDirectory) {
						queue.offer(file)
					}
				}
			} catch (e: SecurityException) {
				// 跳过无权限目录
			}
		}

		return count
	}

	/**
	 * 查找重复文件（基于文件名和大小）
	 */
	fun findDuplicateFiles(directory: File, options: FindFilesOptions = FindFilesOptions()): Map<String, List<File>> {
		val files = findFiles(directory, options)

		return files.groupBy { "${it.name.lowercase(getDefault())}_${it.length()}" }
			.filter { it.value.size > 1 }
	}

	/**
	 * 查找空文件
	 */
	fun findEmptyFiles(directory: File, options: FindFilesOptions = FindFilesOptions()): List<File> {
		val emptyFileOptions = options.copy(
			minSize = 0,
			maxSize = 0
		)
		return findFiles(directory, emptyFileOptions)
	}

	/**
	 * 查找最近修改的文件
	 */
	fun findRecentFiles(
		directory: File,
		withinMillis: Long,
		options: FindFilesOptions = FindFilesOptions()
	): List<File> {
		val cutoffTime = System.currentTimeMillis() - withinMillis
		val recentOptions = options.copy(modifiedAfter = cutoffTime)
		return findFiles(directory, recentOptions)
	}

	/**
	 * 按扩展名分组文件
	 */
	fun groupFilesByExtension(directory: File, options: FindFilesOptions = FindFilesOptions()): Map<String, List<File>> {
		val files = findFiles(directory, options)
		return files.groupBy { it.extension.lowercase(getDefault()) }
	}

	fun getInputStream(url: String): InputStream {

		return try {
			when {
				url.startsWith("cp:") || url.startsWith("classpath:") || url.startsWith("class-path:") || url.startsWith("resource:") || url.startsWith("res:") || url.startsWith("classloader:") || url.startsWith("cl:") -> {
					val resourcePath = url.substringAfter(":").removePrefix("/")
					val classLoader = Thread.currentThread().contextClassLoader ?: this::class.java.classLoader
					classLoader.getResourceAsStream(resourcePath)
						?: throw IllegalArgumentException("Classpath resource not found: $resourcePath")
				}
				url.startsWith("file:") || url.startsWith("f:") -> {
					val filePath = url.substringAfter(":")
					val file = File(filePath)
					if (!file.exists()) throw IllegalArgumentException("File not found: $filePath")
					FileInputStream(file)
				}
				url.startsWith("http:") || url.startsWith("https:") -> {
					URL(url).openStream()
				}
				else -> {
					val file = File(url)
					if (file.exists()) {
						FileInputStream(file)
					} else {
						// 尝试classpath
						val classLoader = Thread.currentThread().contextClassLoader ?: this::class.java.classLoader
						classLoader.getResourceAsStream(url)
							?: throw IllegalArgumentException("Resource not found: $url")
					}
				}
			}
		} catch (e: Exception) {
			throw IllegalArgumentException("Failed to get input stream for URL: $url", e)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun openFile(file: String): File{
		return File(file)
	}

	@JvmStatic
	fun currentName(): String{
		return currentPath().name
	}

	@JvmStatic
	fun currentPath(): File{
		return File(this::class.java.protectionDomain.codeSource.location.path)
	}

	@JvmStatic
	fun currentParentPath(): File {
		return currentPath().parentFile
	}

	@JvmStatic
	fun getIterator(reader: Reader): Iterator<String>{
		return object: Iterator<String>{
			private val bReader = BufferedReader(reader)
			override fun hasNext(): Boolean {
				return bReader.ready()
			}
			override fun next(): String {
				return bReader.readLine()
			}
		}
	}





}