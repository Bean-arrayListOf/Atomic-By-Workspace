package org.myrtle.atomic

open class ImportResult {
	data class Success(val fileName: String, val albumName: String) : ImportResult()
	data class Error(val message: String) : ImportResult()
}