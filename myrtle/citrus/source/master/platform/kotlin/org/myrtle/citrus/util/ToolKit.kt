package org.myrtle.citrus.util

import java.text.SimpleDateFormat

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object ToolKit {
	val date: (String, Long) -> String = { date: String, time: Long ->
		SimpleDateFormat(date).format(time)
	}
	val dateAutoTime: (String) -> String = { date(it, System.currentTimeMillis()) }
	
}