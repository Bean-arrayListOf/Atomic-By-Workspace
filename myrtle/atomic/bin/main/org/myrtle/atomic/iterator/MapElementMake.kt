package org.myrtle.atomic.iterator

import java.io.Serial

class MapElementMake<K,V>(
	override val index: Int,
	override val map: Map<K, V>,
	override val key: K,
	override val value: V
) :MapElement<K, V>{
	companion object{
		@Serial
		private const val serialVersionUID = 3316318250524829551
	}
	override fun toString(): String {
		return "MapElement(map=$map, index=$index, key=$key, value=$value)"
	}
}