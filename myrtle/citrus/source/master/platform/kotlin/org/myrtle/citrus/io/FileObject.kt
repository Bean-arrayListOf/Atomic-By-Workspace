package org.myrtle.citrus.io

import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.Path

interface FileObject : AutoCloseable {
	val file: File
	val path: Path
	val strPath: String
	val absolutePath: String
	val name: String
	val suffixName: String
	val parent: String
	val parentFile: File
	val exists: Boolean
	val isAbsolute: Boolean
	val absoluteFile: File
	val canonicalPath: String
	val canonicalFile: File
	val url: URL
	val uri: URI
	val isDirectory: Boolean
	val isFile: Boolean
	val isHidden: Boolean
	val lastModified: Long
	val length: Long
	val createNewFile: Boolean
	val delete: Boolean
	fun deleteOnExit()
}