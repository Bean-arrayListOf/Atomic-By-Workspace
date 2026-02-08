package org.myrtle.citrus.testCode

import org.myrtle.citrus.JNA.CFactory
import java.io.File
import java.nio.file.Paths

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Launch1 {
	companion object {

		@JvmStatic
		fun main(vararg args: String) {
			val ffmpeg = "/opt/homebrew/bin/ffmpeg"
			val input = File("/Users/javac/Downloads")
			val output = File("/Users/javac/Pictures/18")
			val list = File(input.absolutePath).listFiles()!!
			if (!output.exists()) {
				output.mkdirs()
			}
			for (file in list) {
				if (!file.isFile) {
					continue
				}
				if (file.name.indexOf(".mp4") == -1) {
					continue
				}
				file.exists()
				CFactory.ansiC.system(joinCode(ffmpeg, file, output))
			}
		}

		fun joinCode(ffmpeg: String, input: File, output: File): String {
			val codes = arrayListOf<String>()
			codes.add(ffmpeg)
			codes.add("-ss")
			codes.add("00:00:03.500")
			codes.add("-i")
			codes.add("'${input.absolutePath}'")
			codes.add("-c")
			codes.add("copy")
			codes.add("'${Paths.get(output.absolutePath, input.name).toFile().absolutePath}'")
			return codes.joinToString(" ")
		}
	}
}