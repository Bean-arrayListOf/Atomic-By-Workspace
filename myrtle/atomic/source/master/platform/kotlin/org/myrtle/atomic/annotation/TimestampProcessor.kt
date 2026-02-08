package org.myrtle.atomic.annotation

class TimestampProcessor : AnnotationProcessor {
	override fun invoke(target: Any){
		val clazz = target.javaClass
		val fields = clazz.getDeclaredFields()
		for (field in fields){
			if (field.isAnnotationPresent(Timestamp::class.java)){
				val annotation = field.getAnnotation(Timestamp::class.java)
				//val key = annotation.key
				//val default = annotation.default
				try {
					field.isAccessible = true
					val t = field.type
					field.set(target, System.currentTimeMillis())
				}catch (e:IllegalAccessException){
					e.printStackTrace()
					throw RuntimeException(e)
				}
			}
		}
	}
}