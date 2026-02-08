package org.myrtle.atomic.io

import org.myrtle.atomic.SystemConfig
import java.io.Serial
import java.io.Serializable
import java.util.regex.Pattern

/**
 * 文件搜索配置
 * @author CitrusCat
 * @since 1.0.0
 * @date 2025/11/24
 * @param pattern 文件匹配正则
 * @param patternExclusion 是否排除模式
 * @param subfolder 子文件夹查找
 * @param includeHidden 是否包含隐藏文件
 * @param sortBy 排序模式
 * @param minSize 最小文件(-1为不启用)
 * @param maxSize 最大文件(-1为不启用)
 * @param modifiedAfter 最后文件修改时间(-1为不启用)
 * @param modifiedBefore 最早文件修改时间(-1为不启用)
 * @see Pattern
 * @see FileSort
 * @see Serializable
 * @see SystemConfig.of
 */
data class FindFilesOptions(
	/**
	 * 文件匹配正则
	 * @see Pattern
	 * @param pattern 文件匹配正则
	 */
	val pattern: Pattern? = null,
	/**
	 * 是否排除模式
	 * @param patternExclusion 是否排除模式
	 * @see Boolean
	 */
	val patternExclusion: Boolean = SystemConfig.of.getBoolean("org.myrtle.canary.io.FindFilesOptions.patternExclusion",false),
	/**
	 * 子文件夹查找
	 * @param subfolder 子文件夹查找
	 * @see Boolean
	 */
	val subfolder: Boolean = SystemConfig.of.getBoolean("org.myrtle.canary.io.FindFilesOptions.subfolder",false),
	/**
	 * 隐藏文件查找
	 * @param includeHidden 是否包含隐藏文件
	 * @see Boolean
	 */
	val includeHidden: Boolean = SystemConfig.of.getBoolean("org.myrtle.canary.io.FindFilesOptions.includeHidden",false),
	/**
	 * 排序模式
	 * @param sortBy 排序模式
	 * @see FileSort
	 */
	val sortBy :FileSort = SystemConfig.of.getEnum("org.myrtle.canary.io.FindFilesOptions.sortBy", FileSort::class.java,FileSort.NAME),
	/**
	 * 最小文件(-1为不启用)
	 * @param minSize 最小文件(-1为不启用)
	 * @see Long
	 */
	val minSize: Long = SystemConfig.of.getLong("org.myrtle.canary.io.FindFilesOptions.minSize",-1),
	/**
	 * 最大文件(-1为不启用)
	 * @param maxSize 最大文件(-1为不启用)
	 * @see Long
	 */
	val maxSize: Long = SystemConfig.of.getLong("org.myrtle.canary.io.FindFilesOptions.maxSize",-1),
	/**
	 * 最后文件修改时间(-1为不启用)
	 * @param modifiedAfter 最后文件修改时间(-1为不启用)
	 * @see Long
	 */
	val modifiedAfter: Long = SystemConfig.of.getLong("org.myrtle.canary.io.FindFilesOptions.modifiedAfter",-1),
	/**
	 * 最早文件修改时间(-1为不启用)
	 * @param modifiedBefore 最早文件修改时间(-1为不启用)
	 * @see Long
	 */
	val modifiedBefore: Long = SystemConfig.of.getLong("org.myrtle.canary.io.FindFilesOptions.modifiedBefore",-1)
	): Serializable{
	companion object {
		/**
		 * 序列化UID
		 * @see Serial
		 */
		@Serial
		private const val serialVersionUID: Long = 1860266419000334043
	}
}