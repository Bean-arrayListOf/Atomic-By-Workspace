package org.myrtle.atomic.autoConfig

import org.apache.commons.configuration2.*
import org.apache.commons.configuration2.builder.BuilderParameters
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Parameters
import org.apache.commons.configuration2.interpol.Lookup
import org.apache.commons.configuration2.plist.PropertyListConfiguration
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration
import org.apache.commons.configuration2.tree.DefaultExpressionEngine
import java.io.File

open class DefaultLoadConfigFile(
	private val cLoader: ClassLoader
) : LoadConfigFile, BuilderConfigFile {

	//private val log = LoggerFactory.getLogger(DefaultLoadConfig::class.java)
	//private val cLoader = Thread.currentThread().contextClassLoader
	private val configs = HashMap<String,AbstractConfiguration>()
	private val lookups = ArrayList<Lookup>()
	private var engine: DefaultExpressionEngine? = null

	override fun <T : FileBasedConfiguration> getConfiguration(bp: BuilderParameters, type: Class<T>): T{
		return FileBasedConfigurationBuilder(type).configure(bp).configuration
	}
	override fun loadYaml(bp: BuilderParameters): YAMLConfiguration {
		return getConfiguration(bp, YAMLConfiguration::class.java)
	}

	override fun loadPropertyList(bp: BuilderParameters): PropertyListConfiguration {
		return getConfiguration(bp, PropertyListConfiguration::class.java)
	}

	override fun loadXMLPropertyList(bp: BuilderParameters): XMLPropertyListConfiguration {
		return getConfiguration(bp, XMLPropertyListConfiguration::class.java)
	}

	override fun loadXML(bp: BuilderParameters): XMLConfiguration {
		return getConfiguration(bp, XMLConfiguration::class.java)
	}

	override fun loadJson(bp: BuilderParameters): JSONConfiguration {
		return getConfiguration(bp, JSONConfiguration::class.java)
	}

	override fun loadProperties(bp: BuilderParameters): PropertiesConfiguration {
		return getConfiguration(bp, PropertiesConfiguration::class.java)
	}

	override fun loadINI(bp: BuilderParameters): INIConfiguration {
		return getConfiguration(bp, INIConfiguration::class.java)
	}

	override fun loadXMLProperties(bp: BuilderParameters): XMLPropertiesConfiguration {
		return getConfiguration(bp, XMLPropertiesConfiguration::class.java)
	}

	override fun buildParameters(file: String,resourceFS: Boolean): BuilderParameters?{
		return try {
			if (resourceFS){
				val url = cLoader.getResource(file) ?: return null
				Parameters().fileBased().setURL(url)
			}else{
				val file = File(file)
				if (!file.exists()){
					return null
				}
				Parameters().fileBased().setURL(file.toURI().toURL())
			}.setThrowExceptionOnMissing(true).setEncoding("UTF-8")
		}catch (_:Exception){
			null
		}
	}

	override fun buildParameters(config: ConfigFile): BuilderParameters? {
		return try {
			if (config.classPathAt){
				val url = cLoader.getResource(config.file) ?: return null
				Parameters().fileBased().setURL(url)
			}else{
				val file = File(config.file)
				if (!file.exists()){
					return null
				}
				Parameters().fileBased().setURL(file.toURI().toURL())
			}.setThrowExceptionOnMissing(true).setEncoding("UTF-8")
		}catch (_:Exception){
			null
		}
	}

	override fun addConfig(config: AbstractConfiguration, name: String) {
		this.configs[name] = config
	}

	override fun addConfigs(configs: Map<String, AbstractConfiguration>) {
		this.configs.putAll(configs)
	}

	override fun getBuildConfig(): CombinedConfiguration {
		return CombinedConfiguration().apply {
			configs.forEach { (key,value) ->
				this.addConfiguration(value,key)
			}
			if (engine!=null){
				this.expressionEngine = engine
			}
			this.setDefaultLookups(lookups)
		}
	}

	override fun addLookups(lookups: List<Lookup>) {
		this.lookups.addAll(lookups)
	}

	override fun addLookup(lookup: Lookup) {
		this.lookups.add(lookup)
	}

	override fun setExpressionEngine(engine: DefaultExpressionEngine) {
		this.engine = engine
	}




}