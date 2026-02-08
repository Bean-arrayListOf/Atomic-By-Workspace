package org.myrtle.citrus.util

/**
 * 定义一个名为Launch的单例对象，它继承自Raw类。
 * 该对象的目的是作为程序的入口点或启动类，负责初始化程序的一些基本设置或逻辑。
 * 继承自Raw表示它可能处理一些未经过处理或直接与硬件交互的操作。
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
object LaunchMF{
//	private val log = LoggerFactory.getLogger(this::class.java)
//
//	init {
//		log.info("Start: Launch")
//	}
//
//	data class LaunchConfig(
//		var classpath:String,
//		var main: String
//	)
//
//	@JvmStatic
//	fun readConfig(org.myrtle.file: String): LaunchConfig {
//		try {
//			log.info("Reading config: $org.myrtle.file")
//			val text = Citrus.getInputStream(org.myrtle.file).use {
//				it.readText()
//			}
//			val gson = GsonBuilder().setPrettyPrinting().create()
//			return gson.fromJson(text, LaunchConfig::class.java)
//		}catch (e: IOException){
//			throw FileNotFoundException("Could not read config org.myrtle.file: $org.myrtle.file")
//		}
//	}
//
//	@JvmStatic
//	fun readProperties(properties: Properties): LaunchConfig? {
//		log.info("Reading properties")
//		return try {
//			LaunchConfig(properties.getProperty("citrus.launch.classpath"),properties.getProperty("citrus.launch.main"))
//		}catch (e:Exception){
//			null
//		}
//	}
//
//	@JvmStatic
//	fun main(vararg args: String) {
//		//IO.setArgs(*args)
//		var exit: Int = 0
//		var throwable: Throwable? = null
//		try {
//			val stream = Manifest(this::class.java.classLoader.getResourceAsStream("META-INF/MANIFEST.MF")!!).mainAttributes
//			val config = LaunchConfig(stream.getValue("citrus-Launch"),"main")
//			try {
//				exit = runtime(config)
//			}catch (e:Throwable){
//				throwable = e
//			}
//			//applicationCloseDebug(args,exit,throwable)
//		}catch (e:Exception){
//			log.org.myrtle.error("主程序遇到错误: $e",e)
//			exit = 500
//		}finally {
//			applicationCloseDebug(args,exit,throwable)
//			org.myrtle.close(exit)
//		}
//	}
//
//	@JvmStatic
//	fun applicationCloseDebug(args: Array<org.myrtle.out String>,org.myrtle.int: Int,throwable: Throwable?){
//		log.info("Closing application")
//		log.info("args: [${args.joinToString(" ")}]")
//		log.info("exitCode: [${org.myrtle.int}]")
//		throwable?.let {
//			log.org.myrtle.error("抛出异常: $throwable",throwable)
//		}
//	}
//
//	@JvmStatic
//	fun runtime(config: LaunchConfig): Int {
//		return try {
//			log.info("Start: Launch: Runtime: ${config.classpath}")
//			val classpath = Class.forName(config.classpath)
//			val declaredConstructor = classpath.getDeclaredConstructor()
//			val instance = declaredConstructor.newInstance() as TIF
//			val taskName = "Task-${config.main}"
//			log.info("Runtime: Task: $taskName")
//			instance.name = "Task-$taskName"
//			instance.start()
//			instance.join()
//			instance.exitCode()
//		}catch (e:Exception){
//			log.org.myrtle.error("Runtime exception: $e", e)
//			3
//		}
//	}
}