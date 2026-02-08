package org.myrtle.citrus.util

import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory

/**
 * TimedTask 类用于执行定时任务，继承自 Thread 类。
 * 该类通过指定的平台工厂对象来关闭线程缓存和定时任务。
 *
 * @param platformFactory 平台工厂对象，用于创建和管理平台相关的资源。
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
class JVMCloseTask(private val stream: List<AutoCloseable>, private val thread: List<Thread>) : Thread() {
	private val mark = MarkerFactory.getMarker("JvmCloseTask")

	private val log = LoggerFactory.getLogger(JVMCloseTask::class.java)

	/**
	 * 初始化块，用于设置线程的名称和守护状态。
	 */
	init {
		this.name = "Task-JVMClose"
		this.isDaemon = false
	}

	/**
	 * 线程的入口方法。
	 * 在线程启动时，首先关闭平台的线程缓存，然后关闭定时任务。
	 */
	override fun run() {
		log.info(mark,"Start: JVM Close Task")
		stream.forEach {
			try {
				it.close()
			} catch (e: Throwable) {
				log.warn(mark,"关闭[${it::class.java.simpleName}]遇到异常", e)
			}
		}
		thread.forEach {
			try {
				it.isDaemon = true
				it.interrupt()
			} catch (e: Throwable) {
				log.warn(mark,"关闭[${it.name}/${it::class.java.simpleName}]遇到异常", e)
			}
		}

		log.info(mark,"THE END")
	}
}
