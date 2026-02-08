package org.myrtle.atomic

import org.apache.commons.configuration2.*
import org.apache.commons.configuration2.builder.BuilderParameters
import org.apache.commons.configuration2.builder.fluent.Parameters
import org.apache.commons.configuration2.interpol.DefaultLookups
import org.apache.commons.configuration2.tree.DefaultExpressionEngine
import org.myrtle.atomic.autoConfig.*
import org.slf4j.LoggerFactory
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Duration
import java.util.*

open class SystemConfig : Config {
	private val log  = LoggerFactory.getLogger(this::class.java)
	private val cLoader = Thread.currentThread().contextClassLoader
	private val buildConfig = DefaultLoadConfigFile(cLoader)
	private val config: CombinedConfiguration = CombinedConfiguration()
	//private val ci : ConfigurationInterpolator = ConfigurationInterpolator()

	companion object{
		@JvmStatic
		val of: Config = SystemConfig(ConfigGeneralPreset.getPaths(), true)
	}

	internal constructor(paths: List<ConfigFile>, loadSystemConfig: Boolean = true){
		log.info("SystemConfig init...")

		//val buildConfig = DefaultLoadConfigFile(Thread.currentThread().contextClassLoader)

		if (loadSystemConfig){
			config.addConfiguration(BaseConfiguration(),"BaseConfiguration")
			//buildConfig.addConfig(BaseConfiguration(), "BaseConfiguration")
			log.info("Loaded [Base] configurations")

			config.addConfiguration(SystemConfiguration(),"SystemConfiguration")
			//buildConfig.addConfig(SystemConfiguration(), "SystemConfiguration")
			log.info("Loaded [System] configurations")

			config.addConfiguration(EnvironmentConfiguration(),"EnvironmentConfiguration")
			//buildConfig.addConfig(EnvironmentConfiguration(), "EnvironmentConfiguration")
			log.info("Loaded [Environment] configurations")
		}

		for (path in paths) {

			val fileConfig = loadFileConfig(path) ?: continue

			config.addConfiguration(fileConfig,path.toString())
			//buildConfig.addConfig(fileConfig,path.toString())
			//log.info("Loaded [${path.toString()}]")

		}
		
		config.expressionEngine = DefaultExpressionEngine.INSTANCE
		//buildConfig.setExpressionEngine(DefaultExpressionEngine.INSTANCE)
		config.setDefaultLookups(
			listOf(
				DefaultLookups.CONST.lookup,
				DefaultLookups.DATE.lookup,
				DefaultLookups.DNS.lookup,
				DefaultLookups.ENVIRONMENT.lookup,
				DefaultLookups.FILE.lookup,
				DefaultLookups.JAVA.lookup,
				DefaultLookups.LOCAL_HOST.lookup,
				DefaultLookups.PROPERTIES.lookup,
				DefaultLookups.RESOURCE_BUNDLE.lookup,
				DefaultLookups.SCRIPT.lookup,
				DefaultLookups.SYSTEM_PROPERTIES.lookup,
				DefaultLookups.URL.lookup,
				DefaultLookups.URL_DECODER.lookup,
				DefaultLookups.URL_ENCODER.lookup,
				DefaultLookups.XML.lookup
			)
		)

		//config = buildConfig.getBuildConfig()

		log.info("SystemConfig init.OK")
	}

	private fun loadFileConfig(cf: ConfigFile): AbstractConfiguration? {
		try {

			val bp = getBuilderParameters(cf.file, cf.classPathAt)

			if (bp == null){
				return null
			}else{
				log.info("Loaded [${cf.toString()}]")
			}

			return when(cf.fileType){
				ConfigFileType.ENV -> buildConfig.loadProperties(bp)
				ConfigFileType.Properties -> buildConfig.loadProperties(bp)
				ConfigFileType.JSON -> buildConfig.loadJson(bp)
				ConfigFileType.XML -> buildConfig.loadXML(bp)
				ConfigFileType.INI -> buildConfig.loadINI(bp)
				ConfigFileType.YAML -> buildConfig.loadYaml(bp)
				ConfigFileType.PropertyList -> buildConfig.loadPropertyList(bp)
				ConfigFileType.XMLPropertyList -> buildConfig.loadXMLPropertyList(bp)
				ConfigFileType.XMLProperties -> buildConfig.loadXMLProperties(bp)
			}
		}catch (e:Exception){
			log.warn("Error loading config file: [${cf.toString()}]", e)
			return null
		}
	}

