package org.myrtle.atomic.io

/**
 * 文件排序方式
 * @author CitrusCat
 * @since 1.0.0
 * @date 2025/11/24
 * @param NAME 按文件名排序
 * @param SIZE 按文件大小排序
 * @param MODIFIED 按修改时间排序
 * @param PATH 按完整路径排序
 * @param EXTENSION 按扩展名排序
 */
enum class FileSort {
		NAME,           // 按文件名排序
		SIZE,           // 按文件大小排序
		MODIFIED,       // 按修改时间排序
		PATH,           // 按完整路径排序
		EXTENSION       // 按扩展名排序
	}