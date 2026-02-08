package org.myrtle.citrus.io

import org.myrtle.citrus.PRTS.Companion.neoFileIsMkdirs
import java.io.FileOutputStream
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Connection

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class CUNPakcageThread//val log = Logger.getLogger(this::class.java)
	(
	var k: String,
	var v: Long,
	var connection: Connection,
	output: Path,
	var size: Int,
	var num: Int,
	cct: CPackageCompressionType,
	var name: String
) : Runnable {
	var status: Boolean = false
	var outputPath: Path = output
	var ck: CPackageKit<Any?>

	init {
		this.ck = CPackageKit(cct)
	}

	override fun run() {
		//_log.info("[ 进行 ]:并发线程[分片次数:${v}]: [线程:${num}]：[文件:${k}]")
		//val fos: FileOutputStream
		val path = Paths.get(outputPath.toFile().absolutePath, k).toFile().absoluteFile
		path.neoFileIsMkdirs()
		FileOutputStream(path, true).use { fos ->
			for (i in 0..<v) {
				connection.createStatement().use {
					it.executeQuery("select \"Data\" from \"{0xC:${this.name}}:{Data}:{${k}}\" where \"Index\" = ${i + 1};")
						.let { rs ->
							while (rs.next()) {
								fos.write(ck.decompress(rs.getBytes("Data"), size))
								fos.flush()
							}
						}
				}
			}
		}
		status = true
		//_log.info("[ 完成 ]:并发线程[分片次数:${v}]: [线程:${num}]：[文件:${k}]")
	}
}