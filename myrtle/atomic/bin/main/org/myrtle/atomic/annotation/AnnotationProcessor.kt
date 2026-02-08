package org.myrtle.atomic.annotation

import org.slf4j.LoggerFactory

interface AnnotationProcessor {

	fun invoke(target: Any)
	fun invoke(vararg target: Any){
		val es = ArrayList<Exception>()
		for (t in target){
			try {
				invoke(t)
			}catch (e:Exception){
				es.add(e)
			}
		}

		if (es.isNotEmpty()){
			throw RuntimeException(es[0])
		}
	}
}