package org.myrtle.atomic.iterator

interface MapElement<K,V>: Element {
	val index: Int
	val map: Map<K,V>
	val key: K
	val value: V
}