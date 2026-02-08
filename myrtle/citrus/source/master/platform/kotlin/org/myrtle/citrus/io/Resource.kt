package org.myrtle.citrus.io

import org.myrtle.atomic.HashType
import org.myrtle.citrus.PRTS.Companion.neoBufferedReader
import org.myrtle.citrus.PRTS.Companion.neoInputStreamReader
import org.myrtle.citrus.lang.StochasticThread
import java.io.*
import java.net.URL
import java.net.URLClassLoader
import java.util.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class Resource {
	private val resource: String
	private var classLoader: URLClassLoader? = null
	private var copyPath: File? = null

	companion object {
		@JvmStatic
		fun now(urls: Array<URL>, resourcePath: String): Resource {
			return Resource(urls, resourcePath)
		}

		@JvmStatic
		fun now(resourcePath: File): Resource {
			return Resource(resourcePath)
		}

		@JvmStatic
		fun now(resourcePath: String): Resource {
			return Resource(resourcePath)
		}
	}

	constructor(urls: Array<URL>, resourcePath: String) {
		this.resource = resourcePath
		try {
			classLoader = URLClassLoader(urls)
		} catch (e: Exception) {
			throw FileNotFoundException("MC-IO-Res-000002: Resource not found: $resource")
		}
	}

	constructor(resourcePath: String) {
		this.resource = resourcePath
	}

	constructor(resourcePath: File) {
		this.resource = resourcePath.path
	}

	open fun getStream(): InputStream {
		if (classLoader == null) {
			return this.javaClass.classLoader.getResourceAsStream(resource)
				?: throw FileNotFoundException("MC-IO-Res-000001: org.myrtle.citrus.testCode.Resource not found: $resource")
		}
		return classLoader!!.getResourceAsStream(resource)
			?: throw FileNotFoundException("MC-IO-Res-000001: org.myrtle.citrus.testCode.Resource not found: $resource")
	}

	open fun getReader(): Reader {
		return getStream().neoInputStreamReader().neoBufferedReader()
	}

	open fun copyTo(path: String): File {
		val file = File(path)
		FileOutputStream(file).use { fos ->
			getStream().use { input ->
				val buffer = ByteArray(1024)
				var n = -1
				while (input.read(buffer).also { n = it } != -1) {
					fos.write(buffer, 0, n)
					fos.flush()
				}
			}
		}
		return file
	}

	open fun getFile(): File {
		if (copyPath == null) {
			copyPath = File.createTempFile(
				Base64.getEncoder().encodeToString(StochasticThread().nextHash(HashType.SHA512)), ".temp"
			)
			FileOutputStream(copyPath!!).use { fos ->
				getStream().use { input ->
					val buffer = ByteArray(1024)
					var n = -1
					while (input.read(buffer).also { n = it } != -1) {
						fos.write(buffer, 0, n)
						fos.flush()
					}
				}
			}
		}
		return copyPath!!
	}

}