package org.myrtle.atomic.iterator

import java.io.Serial

class ArrayElementMake<T> (override val index: Int, override val list: Array<T>, override val value: T) : ArrayElement<T> {
	companion object{
		@Serial
		private const val serialVersionUID = 3623224778091587091
	}
	override fun toString(): String {
		return "ListElement(index=$index, list=$list, value=$value)"
	}
}