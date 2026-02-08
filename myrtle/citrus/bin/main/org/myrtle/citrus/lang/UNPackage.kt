package org.myrtle.citrus.lang

import org.myrtle.citrus.io.CPackageBuilder
import org.myrtle.citrus.io.CPackageCompressionType
import org.myrtle.citrus.util.Citrus
import java.io.File
import java.nio.file.Paths

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object UNPackage {
	@JvmStatic
	fun auto(pack: File):File{
		if (!pack.isDirectory) {
			val temp = Citrus.temp.nowTempDir()
			CPackageBuilder().setInput(pack).setPackageSize(1024).setType(CPackageCompressionType.CBZip2).setOutput(temp).builder().unPackage()
			return Paths.get(temp.absolutePath).toFile()
		}
		return pack
	}
}