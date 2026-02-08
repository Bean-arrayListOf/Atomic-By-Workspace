package org.myrtle.citrus.io

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object Streams {
	@JvmStatic
	@Throws(IOException::class)
	fun copy(input: FileInputStream,output: FileOutputStream){
		val inputChannel = input.channel
		val outputChannel = output.channel
		inputChannel.transferTo(0, inputChannel.size(), outputChannel)
	}

	@JvmStatic
	@Throws(IOException::class)
	fun nowRandomFile(output: FileOutputStream,size: Long){
		val random = java.util.Random()
		val buffer = ByteArray(1)
		for (i in 0 until size) {
			random.nextBytes(buffer)
		}
		output.write(buffer)
		output.flush()
	}
}