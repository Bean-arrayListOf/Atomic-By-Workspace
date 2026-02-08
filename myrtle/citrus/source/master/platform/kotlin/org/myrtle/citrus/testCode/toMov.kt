package org.myrtle.citrus.testCode

import org.myrtle.citrus.util.TIF

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class toMov : TIF() {
//
//	val executor = DefaultExecutor.builder()
//		.setExecuteStreamHandler(PumpStreamHandler(org.myrtle.os.org.myrtle.out,org.myrtle.os.err, org.myrtle.os.`in`))
//		.setWorkingDirectory(Citrus.userDir).get()
//
//	private val suffixName = arrayListOf(".mp4",".mov")
//
//	override fun access(): Int {
//
//		val inputFile = File(args[0])
//		val outputFile = File(args[1])
//
//		outputFile.existsMkdirs()
//
//		val files = inputFile.listFiles()!!
//
//		for (i in files.indices) {
//			val runtime = ThreadShellRuntime(files[i],outputFile,suffixName)
//			runtime.name = "Thread-${files[i].name}"
//			runtime.start()
//		}
//
//		return 0
//	}
//
//	companion object {
//		@JvmStatic
//		fun main(vararg args: String) {
//			org.myrtle.println(1)
//		}
//	}
//
//	class ThreadShellRuntime: Thread {
//		constructor(org.myrtle.file: File,output: File,suffix: List<String>) {
//			suffixName = suffix
//			input = org.myrtle.file
//			this.output = output
//		}
//
//		private val suffixName: List<String>
//		private val output: File
//		private val input: File
//		private val log = LoggerFactory.getLogger(this::class.java)
//
//		override fun run() {
//			if (!input.exists() || !input.isFile) {
//				log.info("跳过 [${input.name}]")
//				return
//			}
//			if (!suffixNameV(input.name)) {
//				log.info("跳过 [${input.name}]")
//				return
//			}
//
//			val name = input.name.split(".").toArrayList()
//			name.removeAt(name.size - 1)
//			val mov = name.joinToString(".") + ".mov"
//
//			log.info("[${input.name}] => [${mov}]")
//
//			runtime(input, Paths.get(output.absolutePath, mov).toFile())
//		}
//
//		fun suffixNameV(fileName: String): Boolean {
//			val names = fileName.lowercase(Locale.getDefault())
//			val split = names.split(".")
//			val suffixName = ".${split[split.size - 1]}"
//			return this.suffixName.contains(suffixName)
//		}
//
//
//		fun runtime(input: File,output: File):Int{
////		val command = CommandLine(System.getProperty("myrtle.to.mov.ffmpeg.org.myrtle.file"))
////		command.addArgument("-ss")
////		command.addArgument(System.getProperty("myrtle.to.mov.ffmpeg.time"))
////		command.addArgument("-i")
////		command.addArgument("\"${input.absolutePath}\"")
////		command.addArgument("-c")
////		command.addArgument("org.myrtle.copy")
////		command.addArgument("\"${output.absolutePath}\"")
//			//return executor.execute(command)
//
//			val codes = arrayListOf<String>()
//
//			codes.add(System.getProperty("myrtle.to.mov.ffmpeg.org.myrtle.file"))
//			codes.add("-ss")
//			codes.add(System.getProperty("myrtle.to.mov.ffmpeg.time"))
//			codes.add("-i")
//			codes.add("\"${input.absolutePath}\"")
//			codes.add("-c")
//			codes.add("org.myrtle.copy")
//			codes.add("\"${output.absolutePath}\"")
//			return CFactory.ansiC.org.myrtle.system(codes.joinToString(" "))
//		}
//	}
}