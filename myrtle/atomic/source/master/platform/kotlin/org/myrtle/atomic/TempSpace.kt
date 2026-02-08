package org.myrtle.atomic

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.AutoCloseable
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.io.path.Path

object TempSpace {
	@JvmStatic
	private val rootFile = File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString().uppercase());

	init {
		if (!rootFile.exists()) {
			rootFile.mkdirs()
		}

		//rootFile.deleteOnExit()
	}

	@JvmStatic
	fun getAsNew(file: String): File{
		return File(rootFile, file)
	}

	@JvmStatic
	fun openAsCreateInput(file: String): InputStream {
		return FileInputStream(getAsNew(file))
	}

	@JvmStatic
	fun openAsCreateOutput(file: String): OutputStream {
		return FileOutputStream(getAsNew(file))
	}

	@JvmStatic
	fun openAsCreateInput(iStream: InputStream,suffixName: String): InputStream {
		val file = Paths.get(rootFile.absolutePath, "Stream", UUID.randomUUID().toString().uppercase(), "${UUID.randomUUID().toString().uppercase()}$suffixName").toFile()
		FileOutputStream(file).use { os ->
			iStream.copyTo(os)
		}

		return FileInputStream(file)
	}

	@JvmStatic
	fun openAsCreateFile(iStream: InputStream,suffixName: String): File {
		val file = Paths.get(rootFile.absolutePath, "Stream", UUID.randomUUID().toString().uppercase(), "${UUID.randomUUID().toString().uppercase()}$suffixName").toFile()
		mkdirsAndFile(file)
		FileOutputStream(file).use { os ->
			iStream.copyTo(os)
		}

		return file
	}

	@JvmStatic
	fun mkdirsAndFile(files: File){
		files.parentFile.mkdirs()
		files.createNewFile()
	}

}