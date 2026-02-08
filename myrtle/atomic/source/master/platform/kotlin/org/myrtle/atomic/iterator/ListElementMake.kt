package org.myrtle.atomic.iterator

import java.io.Serial

class ListElementMake<T> (override val index: Int, override val list: List<T>, override val value: T) : ListElement<T> {
	companion object{
		@Serial
		private const val serialVersionUID = 3373736273827127066
	}
	override fun toString(): String {
		return "ListElement(index=$index, list=$list, value=$value)"
	}
}