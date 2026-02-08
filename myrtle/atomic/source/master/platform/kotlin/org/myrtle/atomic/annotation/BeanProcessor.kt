package org.myrtle.atomic.annotation

class BeanProcessor : AnnotationProcessor {
	override fun invoke(target: Any){
		val clazz = target.javaClass
		val fields = clazz.getDeclaredFields()
		for (field in fields){
			if (field.isAnnotationPresent(Bean::class.java)){
				try {
					field.isAccessible = true
					val t = field.type
					//field.get(AutoCloseable)
				}catch (e:Exception){
					e.printStackTrace()
					throw RuntimeException(e)
				}
			}
		}
	}

}