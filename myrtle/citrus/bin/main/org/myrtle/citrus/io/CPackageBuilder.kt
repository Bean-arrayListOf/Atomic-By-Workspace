package org.myrtle.citrus.io

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class CPackageBuilder {
	private var cache = CPackage()
	fun putConfig(key: String, value: String): CPackageBuilder {
		cache.config[key] = value
		return this
	}

	fun setConfig(config: HashMap<String, String>): CPackageBuilder {
		this.cache.config = config
		return this
	}

	fun setPackageSize(packageSize: Int): CPackageBuilder {
		this.cache.setPackageSize(packageSize)
		return this
	}

	fun setInput(input: Path): CPackageBuilder {
		this.cache.setInput(input)
		return this
	}

	fun setInput(input: File): CPackageBuilder {
		this.cache.setInput(input.toPath())
		return this
	}

	fun setInput(input: String): CPackageBuilder {
		this.cache.setInput(Paths.get(input))
		return this
	}

	fun setType(cct: CPackageCompressionType): CPackageBuilder {
		this.cache.setType(cct)
		return this
	}

	fun setType(cct: String): CPackageBuilder {
		this.cache.setType(CPackageCompressionType.valueOf(cct))
		return this
	}

	fun setOutput(output: Path): CPackageBuilder {
		this.cache.setOutput(output)
		return this
	}

	fun setOutput(output: File): CPackageBuilder {
		this.cache.setOutput(output.toPath())
		return this
	}

	fun setOutput(output: String): CPackageBuilder {
		this.cache.setOutput(Paths.get(output))
		return this
	}

	fun builder(): CPackage {
		return cache
	}
}