package org.myrtle.citrus

import java.io.InputStream
import java.util.function.Consumer

object Master2 {
	@JvmStatic
	fun main(args: Array<String>) {
		val builder = ProcessBuilder()
		builder.command("/opt/homebrew/bin/ffmpeg","-i","/Users/cat/Movies/bilibili/32375111830/32375111830-1-30120.mov","-c:v","libx265","-crf","22","-preset","slow","-movflags","use_metadata_tags","-map_metadata","0","/Users/cat/Movies/bilibili/32375111830/1.mov")
		val process = builder.start()
		startStreamGobbler(process.inputStream, Consumer {println(it)})
		startStreamGobbler(process.errorStream, Consumer {println(it)})
		process.waitFor()
	}

	fun init(): () -> Unit {
		return {println("1")}
	}

	fun startStreamGobbler(iStream: InputStream, consumer: Consumer<String>){
		Thread{
			run {
//				BufferedReader(InputStreamReader(iStream)).use { read ->
//					var line: String?
//					 while (read.readLine().also { line = it } != null) {
//						 consumer.accept("[EXEC]: "+line!!)
//					 }
//				}

				iStream.use { read ->
					print("[EXEC]: ")
					val bytes = ByteArray(1024)
					while (true){
						consumer.accept(String(bytes))
					}
				}
			}
		}.start()
	}
}