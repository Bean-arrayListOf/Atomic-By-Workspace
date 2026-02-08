/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus.util

import java.io.File

/**
 * @author cat
 * @since 2025-07-15
 **/
class FFmpeg {
	private val ffmpeg: File
	private val codes = ArrayList<String>()

	constructor(ffmpegPath : File){
		ffmpeg = ffmpegPath
	}
	constructor(ffmpegPath : String){
		ffmpeg = File(ffmpegPath)
	}

	fun ss(arg: String): FFmpeg{
		codes.add("-ss $arg")
		return this
	}

	fun map(arg: String): FFmpeg{
		codes.add("-map $arg")
		return this
	}

	fun c(arg: String): FFmpeg{
		codes.add("-c $arg")
		return this
	}

	fun map_metadat(arg: String): FFmpeg{
		codes.add("-map_metadata $arg")
		return this
	}

	fun map_chapters(arg: String): FFmpeg{
		codes.add("-map_chapters $arg")
		return this
	}

	fun i(arg: String): FFmpeg{
		codes.add("-i $arg")
		return this
	}

	fun o(arg: String): FFmpeg{
		codes.add("$arg")
		return this
	}

	fun c_v(arg: String): FFmpeg{
		codes.add("-c:v $arg")
		return this
	}

	fun crf(arg: String): FFmpeg{
		codes.add("-crf $arg")
		return this
	}

	fun preset(arg: String): FFmpeg{
		codes.add("-preset $arg")
		return this
	}

	fun c_a(arg: String): FFmpeg{
		codes.add("-c:a $arg")
		return this
	}

	fun buildCode(): String{
		return "\"${ffmpeg.absolutePath}\" ${codes.joinToString(" ")}"
	}

}