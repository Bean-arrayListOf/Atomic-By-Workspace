package org.myrtle.citrus.util

import org.myrtle.atomic.*
import org.myrtle.citrus.PRTS.Companion.suffixName
import java.io.*
import java.net.URI
import java.net.URL
import java.nio.charset.Charset
import java.nio.file.Files
import kotlin.io.path.name

/**
 * @author cat
 * @since 2025-08-28
 **/
open class FileHyper : File {
	constructor(pathname: String) : super(pathname)
	constructor(parent: String?, child: String?) : super(parent, child)
	constructor(parentFile: File?, child: String?) : super(parentFile, child)
	constructor(uri: URI?) : super(uri)
	constructor(file: File) : super(file.absolutePath)

	open val suffixName: String
		get() {
			return if (this.name.contains(".")) {
				this.name.substringAfter(".")
			} else {
				this.name
			}
		}

	open val traverseDirectory: List<File>
		get() {
			val files = ArrayList<File>()
			Files.walk(this.toPath())
				.filter(Files::isRegularFile)
				.forEach{ files.add(it.toFile()) }
			return files
		}

	open val sha512: Bytes
		get() = HashKit.sha512(this)
	open val sha384: Bytes
		get() = HashKit.sha384(this)
	open val sha256: Bytes
		get() = HashKit.sha256(this)
	open val sha128: Bytes
		get() = HashKit.sha128(this)


	open fun openIStream(): IStream = FileInputStream(this)
	open fun openOStream(): OStream = FileOutputStream(this)
	open fun openOStream(append: bool): OStream = FileOutputStream(this,append)
	open fun openReader(): Reader = FileReader(this)
	open fun openReader(charset: Charset): Reader = FileReader(this,charset)
	open fun openWriter(): Writer = FileWriter(this)
	open fun openWriter(charset: Charset): Writer = FileWriter(this,charset)
	open fun openWriter(append: bool): Writer = FileWriter(this,append)
	open fun openWriter(charset: Charset,append: bool): Writer = FileWriter(this,charset,append)
	open fun moveTo(target: File, overwrite: bool): bool {
		this.copyTo(target, overwrite)
		if (!target.exists()) {
			return false
		}
		if (this.delete()) {
			return true
		}
		return false
	}
	open fun createFileIsMkdirs(): bool {
		if (!this.exists()) {
			this.parentFile.mkdirs()
			return this.createNewFile()
		}
		return false
	}

	open fun createFile(): bool = createFileIsMkdirs()

	override fun equals(obj: Any?): Boolean {
		if (obj == null) {
			return false
		}
		if (obj !is File) {
			return false
		}
		if (HashKit.sha512(this) == HashKit.sha512(obj)) {
			return true
		}
		return false
	}

	open fun append(text: str){
		openWriter(true).use {
			it.write(text)
		}
	}

	open fun append(bytes: ByteArray){
		openOStream(true).use {
			it.write(bytes)
		}
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + suffixName.hashCode()
		result = 31 * result + traverseDirectory.hashCode()
		return result
	}

	open fun itCopy(input: File){
		input.copyTo(this,true)
	}

	open fun itCopy(input: URL){
		input.openStream().use { iStream ->
			this.openOStream().use { oStream ->
				iStream.copyTo(oStream)
			}
		}
	}

	open fun itCopy(input: URI){
		itCopy(input.toURL())
	}

	open fun itCopy(input: str){
		itCopy(URI.create(input))
	}

	open fun itCopy(input: IStream){
		this.openOStream().use { oStream ->
			input.copyTo(oStream)
		}
	}

	open fun traverseDirectory(suffixes : List<String>,excludeOrSpecify : bool): List<File>{
		if (!this.exists()&&!this.isDirectory){
			return emptyList()
		}
		if (suffixes.isEmpty()) {
			return traverseDirectory
		}
		val efs = ArrayList<String>()
		suffixes.forEach {
			val e = it.replaceFirst(".","")
			efs.add(e)
		}
		val files = ArrayList<File>()
		Files.walk(this.toPath())
			.filter(Files::isRegularFile)
			.forEach{
				if (excludeOrSpecify) {
					if (!efs.contains(it.name.suffixName())) {
						files.add(it.toFile())
					}
				}else {
					if (efs.contains(it.name.suffixName())) {
						files.add(it.toFile())
					}
				}
			}
		return files
	}

	open fun traverseDirectory(suffixes : List<String>): List<File>{
		return traverseDirectory(suffixes,true)
	}


}