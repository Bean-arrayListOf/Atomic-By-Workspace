
/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus.lang

import org.myrtle.atomic.*

/**
 * 计算代码运行时间
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
class TimeKeeping {
	private var start: i64 = 0
	fun start() {
		start = System.currentTimeMillis()
	}
	fun end(): i64 {
		return System.currentTimeMillis() - start
	}
}