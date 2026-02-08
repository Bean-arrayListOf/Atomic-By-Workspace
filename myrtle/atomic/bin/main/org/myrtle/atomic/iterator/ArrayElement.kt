package org.myrtle.atomic.iterator

interface ArrayElement<T>:Element {
	val index: Int
	val list: Array<T>
	val value: T
}