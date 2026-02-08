package org.myrtle.citrus.io

import org.myrtle.atomic.*
import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.Path
import java.util.*

class RamFileObject : FileObject {
	private val rootFile: File
	private val iStream: IStream
	private val oStream: OStream
	private val deleteOnClose: Boolean

	constructor(file: File,deleteOnClose: Boolean) {
		rootFile = file
		oStream = FOStream(file)
		iStream = FIStream(file)
		this.deleteOnClose = deleteOnClose
		val t = Thread{
			//val log = LoggerFactory.getLogger("CCT On ${this::class.java.typeName}")
			run {
				try {
					//log.info("CCT: ${this::class.java.typeName} Closing")
					close()
				}catch (e: Throwable){
					e.printStackTrace()
				}
			}
		}
		t.name = "CCT: ${this::class.java.typeName}(${UUID.randomUUID().toString().uppercase()})"
		Runtime.getRuntime().addShutdownHook(t)
	}

	constructor(iStream: IStream,file: File,deleteOnClose: Boolean){
		rootFile = file
		this.oStream = FOStream(file)
		this.iStream = FIStream(file)
		iStream.transferTo(oStream)
		this.deleteOnClose = deleteOnClose
		val t = Thread{
			//val log = LoggerFactory.getLogger("CCT On ${this::class.java.typeName}")
			run {
				try {
					//log.info("CCT: ${this::class.java.typeName} Closing")
					close()
				}catch (e: Throwable){
					e.printStackTrace()
				}
			}
		}
		t.name = "CCT: ${this::class.java.typeName}(${UUID.randomUUID().toString().uppercase()})"
		Runtime.getRuntime().addShutdownHook(t)
	}

	override fun close(){
		this.iStream.close()
		this.oStream.close()
		if(delete){
			rootFile.delete()
		}
	}

	fun getIStream(): IStream{
		return iStream
	}

	fun getOStream(): OStream{
		return oStream
	}

	override val file : File
		get() = this.rootFile

	override val path: Path
		get() = this.rootFile.toPath()

	override val strPath: String
		get() = this.rootFile.path

	override val absoluteFile: File
		get() = this.rootFile.absoluteFile

	override val absolutePath: String
		get() = this.rootFile.absolutePath

	override val canonicalFile: File
		get() = this.rootFile.canonicalFile

	override val canonicalPath: String
		get() = this.rootFile.canonicalPath

	override val createNewFile: Boolean
		get() = this.rootFile.createNewFile()

	override val delete: Boolean
		get() = this.rootFile.delete()

	override val exists: Boolean
		get() = this.rootFile.exists()

	override val isAbsolute: Boolean
		get() = this.rootFile.isAbsolute

	override val isDirectory: Boolean
		get() = this.rootFile.isDirectory

	override val isFile: Boolean
		get() = this.rootFile.isFile

	override val isHidden: Boolean
		get() = this.rootFile.isHidden

	override val lastModified: Long
		get() = this.rootFile.lastModified()

	override val length: Long
		get() = this.rootFile.length()

	override val name: String
		get() = this.rootFile.name

	override val parent: String
		get() = this.rootFile.parent

	override val parentFile: File
		get() = this.rootFile.parentFile

	override val suffixName: String
		get() {
			val list = ArrayList<String>()
			list.addAll(this.name.split("."))
			list.removeAt(1)
			return list.joinToString(".")
		}

	override val uri: URI
		get() = this.rootFile.toURI()

	override val url: URL
		get() = this.rootFile.toURI().toURL()

	override fun deleteOnExit() {
		return rootFile.deleteOnExit()
	}
}