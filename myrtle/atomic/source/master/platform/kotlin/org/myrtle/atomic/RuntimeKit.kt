package org.myrtle.atomic

import org.slf4j.LoggerFactory

object RuntimeKit {
	@JvmStatic
	private val log = LoggerFactory.getLogger(this::class.java)
	@JvmStatic
	fun main(args: Array<String>) {

		val name = SystemConfig.of.getString("run.name")
		println("name:$name")

		val cla = SystemConfig.of.getString("${name}.class")
		println("class:$cla")

		val initMethod = SystemConfig.of.getString("${name}.method.init")
		if (initMethod == null){log.warn("run.method.init is null")}

		val mainMethod = SystemConfig.of.getString("${name}.method.main")
		if (mainMethod == null){log.warn("run.method.main is null")}

		val closeMethod = SystemConfig.of.getString("${name}.method.close")
		if (closeMethod == null){log.warn("run.method.close is null")}


		cla?.let { cl ->

			val clazz = Class.forName(cl)

			initMethod?.let { init ->
				try {
					val instance = clazz.getDeclaredConstructor(Array<String>::class.java).newInstance(args)
					val method = clazz.getMethod(init)
					method.invoke(instance)
				}catch (e:Exception){
					log.warn("init error:{}",e.message)
					throw e
				}
			}

			mainMethod?.let { main ->
				try {
					val instance = clazz.getDeclaredConstructor(Array<String>::class.java).newInstance(args)
					val method = clazz.getMethod(main)
					method.invoke(instance)
				}catch (e:Exception){
					log.warn("main error:{}",e.message)
					throw e
				}
			}

			closeMethod?.let { close ->
				try {
					val instance = clazz.getDeclaredConstructor(Array<String>::class.java).newInstance(args)
					val method = clazz.getMethod(close)
					method.invoke(instance)
				}catch (e:Exception){
					log.warn("close error:{}",e.message)
					throw e
				}
			}
		}

	}
}