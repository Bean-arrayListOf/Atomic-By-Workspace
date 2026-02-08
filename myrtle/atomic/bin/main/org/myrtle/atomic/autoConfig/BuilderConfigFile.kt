package org.myrtle.atomic.autoConfig

import org.apache.commons.configuration2.AbstractConfiguration
import org.apache.commons.configuration2.CombinedConfiguration
import org.apache.commons.configuration2.builder.BuilderParameters
import org.apache.commons.configuration2.interpol.Lookup
import org.apache.commons.configuration2.tree.DefaultExpressionEngine

interface BuilderConfigFile {
	fun buildParameters(file: String,resourceFS: Boolean): BuilderParameters?
	fun addConfig(config: AbstractConfiguration, name: String)
	fun addConfigs(configs: Map<String, AbstractConfiguration>)
	fun getBuildConfig(): CombinedConfiguration
	fun addLookups(lookups: List<Lookup>)
	fun addLookup(lookup: Lookup)
	fun setExpressionEngine(engine: DefaultExpressionEngine)
	fun buildParameters(config: ConfigFile): BuilderParameters?
}