package org.myrtle.atomic.autolog

import org.slf4j.Logger
import java.io.Serial
import java.io.Serializable

data class LogBean(
		val log: Logger,
		val time: Long = System.currentTimeMillis(),
	): Serializable{
	companion object{
		@Serial
		private const val serialVersionUID = 6551532269474955300
	}
}