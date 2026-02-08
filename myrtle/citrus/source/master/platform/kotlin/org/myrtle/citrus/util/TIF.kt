package org.myrtle.citrus.util

/**
 * 程序启动线程
 * @see Thread
 *
 * @author CitrusCat
 * @since 2024/12/28
 */
abstract class TIF : Thread() {
//
//	/**
//	 * Title 内容
//	 * @author CitrusCat
//	 * @since 2024/12/31
//	 * @see ArrayList
//	 * @see String
//	 */
//	val title = arrayListOf<String>()
//
//	/**
//	 * 是否打印 title 默认 true
//	 * @author CitrusCat
//	 * @since 2024/12/31
//	 * @see Boolean
//	 */
//	var printTitle = true
//
//	/**
//	 * 日志记录器
//	 * @see Logger
//	 * @see LoggerFactory
//	 * @author arrayListOf
//	 * @since 24.10.05.01
//	 */
//	protected val log: Logger = LoggerFactory.getLogger(this::class.java)
//
//	/**
//	 * 启动时间
//	 * @see Long
//	 * @see System
//	 * @author arrayListOf
//	 * @since 24.10.05.01
//	 */
//	protected val startTime: Long = System.currentTimeMillis()
//
//	/**
//	 * 退出码
//	 * @see Int
//	 * @author arrayListOf
//	 * @since 24.10.05.01
//	 */
//	private var exit: Int = 0
//
//	val args: List<String> = Citrus.args
//	val core: Core = Citrus.nowDefaultPlatform()
//	val jvmArgs: JvmArgs = Citrus.jvmArgs
//
//	final override fun start() {
//		super.start()
//	}
//
//	/**
//	 * 运行
//	 * @author arrayListOf
//	 * @since 24.10.05.01
//	 */
//	final override fun run() {
//		if (printTitle) {
//			title.forEach {
//				Citrus.writerLine(it)
//			}
//			if (!Citrus.jvmArgs.getBoolean("test",false)) {
//				Citrus.writerLine(" :: =[${Version.PROJECT_NAME}]= :${Version.PROJECT_NAME}: =[TIF]> :$name: :: ")
//				Citrus.writerLine(" :: pv${Version.VERSION}, av${Version.API_VERSION}, bv${Version.BUILD_VERSION} ::")
//				Citrus.writerLine(" :: Powered by ${Version.PROJECT_NAME} ::")
//			}
//		}
//		val tif = marker.get("TIF")
//		log.info(tif,"Start")
//		try {
//			init()
//		}catch (_:NotImplementedError){
//			log.info("init 方法没有实现")
//		}
//		try {
//			main()
//		} catch (e: NotImplementedError) {
//			exit = access()
//		}
//		try {
//			org.myrtle.close()
//		}catch (_:NotImplementedError){
//			log.info("org.myrtle.close 方法没有实现")
//		}
//		val end = System.currentTimeMillis() - startTime
//		log.info(tif,"Close[${end}MS]")
//	}
//
//	open fun init(){
//		TODO("No Implemented")
//	}
//
//	open fun org.myrtle.close(){
//		TODO("No Implemented")
//	}
//
//	open fun main(){
//		TODO("No Implemented")
//	}
//
//	/**
//	 * 获取退出码
//	 * @return Int
//	 * @author arrayListOf
//	 * @since 24.10.05.01
//	 * @see Int
//	 */
//	fun exitCode(): Int {
//		return exit
//	}
//
//	abstract fun access(): Int
//
//	final override fun interrupt() {
//		super.interrupt()
//	}
//

}