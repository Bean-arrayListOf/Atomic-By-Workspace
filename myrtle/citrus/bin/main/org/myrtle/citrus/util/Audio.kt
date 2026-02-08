package org.myrtle.citrus.util

import org.myrtle.atomic.StringKit.toLowerCase
import me.tongfei.progressbar.ProgressBar
import java.io.File
import javax.sound.sampled.AudioSystem
import kotlin.system.exitProcess

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object Audio {

	@JvmStatic
	fun main(vararg args: String) {
		if (args[0].toLowerCase() != "play") {
			exitProcess(1)
		}
		val file = File(args[1])
		val stream =
			AudioSystem.getAudioInputStream(file)
		val clip = AudioSystem.getClip()
		clip.open(stream)
		clip.start()
		val pb = ProgressBar.builder().setInitialMax(clip.microsecondLength).setTaskName(getFileName(file)).build()
		pb.start
		while (clip.isRunning){
			pb.stepTo(clip.microsecondPosition)
		}
		pb.close()
		stream.close()
	}

	fun getFileName(file: File):String{
		val names = arrayListOf<String>()
		names.addAll(file.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
		names.removeAt(names.lastIndex)
		return names.joinToString(".")
	}

}