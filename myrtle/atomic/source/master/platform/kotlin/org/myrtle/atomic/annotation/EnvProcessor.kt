package org.myrtle.atomic.annotation

import org.myrtle.atomic.type

class EnvProcessor : AnnotationProcessor {

	override fun invoke(target: Any){
		val clazz = target.javaClass
		val fields = clazz.getDeclaredFields()
		for (field in fields){
			if (field.isAnnotationPresent(Env::class.java)){
				val annotation = field.getAnnotation(Env::class.java)
				val key = annotation.key
				//val default = annotation.default
				try {
					field.isAccessible = true
					val t = field.type
					field.set(target, when (t){
						Boolean -> System.getenv(key).toBoolean()
						String -> System.getenv(key)
						Int -> System.getenv(key)!!.toInt()
						Long -> System.getenv(key)!!.toLong()
						Float -> System.getenv(key)!!.toFloat()
						Double -> System.getenv(key)!!.toDouble()
						Short -> System.getenv(key)!!.toShort()
						Byte -> System.getenv(key)!!.toByte()
						Char -> System.getenv(key)!!.toCharArray()[0]
						else -> System.getenv(key)
					})
				}catch (e:IllegalAccessException){
					e.printStackTrace()
					throw RuntimeException(e)
				}
			}
		}
	}
}