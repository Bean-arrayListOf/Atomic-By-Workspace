package org.myrtle.citrus.io

import org.myrtle.citrus.db.MapStorageSQLite
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class Punica {
	private val dbName = "Punica_Storage"
	fun archive(input: File, output: File){
		if (!output.exists()) {
			output.parentFile.mkdirs()
			output.createNewFile()
		}
		MapStorageSQLite(output).use { db ->
			val files = findFiles(input, listOf(".DS_Store"))

			for (file in files) {
				val name = file.absolutePath.replace(input.absolutePath+"/","")
				FileInputStream(file).use { isr ->
					putStream(db,name,isr)
				}
			}
		}
	}

	fun putStream(db: MapStorageSQLite,name: String,input: InputStream){
		val buffer = ByteArray(1024)
		var n = -1
		while (input.read(buffer,0, buffer.size).also { n=it } != -1) {
			db.add(dbName,name,buffer)
		}
	}

	fun findFiles(sourceDir: File, ignore: List<String>): List<File> {
		val result = mutableListOf<File>()

		if (!sourceDir.exists() || !sourceDir.isDirectory) {
			return result
		}

		sourceDir.walk()
			.onEnter { dir ->
				// 检查当前目录是否在忽略列表中
				!ignore.any { dir.name.equals(it, ignoreCase = true) }
			}
			.filter { file ->
				// 过滤掉目录和忽略的文件
				file.isFile && !ignore.any { file.name.equals(it, ignoreCase = true) }
			}
			.forEach { file ->
				result.add(file)
			}

		return result
	}

	fun unarchive(input: File, output: File){
		if (!output.exists()) {
			output.parentFile.mkdirs()
			//output.createNewFile()
		}
		MapStorageSQLite(input).use { db ->
			val files = db.getKeys(dbName)
			for (file in files) {
				val name = file.replace(dbName+"/","")
				val out = File(output,name)
				if (!out.exists()) {
					out.parentFile.mkdirs()
					out.createNewFile()
				}
				out.outputStream().use { os ->
					db.gets(dbName,file).forEach {
						os.write(it)
					}
				}
			}
		}
	}
}