package org.myrtle.atomic.io

import java.io.File
import java.io.Serial
import java.io.Serializable

/**
 * FileSearchResult
 * @author CitrusCat
 * @date 2025/11/24
 * @description 文件搜索结果
 * @param files 搜索到的文件
 * @param totalFilesFound 搜索到的文件总数
 * @param totalDirectoriesSearched 搜索到的目录总数
 * @param searchDuration 搜索耗时
 * @param options 搜索选项
 * @see List
 * @see FindFilesOptions
 * @see Serializable
 * @see File
 * @since 1.0.0
 */
data class FileSearchResult(
	val files: List<File>,
	val totalFilesFound: Int,
	val totalDirectoriesSearched: Int,
	val searchDuration: Long,
	val options: FindFilesOptions
	) : Serializable{
	companion object {
		@Serial
		private const val serialVersionUID = 6551532269474955300
	}
}