package org.myrtle.citrus.lang

import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class TempSQLite : TempDB {
	private lateinit var connection: Connection
	private var tempDBPath = Paths.get(
		System.getProperty("java.io.tmpdir").toString(),
		"com.myrtle.citrus.lang.TempDB",
		"${Random().nextLong(System.currentTimeMillis())}.db"
	).toFile()

	init {
		connection = runBlocking {
			async {
				if (!tempDBPath.exists()) {
					tempDBPath.parentFile.mkdirs()
				}
			}.await()
			async {
				DriverManager.getConnection("jdbc:sqlite:${tempDBPath}")
			}.await()
		}
	}

	override fun getTemp(): File {
		return tempDBPath
	}

	@OptIn(DelicateCoroutinesApi::class)
	override fun copy(out: File) {
		GlobalScope.launch {
			connection.close()
			try {
				if (!out.exists()) {
					out.createNewFile()
				}
				FileInputStream(tempDBPath).use { fis ->
					FileOutputStream(out).use { fos ->
						val buffer = ByteArray(1024)
						var n = -1
						while (fis.read(buffer).also { n = it } != -1) {
							fos.write(buffer, 0, n)
							fos.flush()
						}
					}
				}
				connection = DriverManager.getConnection("jdbc:sqlite:${tempDBPath}")
			} catch (e: Exception) {
				connection = DriverManager.getConnection("jdbc:sqlite:${tempDBPath}")
				throw IOException("复制缓存失败.", e)
			}
		}
	}

	override fun getConnection(): Connection {
		return connection
	}

	override fun createTemp(file: File) {
		if (!tempDBPath.exists()) {
			tempDBPath.parentFile.mkdirs()
		}
		connection = DriverManager.getConnection("jdbc:sqlite:${file.absolutePath}")
		this.tempDBPath = file
	}

	override fun close() {
		connection.close()
		tempDBPath.deleteOnExit()
	}
}