package org.myrtle.citrus

import org.myrtle.atomic.AutoLog
import org.myrtle.atomic.IOKit
import org.myrtle.atomic.SystemConfig
import org.myrtle.atomic.io.FileSort
import org.myrtle.atomic.io.FindFilesOptions
import org.myrtle.citrus.JNA.CFactory
import org.slf4j.Logger
import java.io.File
import java.util.regex.Pattern

class Mas {
	companion object{
		@JvmStatic
		private val log: Logger = AutoLog
		@JvmStatic
		fun main(vararg args: String) {
			val source = SystemConfig.of.getString("source") ?: throw IllegalArgumentException("请设置参数:source")
			val target = SystemConfig.of.getString("target") ?: throw IllegalArgumentException("请设置参数:target")
			val sourceDir = File(source)
			val targetDir = File(target)

			val delete = SystemConfig.of.getBoolean("copy.to.delete",true)

			val pattern = Pattern.compile(".*\\.mp4", Pattern.CASE_INSENSITIVE)
			val initCode =
				$$"/opt/homebrew/bin/ffmpeg -loglevel error -ss 00:00:03.5 -i \"${INPUT_FILE}\" -map_metadata 0 -map 0 -c copy \"${OUTPUT_FILE}\""

			if(!targetDir.exists()){
				log.info("创建目录:{}", targetDir.absolutePath)
				targetDir.mkdirs()
			}

			val option = FindFilesOptions(
				// 结尾为.mp4文件不区分大小写
				pattern = pattern,
				// 忽略隐藏文件
				includeHidden = false,
				// 忽略子目录
				subfolder = false,
				// 排序模式
				sortBy = FileSort.NAME
			)

			val files = IOKit.findFiles(sourceDir,option)
			val errors = ArrayList<String>()

			for (i in files.indices){
				val sourceName = files[i].name
				val targetName = sourceName.replace(".mp4", ".mov")
				val targetFile = File(targetDir, targetName)
				val code = "INPUT_FILE=\"${files[i].absolutePath.replace("\"","\\\"")}\" && OUTPUT_FILE=\"${targetFile.absolutePath.replace("\"","\\\"")}\" && ${initCode}"

				val system = CFactory.ansiC.system(code)
				log.info("[${files.size}/${(i+1)}]: [${sourceName}] -> [${targetName}]] exitCode:[${system}]")
				if(system != 0){
					errors.add(sourceName)
				}else{
					if (delete){
						files[i].deleteOnExit()
					}
				}
			}

			println("错误文件:")
			for (i in errors.indices){
				println(errors[i])
			}
		}
	}
}