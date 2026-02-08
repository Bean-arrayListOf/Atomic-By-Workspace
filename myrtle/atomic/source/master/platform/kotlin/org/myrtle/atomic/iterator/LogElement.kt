package org.myrtle.atomic.iterator

import org.slf4j.Marker
import org.slf4j.event.Level

interface LogElement : Element {
	var level: Level
	var mark: Marker?
	var clas: Class<*>?
	var message: ArrayList<String>?
	var exception: Exception?
}