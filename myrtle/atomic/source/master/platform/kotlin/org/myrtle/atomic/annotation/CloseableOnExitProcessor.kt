package org.myrtle.atomic.annotation

import org.myrtle.atomic.Console.Companion.println
import org.slf4j.LoggerFactory
import java.io.Closeable
import java.io.InputStream
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.Random
import java.util.UUID

class CloseableOnExitProcessor : AnnotationProcessor {
	override fun invoke(target: Any){
		val clazz = target.javaClass
		val fields = clazz.getDeclaredFields()
		for (field in fields){
			if (field.isAnnotationPresent(CloseableOnExit::class.java)){
				try {
					field.isAccessible = true
					val t = field.type
					Runtime.getRuntime().addShutdownHook(AOE(field,target))
				}catch (e:Exception){
					e.printStackTrace()
					throw RuntimeException(e)
				}
			}
		}
	}

	class AOE : Thread{
		private val log = LoggerFactory.getLogger(this::class.java)
		private val clazz: Field
		private val target: Any
		private val uid = kotlin.random.Random.nextLong(100000,999999)
		constructor(clazz: Field,target: Any):super(){
			this.clazz = clazz
			this.target = target
			name = "AOE: (${clazz.name}:${uid})"
		}

		override fun run() {
			try {
				clazz.isAccessible = true
				clazz.type.getMethod("close").invoke(clazz.get(target))
				log.info("CloseableOnExit: [${clazz.name}] ok.",)
			}catch (e:Exception){
				log.error("CloseableOnExit: [${clazz.name}] error:",e)
			}
		}

	}
}