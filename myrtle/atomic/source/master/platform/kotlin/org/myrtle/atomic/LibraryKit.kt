package org.myrtle.atomic

import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Paths
import java.util.UUID
import kotlin.random.Random

object LibraryKit {
	private val log = LoggerFactory.getLogger(this::class.java)

	@JvmStatic
	val tempDir: File = Paths.get(System.getProperty("java.io.tmpdir"),File(this::class.java.protectionDomain.codeSource.location.path).nameWithoutExtension,this::class.java.typeName,
		UUID.randomUUID().toString().uppercase(),"Native","Library").toFile().apply {
			if (!this.exists()) this.mkdirs()
			this.deleteOnExit()
			log.info("[LibraryKit]: [=CREATE=]: Create Temp Dir: [${this.absolutePath}] [deleteOnExit:true]")
		}

	@JvmStatic
	private val libraryMap: HashMap<String,String> = HashMap()

//	@JvmStatic
//	val resourceLibraryDir: String = SystemConfig.of.getString("oma.librarykit.resource.library.dir","lib")

	private val rld = "lib"

	@JvmStatic
	fun loadLibrary(cl: ClassLoader,name: String): String {
		val libraryName = "${name}-${getOSName()}-${getOSArch()}.${getOSSuffixName()}"
		log.info("[LibraryKit]: [=FORMAT=]: Format Library Name: [${name}] =[os:${getOSName()},arch:${getOSArch()},suffix:${getOSSuffixName()}]=> [${libraryName}]")

		val map = libraryMap[name]
		val path = if (map == null) {
			//val tFile = File(tempDir,libraryName)
			val tFile = File(tempDir,"${UUID.randomUUID().toString().uppercase()}.${getOSSuffixName()}.obj")
			//tFile.createNewFile()
			val fileA = tFile.absolutePath
			getStream(cl,libraryName).use { istream ->
				log.info("[LibraryKit]: [=READ=]: Read Resource Library File: [${rld}/${libraryName}]")
				FileOutputStream(tFile).use { fos ->
					log.info("[LibraryKit]: [=WRITE=]: Write Library File: [${fileA}]")
					istream.transferTo(fos)
				}
			}
			libraryMap[name] = fileA
			fileA
		}else{
			map
		}


		log.info("[LibraryKit]: [=LOAD=]: Load Library: [${path}]")
		System.load(path)
		return path
	}

	@JvmStatic
	fun load(cl: ClassLoader,resourcePath: String): String {
		val resource = cl.getResource(resourcePath) ?: throw Exception("Not Found Resource: $resourcePath")
		log.info("[LibraryKit]: [=GET=]: Get Resource File: [${resourcePath}]")

		val map = libraryMap[resourcePath]
		val path = if (map == null) {
			val tFile = File(tempDir,"${UUID.randomUUID().toString().uppercase()}.${getOSSuffixName()}.obj")
			val aFile = tFile.absolutePath
			resource.openStream().use { istream ->
				log.info("[LibraryKit]: [=READ=]: Read Resource Library File: [${resourcePath}]")
				FileOutputStream(tFile).use { fos ->
					log.info("[LibraryKit]: [=WRITE=]: Write Resource Library File: [${aFile}]")
					istream.transferTo(fos)
				}
			}
			aFile
 		}else{
			 map
		}

		log.info("[LibraryKit]: [=LOAD=]: Load Resource: [${path}]")
		System.load(path)
		return path
	}

	@JvmStatic
	fun load(resourcePath: String): String {
		return load(Thread.currentThread().contextClassLoader,resourcePath)
	}

	@JvmStatic
	fun loadLibrary(name: String): String {
		return loadLibrary(Thread.currentThread().contextClassLoader,name)
	}

	@JvmStatic
	fun getStream(cl: ClassLoader,name: String): InputStream{
		val resource = cl.getResource("${rld}/${name}") ?: throw Exception("Not Found Resource: lib/${name}")
		return resource.openStream()
	}

	@JvmStatic
	fun getOSArch(): String {
		val osArch = System.getProperty("os.arch")
		return if (osArch.contains("x86_64")){
			"amd64"
		}else if (osArch.contains("x86")){
			"i386"
		}else if (osArch.contains("aarch64")){
			"arm64"
		}else{
			throw Exception("Not Support OS")
		}
	}

	@JvmStatic
	fun getOSName(): String {
		val osName = System.getProperty("os.name").lowercase()
		return if (osName.contains("linux")){
			"linux"
		}else if (osName.contains("windows")){
			"windows"
		}else if (osName.contains("mac")){
			"Darwin"
		}else{
			throw Exception("Not Support OS")
		}
	}

	@JvmStatic
	fun getOSSuffixName(): String {
		return when(getOSName()){
			"linux" -> "so"
			"windows" -> "dll"
			"Darwin" -> "dylib"
			else -> throw Exception("Not Support OS")
		}
	}
}