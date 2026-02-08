package org.myrtle.citrus.io

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("此类已弃用,将会在下一个版本删除", level = DeprecationLevel.WARNING)
class PackageBuilder {
	private var cache = CPackage()
	fun putConfig(key: String, value: String): PackageBuilder {
		cache.config[key] = value
		return this
	}

	fun setConfig(config: HashMap<String, String>): PackageBuilder {
		this.cache.config = config
		return this
	}

	fun setPackageSize(packageSize: Int): PackageBuilder {
		this.cache.setPackageSize(packageSize)
		return this
	}

	fun setInput(input: Path): PackageBuilder {
		this.cache.setInput(input)
		return this
	}

	fun setInput(input: File): PackageBuilder {
		this.cache.setInput(input.toPath())
		return this
	}

	fun setInput(input: String): PackageBuilder {
		this.cache.setInput(Paths.get(input))
		return this
	}

	fun setOutput(output: Path): PackageBuilder {
		this.cache.setOutput(output)
		return this
	}

	fun setOutput(output: File): PackageBuilder {
		this.cache.setOutput(output.toPath())
		return this
	}

	fun setOutput(output: String): PackageBuilder {
		this.cache.setOutput(Paths.get(output))
		return this
	}

	fun builder(): CPackage {
		return cache
	}
}