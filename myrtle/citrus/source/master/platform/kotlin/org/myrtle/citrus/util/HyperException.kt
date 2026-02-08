package org.myrtle.citrus.util

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class HyperException(private val eTable: HashMap<Long, Class<*>>) {

//	@Throws(Exception::class)
//	fun threw(id: Long){
//		eTable[id]?.let {
//			throw it
//		}
//	}

//	operator fun get(id:Long): Exception?{
//		val clazz = eTable[id]!!
//		return exceptionCreateFactory(clazz,"e")
//	}

//	private fun exceptionCreateFactory(ec: Class<*>,message: String?): Exception{
//		val nowInstance = Citrus.nowDefaultPlatform().nowInstance<Exception>(ec, message)
//		return nowInstance.instance
//	}
}