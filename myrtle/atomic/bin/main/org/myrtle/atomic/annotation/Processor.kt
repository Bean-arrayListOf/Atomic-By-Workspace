package org.myrtle.atomic.annotation

import org.slf4j.LoggerFactory

object Processor {
	private val log = LoggerFactory.getLogger(this::class.java)
	private val processors = ArrayList<AnnotationProcessor>()
	init{
		processors.add(EnvProcessor())
		processors.add(SConfigProcessor())
		processors.add(TimestampProcessor())
		processors.add(SystemPropertiesProcessor())
		processors.add(OpenStreamProcessor())
		processors.add(CloseableOnExitProcessor())
	}

	fun invoke(target: Any){
		for (processor in processors){
			try {
				processor.invoke(target)
			}catch (e:Exception){
				log.error("processor invoke error:",e)
			}
		}
	}
}