	@Suppress("NOTHING_TO_INLINE")
	private inline fun getBuilderParameters(file: String,resourceFS: Boolean): BuilderParameters?{
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
		}catch (e:Exception){
			log.warn(e.message,e)
			null
		}
	}

	override fun containsKey(key: String): Boolean = config.containsKey(key)

	override fun containsValue(value: Any): Boolean = config.containsValue(value)

	override fun <T> get(cls: Class<T>, key: String): T? {
		return try {
			config.get(cls, key)
		}catch (_:Exception){
			null
		}
	}

	override fun <T> get(cls: Class<T>, key: String, default: T): T {
		return try {
			config.get(cls, key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getBigDecimal(key: String): BigDecimal? {
		return try {
			config.getBigDecimal(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getBigDecimal(key: String, default: BigDecimal): BigDecimal {
		return try {
			config.getBigDecimal(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getBigInteger(key: String): BigInteger? {
		return try {
			config.getBigInteger(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getBigInteger(key: String, default: BigInteger): BigInteger {
		return try {
			config.getBigInteger(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getBoolean(key: String): Boolean? {
		return try {
			config.getBoolean(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getBoolean(key: String, default: Boolean): Boolean {
		return try {
			config.getBoolean(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getByte(key: String): Byte? {
		TODO("Not yet implemented")
	}

	override fun getByte(key: String, default: Byte): Byte {
		TODO("Not yet implemented")
	}

	override fun <T> getCollection(
		cls: Class<T>,
		key: String,
		target: Collection<T>
	): Collection<T>? {
		return try {
			config.getCollection(cls, key, target)
		}catch (_:Exception){
			null
		}
	}

	override fun <T> getCollection(
		cls: Class<T>,
		key: String,
		target: Collection<T>,
		default: Collection<T>
	): Collection<T> {
		return try {
			config.getCollection(cls, key, target, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getDouble(key: String): Double? {
		return try {
			config.getDouble(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getDouble(key: String, default: Double): Double {
		return try {
			config.getDouble(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getDuration(key: String): Duration? {
		return try {
			config.getDuration(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getDuration(key: String, default: Duration): Duration {
		return try {
			config.getDuration(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getEncodedString(
		key: String,
		decoder: ConfigurationDecoder
	): String? {
		return try {
			config.getEncodedString(key, decoder)
		}catch (_:Exception){
			null
		}
	}

	override fun getFloat(key: String): Float? {
		return try {
			config.getFloat(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getFloat(key: String, default: Float): Float {
		return try {
			config.getFloat(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getInt(key: String): Int? {
		return try {
			config.getInt(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getInt(key: String, default: Int): Int {
		return try {
			config.getInt(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getKeys(): Iterator<String?>? {
		return try {
			config.keys
		}catch (_:Exception){
			null
		}
	}

	override fun getKeys(prefix: String): Iterator<String?>? {
		return try {
			config.getKeys(prefix)
		}catch (_:Exception){
			emptyList<String>().iterator()
		}
	}

	override fun getKeys(
		prefix: String,
		delimiter: String
	): Iterator<String> {
		return try {
			config.getKeys(prefix,delimiter)
		}catch (_:Exception){
			emptyList<String>().iterator()
		}
	}

	override fun <T> getList(cls: Class<T>, key: String): List<T>? {
		return try {
			config.getList(cls,key)
		}catch (_:Exception){
			null
		}
	}

	override fun <T> getList(
		cls: Class<T>,
		key: String,
		default: List<T>
	): List<T> {
		return try {
			config.getList(cls,key)
		}catch (_:Exception){
			default
		}
	}

	override fun getLong(key: String): Long? {
		return try {
			config.getLong(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getLong(key: String, default: Long): Long {
		return try {
			config.getLong(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getProperties(key: String): Properties? {
		return try {
			config.getProperties(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getProperties(key: String, default: Properties): Properties {
		return try {
			config.getProperties(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getShort(key: String): Short? {
		return try {
			config.getShort(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getShort(key: String, default: Short): Short {
		return try {
			config.getShort(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun getString(key: String): String? {
		return try {
			config.getString(key)
		}catch (_:Exception){
			null
		}
	}

	override fun getString(key: String, default: String): String {
		return try {
			config.getString(key, default)
		}catch (_:Exception){
			default
		}
	}

	override fun <T : Enum<T>> getEnum(key: String,cls: Class<T>): T? {
		return try {
			config.getEnum(key,cls)
		}catch (_:Exception){
			null
		}
	}

	override fun <T : Enum<T>> getEnum(key: String,cls: Class<T>, default: T): T {
		return try {
			config.getEnum(key,cls, default)
		}catch (_:Exception){
			default
		}
	}

	fun getConfig(): CombinedConfiguration {
		return config
	}

	override fun addConfig(config: AbstractConfiguration, name: String) {
		this.config.addConfiguration(config, name)
	}

	override fun addConfig(file: ConfigFile) {
		//val parameters = buildConfig.buildParameters(file)
		val loadFileConfig = loadFileConfig(file)
		config.addConfiguration(loadFileConfig, file.toString())
	}
}