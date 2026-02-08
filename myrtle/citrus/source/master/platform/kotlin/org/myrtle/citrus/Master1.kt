package org.myrtle.citrus

import org.myrtle.citrus.mapdb.MapDB
import java.io.FileInputStream
import java.sql.DriverManager

class Master1 {

	companion object{

		@JvmStatic
		fun main(args: Array<String>) {

			val db = MapDB(DriverManager.getConnection("jdbc:sqlite:db/1.db"))

			db.pushStream("1","2").use { out ->
				FileInputStream("/Users/cat/Documents/Raw/Source/Call Me Now/Call Me Now.m4a").use { input ->
					val buffer = ByteArray(1024)
					var count = 0
					while (input.read(buffer, 0, buffer.size).also { count = it } != -1) {
						out.write(buffer, 0, count)
					}
				}
			}

//			val factory = JSONObject()
//			val mapper = com.fasterxml.jackson.databind.ObjectMapper(factory)
//
//			val cur = File(ConfigGeneralPreset::class.java.protectionDomain.codeSource.location.path)
//
//			val curDir = cur.parentFile.absolutePath
//			val curName = cur.nameWithoutExtension
//			val curNameNotSuffix: String = curName.split(".")[0]
//
//			val config = GeneralDefault(
//				arrayListOf(
//					ConfigFile("config/.env", true, ConfigFileType.ENV),
//			ConfigFile("config/.env.properties", true, ConfigFileType.Properties),
//			ConfigFile("config/.env.json", true, ConfigFileType.JSON),
//			ConfigFile("config/.env.xml", true, ConfigFileType.XML),
//			ConfigFile("config/.env.ini", true, ConfigFileType.INI),
//			ConfigFile("config/.env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("config/.env.yml", true, ConfigFileType.YAML),
//			ConfigFile("config/.env.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile("config/env", true, ConfigFileType.ENV),
//			ConfigFile("config/env.properties", true, ConfigFileType.Properties),
//			ConfigFile("config/env.json", true, ConfigFileType.JSON),
//			ConfigFile("config/env.xml", true, ConfigFileType.XML),
//			ConfigFile("config/env.ini", true, ConfigFileType.INI),
//			ConfigFile("config/env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("config/env.yml", true, ConfigFileType.YAML),
//			ConfigFile("config/env.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile("config/canary.env", true, ConfigFileType.ENV),
//			ConfigFile("config/canary.properties", true, ConfigFileType.Properties),
//			ConfigFile("config/canary.env.properties", true, ConfigFileType.Properties),
//			ConfigFile("config/canary.json", true, ConfigFileType.JSON),
//			ConfigFile("config/canary.env.json", true, ConfigFileType.JSON),
//			ConfigFile("config/canary.xml", true, ConfigFileType.XML),
//			ConfigFile("config/canary.env.xml", true, ConfigFileType.XML),
//			ConfigFile("config/canary.ini", true, ConfigFileType.INI),
//			ConfigFile("config/canary.env.ini", true, ConfigFileType.INI),
//			ConfigFile("config/canary.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("config/canary.env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("config/canary.yml", true, ConfigFileType.YAML),
//			ConfigFile("config/canary.env.yml", true, ConfigFileType.YAML),
//			ConfigFile("config/canary.yaml", true, ConfigFileType.YAML),
//			ConfigFile("config/canary.env.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile(".env", true, ConfigFileType.ENV),
//			ConfigFile(".env.properties", true, ConfigFileType.Properties),
//			ConfigFile(".env.json", true, ConfigFileType.JSON),
//			ConfigFile(".env.xml", true, ConfigFileType.XML),
//			ConfigFile(".env.ini", true, ConfigFileType.INI),
//			ConfigFile(".env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile(".env.yml", true, ConfigFileType.YAML),
//			ConfigFile(".env.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile("env", true, ConfigFileType.ENV),
//			ConfigFile("env.properties", true, ConfigFileType.Properties),
//			ConfigFile("env.json", true, ConfigFileType.JSON),
//			ConfigFile("env.xml", true, ConfigFileType.XML),
//			ConfigFile("env.ini", true, ConfigFileType.INI),
//			ConfigFile("env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("env.yml", true, ConfigFileType.YAML),
//			ConfigFile("env.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile("\${rootDir}/config/.env", true, ConfigFileType.ENV),
//			ConfigFile("\${rootDir}/config/.env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/config/env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/config/.env.json", true, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/config/env.json", true, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/config/.env.xml", true, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/config/env.xml", true, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/config/.env.ini", true, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/config/env.ini", true, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/config/.env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/config/env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/config/.env.yml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/env.yml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/.env.yaml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/env.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile("\${rootDir}/.env", false, ConfigFileType.ENV),
//			ConfigFile("\${rootDir}/.env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/.env.json", false, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/env.json", false, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/.env.xml", false, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/env.xml", false, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/.env.ini", false, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/env.ini", false, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/.env.plist", false, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/env.plist", false, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/.env.yml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/env.yml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/.env.yaml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/env.yaml", false, ConfigFileType.YAML),
//
//			ConfigFile("\${rootDir}/config/\${name}.env", true, ConfigFileType.ENV),
//			ConfigFile("\${rootDir}/config/\${name}.env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/config/\${name}.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/config/\${name}.env.json", true, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/config/\${name}.json", true, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/config/\${name}.env.xml", true, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/config/\${name}.xml", true, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/config/\${name}.env.ini", true, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/config/\${name}.ini", true, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/config/\${name}.env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/config/\${name}.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/config/\${name}.env.yml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/\${name}.yml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/\${name}.env.yaml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/\${name}.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile("\${rootDir}/\${name}.env", false, ConfigFileType.ENV),
//			ConfigFile("\${rootDir}/\${name}.env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/\${name}.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/\${name}.env.json", false, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/\${name}.json", false, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/\${name}.env.xml", false, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/\${name}.xml", false, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/\${name}.env.ini", false, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/\${name}.ini", false, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/\${name}.env.plist", false, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/\${name}.plist", false, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/\${name}.env.yml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/\${name}.yml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/\${name}.env.yaml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/\${name}.yaml", false, ConfigFileType.YAML),
//
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env", true, ConfigFileType.ENV),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env.json", true, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.json", true, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env.xml", true, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.xml", true, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env.ini", true, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.ini", true, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.plist", true, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env.yml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.yml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.env.yaml", true, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/config/\${nameNotSuffix}.yaml", true, ConfigFileType.YAML),
//
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env", false, ConfigFileType.ENV),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.properties", false, ConfigFileType.Properties),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env.json", false, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.json", false, ConfigFileType.JSON),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env.xml", false, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.xml", false, ConfigFileType.XML),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env.ini", false, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.ini", false, ConfigFileType.INI),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env.plist", false, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.plist", false, ConfigFileType.XMLPropertyList),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env.yml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.yml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.env.yaml", false, ConfigFileType.YAML),
//			ConfigFile("\${rootDir}/\${nameNotSuffix}.yaml", false, ConfigFileType.YAML),
//			)
//			)
//
//			FileWriter("/Users/cat/Documents/share/Intellij-IDEA/Workspaces/Atomic-By-Workspace/myrtle/citrus/source/master/resources/1.ion").use {
//				mapper.writerWithDefaultPrettyPrinter().writeValue(it,config)
//				it.toString()
//			}

		}
	}

}