package org.myrtle.atomic.iterator

interface ListElement<T>: Element {
	val index: Int
	val list: List<T>
	val value: T
}