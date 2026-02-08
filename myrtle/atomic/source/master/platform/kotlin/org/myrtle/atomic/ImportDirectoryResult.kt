package org.myrtle.atomic

open class ImportDirectoryResult {
	data class Success(val total: Int, val success: Int, val albumName: String) : ImportDirectoryResult()
	data class Error(val message: String) : ImportDirectoryResult()
}