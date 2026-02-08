package org.myrtle.atomic.annotation

import org.myrtle.atomic.SystemConfig

class SystemPropertiesProcessor : AnnotationProcessor {
	override fun invoke(target: Any){
		val clazz = target.javaClass
		val fields = clazz.getDeclaredFields()
		for (field in fields){
			if (field.isAnnotationPresent(SProperties::class.java)){
				val annotation = field.getAnnotation(SProperties::class.java)
				val key = annotation.key
				//val default = annotation.default
				try {
					field.isAccessible = true
					val t = field.type
					field.set(target, when (t){
						Boolean -> System.getProperty(key).toBoolean()
						String -> System.getProperty(key)
						Int -> System.getProperty(key)!!.toInt()
						Long -> System.getProperty(key)!!.toLong()
						Float -> System.getProperty(key)!!.toFloat()
						Double -> System.getProperty(key)!!.toDouble()
						Short -> System.getProperty(key)!!.toShort()
						Byte -> System.getProperty(key)!!.toByte()
						Char -> System.getProperty(key)!!.toCharArray()[0]
						else -> System.getProperty(key)
					})
				}catch (e:IllegalAccessException){
					e.printStackTrace()
					throw RuntimeException(e)
				}
			}
		}
	}
}