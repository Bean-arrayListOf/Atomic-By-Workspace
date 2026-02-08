package org.myrtle.citrus.util

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class CoreLaunch {
//	enum class RunMode{
//		TIF,Thread,Main
//	}
//
//	data class Core(
//		val classPath: String,
//		val mode: RunMode = RunMode.TIF,
//		val name: String = "TIF-Runtime",
//		val title: List<String> = listOf()
//	) : Serializable
//
//	data class CoreConfig(
//		val coreMap: HashMap<String,Core> = hashMapOf(),
//		val run: String
//	) : Serializable
//
//	companion object{
//
//		private val log = LoggerFactory.getLogger(this::class.java)
//		private val make = MarkerFactory.getMarker("CoreLaunch")
//
//		@JvmStatic
//		fun main(vararg args: String) {
//			//Citrus.bootApplication(this::class.java, *args)
//			val configFile = Citrus.jvmArgs.getString("citrus.core.launch.config")
//			if (configFile==null){
//				val e = NullPointerException("参数[citrus.core.launch.config]为空")
//				log.org.myrtle.error(make,e.message,e)
//				throw e
//			}
//			log.info(make,"读取配置文件[${configFile}]")
//			val inputStream = Citrus.getInputStream(configFile)
//			val config: CoreConfig = Gson().fromJson(inputStream.readText(),CoreConfig::class.java)
//			org.myrtle.println(config)
//			val uid = Citrus.jvmArgs.getString("citrus.core.launch.uid")
//				?: config.run
//			val core = config.coreMap[uid] ?: throw NullPointerException("core $uid not found")
//			//val pl = Citrus.nowDefaultPlatform()
//			log.info(make,"运行配置: [uid: ${uid},name: ${core.name}, mode: ${core.mode},cp: ${core.classPath}]")
//			when(core.mode){
//				RunMode.TIF -> {
//					log.info(make,"ITF mode")
//					val instance = pl.nowInstance<TIF>(core.classPath).instance
//					instance.name = core.name
//					instance.title.addAll(core.title)
//					instance.start()
//					instance.join()
//				}
//				RunMode.Thread -> {
//					log.info(make,"Thread mode")
//					val instance = pl.nowInstance<Thread>(core.classPath).instance
//					instance.name = core.name
//					instance.start()
//					instance.join()
//				}
//				RunMode.Main -> {
//					log.info(make,"Main mode")
//					val array = arrayOf(*args)
//					pl.nowMethod<Any>(pl.nowInstance<Any>(core.classPath),"main",array)
//				}
//			}
//		}
//	}
}