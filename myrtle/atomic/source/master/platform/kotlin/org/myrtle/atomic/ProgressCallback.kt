package org.myrtle.atomic

fun interface ProgressCallback {
	fun onProgress(current: Int, total: Int, filePath: String)
}