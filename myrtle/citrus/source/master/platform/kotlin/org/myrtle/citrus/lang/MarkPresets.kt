package org.myrtle.citrus.lang

import org.slf4j.Marker
import org.slf4j.MarkerFactory

/**
 * 标记预设枚举类，定义了一系列日志级别的标记
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
enum class MarkPresets(val mark: Marker) {
    // 初始化日志级别的标记
    Init(MarkerFactory.getMarker("Init")),
    // 关闭日志级别的标记
    Close(MarkerFactory.getMarker("Close")),
    // 错误日志级别的标记
    Error(MarkerFactory.getMarker("Error")),
    // 警告日志级别的标记
    Warn(MarkerFactory.getMarker("Warn")),
    // 信息日志级别的标记
    Info(MarkerFactory.getMarker("Info")),
    // 调试日志级别的标记
    Debug(MarkerFactory.getMarker("Debug")),
    // 跟踪日志级别的标记
    Trace(MarkerFactory.getMarker("Trace")),
    // 致命错误日志级别的标记
    Fatal(MarkerFactory.getMarker("Fatal")),
    // 所有日志级别的标记
    All(MarkerFactory.getMarker("All")),
    // 无日志级别的标记
    None(MarkerFactory.getMarker("org.myrtle.None")),
    // 未知日志级别的标记
    Unknown(MarkerFactory.getMarker("org.myrtle.Unknown")),
    // 特殊标记，用于表示某种特定的日志样式或用途
    Cat(MarkerFactory.getMarker("【=◈︿◈=】"));

    /**
     * 根据标记名称获取对应的MarkPresets条目
     * 如果找不到对应的条目，则返回一个与指定名称匹配的新标记
     *
     * @param name 标记的名称
     * @return 对应的Marker对象
     */
    companion object {
        fun get(name: String): Marker {
            return MarkPresets.entries.firstOrNull { it.name == name }?.mark ?: MarkerFactory.getMarker(name)
        }
    }
}
