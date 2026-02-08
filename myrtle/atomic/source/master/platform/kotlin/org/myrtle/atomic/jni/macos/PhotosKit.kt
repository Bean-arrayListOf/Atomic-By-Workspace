package org.myrtle.atomic.jni.macos

import org.myrtle.atomic.TempSpace
import org.myrtle.atomic.cl
import java.io.File

object PhotosKit {

	private val addImageScript = getTempScriptAddImage()

	private fun getTempScriptAddImage(): File{
		val cl = Thread.currentThread().contextClassLoader
		return TempSpace.openAsCreateFile(cl.getResource("script/AddPhotosScript.scpt")!!.openStream(),".scpt")
	}

	fun addImage(imageFile: String,albumName: String): Int{
		val pb = ProcessBuilder("/bin/sh","-c","export \"image_path=\"${imageFile}\" && export album_name=\"$albumName\" && export script_path=\"$addImageScript\" && /usr/bin/osascript \${script_path} \${image_path} \${album_name}\"")

		val p = pb.start()

		p.inputStream.bufferedReader().use { reader ->
			reader.lineSequence().forEach { line ->
				println("输出: $line")
			}
		}

		p.errorStream.bufferedReader().use { reader ->
			reader.lineSequence().forEach { line ->
				println(line)
			}
		}

		return p.waitFor()

	}
}