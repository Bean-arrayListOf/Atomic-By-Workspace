package org.myrtle.atomic.autoConfig

import org.apache.commons.configuration2.*
import org.apache.commons.configuration2.builder.BuilderParameters
import org.apache.commons.configuration2.plist.PropertyListConfiguration
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration

interface LoadConfigFile {
	fun <T : FileBasedConfiguration> getConfiguration(bp: BuilderParameters, type: Class<T>): T
	fun loadYaml(bp: BuilderParameters): YAMLConfiguration
	fun loadPropertyList(bp: BuilderParameters): PropertyListConfiguration
	fun loadXMLPropertyList(bp: BuilderParameters): XMLPropertyListConfiguration
	fun loadXML(bp: BuilderParameters): XMLConfiguration
	fun loadJson(bp: BuilderParameters): JSONConfiguration
	fun loadProperties(bp: BuilderParameters): PropertiesConfiguration
	fun loadINI(bp: BuilderParameters): INIConfiguration
	fun loadXMLProperties(bp: BuilderParameters): XMLPropertiesConfiguration
}