package org.myrtle.citrus.util

import org.slf4j.Marker
import org.slf4j.MarkerFactory

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object marker {
	private val map = hashMapOf<String, Marker>()

	fun get(markerName: String): Marker {
		var mark = map[markerName]
		if(mark == null) {
			mark = MarkerFactory.getMarker(markerName)
			map[markerName] = mark
		}
		return mark!!
	}
}