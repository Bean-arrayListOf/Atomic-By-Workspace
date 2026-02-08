package org.myrtle.atomic.iterator

import org.slf4j.Marker
import org.slf4j.event.Level
import java.io.Serial

class LogElementMake(
	override var level: Level,
	override var mark: Marker?,
	override var clas: Class<*>?,
	override var message: ArrayList<String>?,
	override var exception: Exception?
) : LogElement {
	companion object {
		@Serial
		private const val serialVersionUID = 979922569305213708
	}
}