
/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus.lang

import org.myrtle.atomic.SystemKit
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * 语言包工具类，用于从语言表中根据键获取语言内容，并格式化输出
 *
 * @constructor 通过语言表初始化语言包
 * @param table 语言表，包含语言键值对
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
class LanguageKit(table: LanguageTable) {

	companion object {
		@JvmStatic
		fun now(table: LanguageTable): LanguageKit {
			return LanguageKit(table)
		}
	}

	/**
	 * 语言表数据类，封装了语言键值对表
	 *
	 * @property table 语言键值对表
	 */
	data class LanguageTable(
		val table: Map<String, String>
	)

	// 私有化语言表，防止外部直接访问
	private val table: Map<String, String> = table.table

	/**
	 * 根据键获取语言内容，并使用提供的参数格式化输出
	 *
	 * @param key 语言键
	 * @param arg 格式化输出的参数
	 * @return 格式化后的语言字符串，若出现异常则返回null
	 */
	fun get(key: String, vararg arg: Any): String? {
		try {
			// 使用 ByteArrayOutputStream 捕获格式化后的输出
			return ByteArrayOutputStream().use { baos ->
				// 使用 PrintStream 将 ByteArrayOutputStream 转换为输出流
				PrintStream(baos).use { ps ->
					// 根据语言键获取语言模板，并使用参数格式化输出
					ps.printf(table[key], *arg)
					ps.flush()
				}
				// 将 ByteArrayOutputStream 中的内容转换为字符串返回
				String(baos.toByteArray(), SystemKit.encode)
			}
		} catch (e: Exception) {
			// 异常处理：打印堆栈跟踪，并返回null
			e.printStackTrace()
			return null
		}
	}
}
