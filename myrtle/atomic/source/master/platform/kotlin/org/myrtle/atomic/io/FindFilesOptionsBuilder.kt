package org.myrtle.atomic.io

import org.myrtle.atomic.SystemConfig
import java.util.regex.Pattern

class FindFilesOptionsBuilder {
		private var pattern: Pattern? = null
		private var patternExclusion: Boolean = SystemConfig.of.getBoolean("org.myrtle.canary.io.FindFilesOptions.patternExclusion",false)
		private var subfolder: Boolean = SystemConfig.of.getBoolean("org.myrtle.canary.io.FindFilesOptions.subfolder",false)
		private var includeHidden: Boolean = SystemConfig.of.getBoolean("org.myrtle.canary.io.FindFilesOptions.includeHidden",false)
		private var sortBy: FileSort = FileSort.valueOf(SystemConfig.of.getString("org.myrtle.canary.io.FindFilesOptions.sortBy","NAME"))
		private var minSize: Long = -1
		private var maxSize: Long = -1
		private var modifiedAfter: Long = -1
		private var modifiedBefore: Long = -1

		companion object{
			@JvmStatic
			val notEnabled: Long = -1
		}

		fun pattern(pattern: Pattern) = apply { this.pattern = pattern }
		fun pattern(pattern: String) = apply { this.pattern = Pattern.compile(pattern) }
		fun suffix(suffixNames: List<String>) = apply { this.pattern = Pattern.compile("^[^.]*\\.(${suffixNames.joinToString("|")})$") }
		fun patternExclusion(patternExclusion: Boolean) = apply { this.patternExclusion = patternExclusion }
		fun subfolder(subfolder: Boolean) = apply { this.subfolder = subfolder }
		fun includeHidden(includeHidden: Boolean) = apply { this.includeHidden = includeHidden }
		fun sortBy(sortBy: FileSort) = apply { this.sortBy = sortBy }
		fun minSize(minSize: Long) = apply { this.minSize = minSize }
		fun maxSize(maxSize: Long) = apply { this.maxSize = maxSize }
		fun modifiedAfter(modifiedAfter: Long) = apply { this.modifiedAfter = modifiedAfter }
		fun modifiedBefore(modifiedBefore: Long) = apply { this.modifiedBefore = modifiedBefore }

		fun build(): FindFilesOptions {
			return FindFilesOptions(
				pattern = pattern,
				patternExclusion = patternExclusion,
				subfolder = subfolder,
				includeHidden = includeHidden,
				sortBy = sortBy,
				minSize = minSize,
				maxSize = maxSize,
				modifiedAfter = modifiedAfter,
				modifiedBefore = modifiedBefore
			)
		}
	}