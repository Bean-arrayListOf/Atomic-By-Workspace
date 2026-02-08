package org.myrtle.atomic.annotation

import org.myrtle.atomic.SystemConfig

class SConfigProcessor : AnnotationProcessor {
	override fun invoke(target: Any){
		val clazz = target.javaClass
		val fields = clazz.getDeclaredFields()
		for (field in fields){
			if (field.isAnnotationPresent(SConfig::class.java)){
				val annotation = field.getAnnotation(SConfig::class.java)
				val key = annotation.key
				//val default = annotation.default
				try {
					field.isAccessible = true
					val t = field.type
					field.set(target, SystemConfig.of.get(t, key))
				}catch (e:IllegalAccessException){
					throw RuntimeException(e)
				}
			}
		}
	}
}