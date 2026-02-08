package org.myrtle.orcas.config

import java.io.File
import java.nio.file.Paths

class Config {
	val appFile: String = ""
	val appExecFile: String = ""
	val os: String = ""
	val unpackDir: File = Paths.get(System.getProperty("user.home"),".orcas.tmp").toFile()
}