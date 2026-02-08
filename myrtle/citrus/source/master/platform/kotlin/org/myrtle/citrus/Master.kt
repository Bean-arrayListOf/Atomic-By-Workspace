package org.myrtle.citrus

import com.google.gson.annotations.SerializedName
import org.myrtle.atomic.*
import org.myrtle.atomic.io.FileSort
import org.myrtle.atomic.io.FindFilesOptions
import org.myrtle.atomic.io.FindFilesOptionsBuilder
import org.myrtle.citrus.JNA.CFactory
import org.myrtle.citrus.PRTS.Companion.readText
import me.tongfei.progressbar.ProgressBarBuilder
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Paths
import java.util.regex.Pattern

class Master {

	companion object{
		private val log = LoggerFactory.getLogger(this::class.java)
		//private val gson = GsonBuilder().setPrettyPrinting().setVersion(5.0).create()
		data class ArchiveToolBean(
			@SerializedName("source")
			val sourcePath: String,
			@SerializedName("target")
			val targetPath: String,
			@SerializedName("separator")
			val separator: String,
			@SerializedName("suffix")
			val suffixNames: List<String>,
			@SerializedName("to")
			val to: String
		)

		data class Result(
			@SerializedName("总计")
			var count: int = 0,
			@SerializedName("排除目录")
			var excludeDir: int = 0,
			@SerializedName("排除")
			var exclude: int = 0,
			@SerializedName("归档")
			var archive: int = 0,
			@SerializedName("跳过")
			var skip: Int = 0
		)
		@JvmStatic
		@Throws(Exception::class)
		fun main(vararg args: String) {



			val jsonString = org.myrtle.atomic.IO.getInputStream("config/ArchiveTool.json").readText()
			log.info("archiveTool: ${jsonString.replace("\n","").replace("\t","")}")
			val archiveTool = JsonKit.gson.fromJson(jsonString, ArchiveToolBean::class.java)

			val inputFile = File(archiveTool.sourcePath)
			val outputFile = File(archiveTool.targetPath)

			val snPattern = Pattern.compile("^[^.]*\\.(${archiveTool.suffixNames.joinToString("|")})$")
			val sepPattern = Pattern.compile(archiveTool.separator)

			val count = Result()

			val options = FindFilesOptions(
				pattern = snPattern,
				patternExclusion = false,
				subfolder = true,
				includeHidden = false,
				sortBy = FileSort.NAME,
				minSize = FindFilesOptionsBuilder.notEnabled,
				maxSize = FindFilesOptionsBuilder.notEnabled,
				modifiedAfter = FindFilesOptionsBuilder.notEnabled,
				modifiedBefore = FindFilesOptionsBuilder.notEnabled

			)

			val inputFiles = IO.findFiles(inputFile,options)
			//{ file ->
//				val names = arrayListOf<String>()
//				names.addAll(file.name.split(sepPattern))
//				names.removeAt(names.size - 1)
//				val output = Paths.get(outputFile.absolutePath,names.joinToString("/")).toFile()
//				if (!output.exists()){
//					output.mkdirs()
//				}
//
//				val outputFile1 = Paths.get(output.absolutePath,file.name).toFile()
//
//				if (!output.exists()){
//					log.info("归档: ${file.name}")
//					file.copyTo(outputFile1)
//				}else{
//					log.info("跳过: ${file.name}")
//				}
//
//			}

			count.count = inputFiles.size

			val pb = ProgressBarBuilder().setTaskName("AT").setInitialMax(inputFiles.size.toLong()).build()

			pb.step()

			for (ifi in inputFiles.indices) {
				val name = inputFiles[ifi].name
				val names = arrayListOf<String>()
				names.addAll(name.split(sepPattern))
				names.removeAt(names.size-1)
				val of = File(outputFile.absolutePath,names.joinToString("/"))
				//val of = File(outputFile.absolutePath)

				if (!of.exists()) {
					of.mkdirs()
				}

				count.archive++

				val outNames = arrayListOf<String>()
				outNames.addAll(name.split("."))

				val outName = "${outNames[0]}.${archiveTool.to}"
				//val outName = name

				//log.info("[${(ifi+1)}/${inputFiles.size}]: 归档: [${outName}].")
				pb.extraMessage = "A: $outName"

				val outf = Paths.get(of.absolutePath, outName).toFile()

				if (!outf.exists()){
//					FIStream(inputFiles[ifi]).use { fis ->
//						FileOutputStream(outf, false).use { fos ->
//							fis.transferTo(fos)
//						}
//					}
					CFactory.ansiC.system("OMP_NUM_THREADS=8 /opt/homebrew/bin/magick -define exif:all \"${inputFiles[ifi].absolutePath}\" \"${outf.absolutePath}\"")
				}else{
					count.skip++
				}

				pb.step()
			}

			pb.close()

//			println("结果:")
//			println("\t总共:\t\t[${zg}]")
//			println("\t排除目录:\t[${dir}]")
//			println("\t排除:\t\t[${pc}]")
//			println("\t归档:\t\t[${ar}]")
			println("\n"+ JsonKit.gson.toJson(count))


		}
	}

}