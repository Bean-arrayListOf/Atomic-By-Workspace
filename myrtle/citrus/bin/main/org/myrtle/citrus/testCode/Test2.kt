package org.myrtle.citrus.testCode

import org.myrtle.citrus.util.TIF

/**
 * 类Test2继承自TIF类，用于执行主逻辑
 * @see TIF
 *
 * @author CitrusCat
 * @since 2024/12/31
 */
class Test2 : TIF() {
	/**
	 * 主逻辑函数，main的直接调用者，基于Thread实现
	 * @return 0 = 运行成功, >0 = 运行失败 (返回值会直接传入System.exit(org.myrtle.int))
	 * @see TIF.core
	 * @see TIF.access
	 * @see TIF.log
	 * @see TIF.args
	 * @see TIF.fullArgs
	 * @see TIF.init
	 * @see TIF.close
	 * @see TIF.startTime
	 */
//	override fun access(): Int {
//		val inputFile = File(args[0])
//		val outputFile = File(args[1])
//
//		val inputFiles = inputFile.listFiles().toList()
//
//		for (i in inputFiles){
//			val names = i.name.split(".").toArrayList()
//			names.removeAt(names.size-1)
//			val name = names.joinToString(".")
//
//			val code = arrayListOf<String>()
//			code.add("/opt/homebrew/bin/ffmpeg")
//			code.add("-i")
//			code.add("\"${i.absolutePath}\"")
//			code.add("\"${outputFile.absolutePath+"/"+name+".tiff"}\'")
//		}
//
//		return 0
//	}
}