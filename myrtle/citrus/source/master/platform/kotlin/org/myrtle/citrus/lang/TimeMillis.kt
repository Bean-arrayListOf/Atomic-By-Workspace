package org.myrtle.citrus.lang

import java.io.Serializable

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("功能已弃用,将会在下一个版本删除", level = DeprecationLevel.WARNING)
object TimeMillis {
    enum class Type:Serializable{
        System,Calendar,Date,Instant,
    }
    @JvmStatic
    fun get(type: Type):Long{
        return when(type){
            Type.System -> {
                java.lang.System.currentTimeMillis()
            }
            Type.Calendar -> {
                val calendar: java.util.Calendar = java.util.Calendar.getInstance()
                calendar.timeInMillis
            }
            Type.Date -> {
                val date = java.util.Date()
                date.time
            }
            Type.Instant -> {
                java.time.Instant.now().epochSecond
            }
        }
    }
}