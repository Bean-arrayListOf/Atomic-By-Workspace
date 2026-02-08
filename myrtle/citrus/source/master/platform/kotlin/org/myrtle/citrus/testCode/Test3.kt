package org.myrtle.citrus.testCode

import me.tongfei.progressbar.ProgressBar
import java.io.File
import javax.sound.sampled.AudioSystem

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Test3 {
	companion object{
		@JvmStatic
		fun main(vararg args: String) {
			val file = File(args[0])
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
		fun getFileName(file:File):String{
			val names = arrayListOf<String>()
			names.addAll(file.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
			names.removeAt(names.lastIndex)
			return names.joinToString(".")
		}
	}

}