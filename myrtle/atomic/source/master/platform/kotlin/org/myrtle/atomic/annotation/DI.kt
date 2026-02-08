package org.myrtle.atomic.annotation

object DI {
	@JvmStatic
	private val map = HashMap<String, Any>()

	@JvmStatic
	fun newAdd(clazz: Class<*>){
		val dc = clazz.getDeclaredConstructor()
		val any = dc.newInstance()
		map[clazz.typeName] = any
	}

	@JvmStatic
	fun ddd(any: Any){
		map[any.javaClass.typeName] = any
	}

	@JvmStatic
	fun <T: Any> get(clazz: Class<T>): T{
		return map[clazz.typeName] as T
	}

	@JvmStatic
	fun <T: Any> get(name: String): T{
		return map[name] as T
	}
}