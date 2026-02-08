package org.myrtle.atomic.autoConfig

import java.io.File

object ConfigGeneralPreset {
	private val cPath: File = File(ConfigGeneralPreset::class.java.protectionDomain.codeSource.location.path)
	private val cParent: File = cPath.parentFile
	private val cName: String = cPath.nameWithoutExtension
	private val cNameNotSuffix: String = cName.split(".")[0]
	private val paths: List<ConfigFile>

	init{
		val p = arrayListOf(
			ConfigFile("config/.env", true, ConfigFileType.ENV),
			ConfigFile("config/.env.properties", true, ConfigFileType.Properties),
			ConfigFile("config/.env.json", true, ConfigFileType.JSON),
			ConfigFile("config/.env.xml", true, ConfigFileType.XML),
			ConfigFile("config/.env.ini", true, ConfigFileType.INI),
			ConfigFile("config/.env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("config/.env.yml", true, ConfigFileType.YAML),
			ConfigFile("config/.env.yaml", true, ConfigFileType.YAML),

			ConfigFile("config/env", true, ConfigFileType.ENV),
			ConfigFile("config/env.properties", true, ConfigFileType.Properties),
			ConfigFile("config/env.json", true, ConfigFileType.JSON),
			ConfigFile("config/env.xml", true, ConfigFileType.XML),
			ConfigFile("config/env.ini", true, ConfigFileType.INI),
			ConfigFile("config/env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("config/env.yml", true, ConfigFileType.YAML),
			ConfigFile("config/env.yaml", true, ConfigFileType.YAML),

			ConfigFile("config/canary.env", true, ConfigFileType.ENV),
			ConfigFile("config/canary.properties", true, ConfigFileType.Properties),
			ConfigFile("config/canary.env.properties", true, ConfigFileType.Properties),
			ConfigFile("config/canary.json", true, ConfigFileType.JSON),
			ConfigFile("config/canary.env.json", true, ConfigFileType.JSON),
			ConfigFile("config/canary.xml", true, ConfigFileType.XML),
			ConfigFile("config/canary.env.xml", true, ConfigFileType.XML),
			ConfigFile("config/canary.ini", true, ConfigFileType.INI),
			ConfigFile("config/canary.env.ini", true, ConfigFileType.INI),
			ConfigFile("config/canary.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("config/canary.env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("config/canary.yml", true, ConfigFileType.YAML),
			ConfigFile("config/canary.env.yml", true, ConfigFileType.YAML),
			ConfigFile("config/canary.yaml", true, ConfigFileType.YAML),
			ConfigFile("config/canary.env.yaml", true, ConfigFileType.YAML),

			ConfigFile(".env", true, ConfigFileType.ENV),
			ConfigFile(".env.properties", true, ConfigFileType.Properties),
			ConfigFile(".env.json", true, ConfigFileType.JSON),
			ConfigFile(".env.xml", true, ConfigFileType.XML),
			ConfigFile(".env.ini", true, ConfigFileType.INI),
			ConfigFile(".env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile(".env.yml", true, ConfigFileType.YAML),
			ConfigFile(".env.yaml", true, ConfigFileType.YAML),

			ConfigFile("env", true, ConfigFileType.ENV),
			ConfigFile("env.properties", true, ConfigFileType.Properties),
			ConfigFile("env.json", true, ConfigFileType.JSON),
			ConfigFile("env.xml", true, ConfigFileType.XML),
			ConfigFile("env.ini", true, ConfigFileType.INI),
			ConfigFile("env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("env.yml", true, ConfigFileType.YAML),
			ConfigFile("env.yaml", true, ConfigFileType.YAML),

			ConfigFile("${cParent}/config/.env", true, ConfigFileType.ENV),
			ConfigFile("${cParent}/config/.env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/config/env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/config/.env.json", true, ConfigFileType.JSON),
			ConfigFile("${cParent}/config/env.json", true, ConfigFileType.JSON),
			ConfigFile("${cParent}/config/.env.xml", true, ConfigFileType.XML),
			ConfigFile("${cParent}/config/env.xml", true, ConfigFileType.XML),
			ConfigFile("${cParent}/config/.env.ini", true, ConfigFileType.INI),
			ConfigFile("${cParent}/config/env.ini", true, ConfigFileType.INI),
			ConfigFile("${cParent}/config/.env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/config/env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/config/.env.yml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/env.yml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/.env.yaml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/env.yaml", true, ConfigFileType.YAML),

			ConfigFile("${cParent}/.env", false, ConfigFileType.ENV),
			ConfigFile("${cParent}/.env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/.env.json", false, ConfigFileType.JSON),
			ConfigFile("${cParent}/env.json", false, ConfigFileType.JSON),
			ConfigFile("${cParent}/.env.xml", false, ConfigFileType.XML),
			ConfigFile("${cParent}/env.xml", false, ConfigFileType.XML),
			ConfigFile("${cParent}/.env.ini", false, ConfigFileType.INI),
			ConfigFile("${cParent}/env.ini", false, ConfigFileType.INI),
			ConfigFile("${cParent}/.env.plist", false, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/env.plist", false, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/.env.yml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/env.yml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/.env.yaml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/env.yaml", false, ConfigFileType.YAML),

			ConfigFile("${cParent}/config/${cName}.env", true, ConfigFileType.ENV),
			ConfigFile("${cParent}/config/${cName}.env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/config/${cName}.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/config/${cName}.env.json", true, ConfigFileType.JSON),
			ConfigFile("${cParent}/config/${cName}.json", true, ConfigFileType.JSON),
			ConfigFile("${cParent}/config/${cName}.env.xml", true, ConfigFileType.XML),
			ConfigFile("${cParent}/config/${cName}.xml", true, ConfigFileType.XML),
			ConfigFile("${cParent}/config/${cName}.env.ini", true, ConfigFileType.INI),
			ConfigFile("${cParent}/config/${cName}.ini", true, ConfigFileType.INI),
			ConfigFile("${cParent}/config/${cName}.env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/config/${cName}.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/config/${cName}.env.yml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/${cName}.yml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/${cName}.env.yaml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/${cName}.yaml", true, ConfigFileType.YAML),

			ConfigFile("${cParent}/${cName}.env", false, ConfigFileType.ENV),
			ConfigFile("${cParent}/${cName}.env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/${cName}.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/${cName}.env.json", false, ConfigFileType.JSON),
			ConfigFile("${cParent}/${cName}.json", false, ConfigFileType.JSON),
			ConfigFile("${cParent}/${cName}.env.xml", false, ConfigFileType.XML),
			ConfigFile("${cParent}/${cName}.xml", false, ConfigFileType.XML),
			ConfigFile("${cParent}/${cName}.env.ini", false, ConfigFileType.INI),
			ConfigFile("${cParent}/${cName}.ini", false, ConfigFileType.INI),
			ConfigFile("${cParent}/${cName}.env.plist", false, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/${cName}.plist", false, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/${cName}.env.yml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/${cName}.yml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/${cName}.env.yaml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/${cName}.yaml", false, ConfigFileType.YAML),

			ConfigFile("${cParent}/config/${cNameNotSuffix}.env", true, ConfigFileType.ENV),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.env.json", true, ConfigFileType.JSON),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.json", true, ConfigFileType.JSON),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.env.xml", true, ConfigFileType.XML),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.xml", true, ConfigFileType.XML),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.env.ini", true, ConfigFileType.INI),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.ini", true, ConfigFileType.INI),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.env.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.plist", true, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.env.yml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.yml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.env.yaml", true, ConfigFileType.YAML),
			ConfigFile("${cParent}/config/${cNameNotSuffix}.yaml", true, ConfigFileType.YAML),

			ConfigFile("${cParent}/${cNameNotSuffix}.env", false, ConfigFileType.ENV),
			ConfigFile("${cParent}/${cNameNotSuffix}.env.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/${cNameNotSuffix}.properties", false, ConfigFileType.Properties),
			ConfigFile("${cParent}/${cNameNotSuffix}.env.json", false, ConfigFileType.JSON),
			ConfigFile("${cParent}/${cNameNotSuffix}.json", false, ConfigFileType.JSON),
			ConfigFile("${cParent}/${cNameNotSuffix}.env.xml", false, ConfigFileType.XML),
			ConfigFile("${cParent}/${cNameNotSuffix}.xml", false, ConfigFileType.XML),
			ConfigFile("${cParent}/${cNameNotSuffix}.env.ini", false, ConfigFileType.INI),
			ConfigFile("${cParent}/${cNameNotSuffix}.ini", false, ConfigFileType.INI),
			ConfigFile("${cParent}/${cNameNotSuffix}.env.plist", false, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/${cNameNotSuffix}.plist", false, ConfigFileType.XMLPropertyList),
			ConfigFile("${cParent}/${cNameNotSuffix}.env.yml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/${cNameNotSuffix}.yml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/${cNameNotSuffix}.env.yaml", false, ConfigFileType.YAML),
			ConfigFile("${cParent}/${cNameNotSuffix}.yaml", false, ConfigFileType.YAML),
		)

		p.reverse()

		paths = p
	}

	fun getPaths(): List<ConfigFile> {
		return paths
	}
}