package org.myrtle.citrus.io

import org.myrtle.citrus.db.MapStorageSQLite
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Paths

class CitrusArchive : AutoCloseable {
	private val storage: MapStorageSQLite
	private val BUFFER_SIZE: Int = 16000
	private val DATA_ARCHIVE_NAME: String = "Storage"
	private val skipName: List<String>
	constructor(path: String,skip: List<String>) {
		this.storage = MapStorageSQLite(path)
		this.skipName = skip
		storage.put("config","BUFFER_SIZE",BUFFER_SIZE.toString())
	}

	fun archiveElement(root: File,file: File) {
		val archiveElementName = file.absolutePath.replace(root.absolutePath+"/", "")

		FileInputStream(file).use { fis ->
			var n : Int = 0
			val buffer = ByteArray(BUFFER_SIZE)
			while (fis.read(buffer).also { n = it } != -1) {
				storage.add(DATA_ARCHIVE_NAME,archiveElementName,buffer)
			}
		}
	}

	fun findAllFiles(directory: File): List<File> {
		val files = mutableListOf<File>()
		if (directory.isDirectory) {
			val subFiles = directory.listFiles()
			if (subFiles!= null) {
				for (subFile in subFiles) {
					if (subFile.isFile) {
						if (!skipName.contains(subFile.name)) {
							files.add(subFile)
						}
					} else if (subFile.isDirectory) {
						// 递归调用，查找子文件夹中的文件
						files.addAll(findAllFiles(subFile))
					}
				}
			}
		}
		return files
	}

	fun archiveElements(root: File,dir: File) {
		val list = findAllFiles(dir)
		for (file in list) {
			archiveElement(root,file)
		}
	}

	fun listArchiveElements():List<String> {
		return storage.getKeys(DATA_ARCHIVE_NAME)
	}

	fun unArchiveElement(name: String,file: File) {
		if (!file.exists()) {
			file.parentFile.mkdirs()
			file.createNewFile()
		}

		FileOutputStream(file).use { fos ->
			storage.gets(DATA_ARCHIVE_NAME,name).forEach { bytes ->
				fos.write(bytes)
				fos.flush()
			}
		}
	}

	fun unArchiveElements(dir: File) {
		val list = listArchiveElements()
		for (element in list) {
			val out = Paths.get(dir.absolutePath,element).toFile()
			unArchiveElement(element,out)
		}
	}

	override fun close() {
		storage.close()
	}

}