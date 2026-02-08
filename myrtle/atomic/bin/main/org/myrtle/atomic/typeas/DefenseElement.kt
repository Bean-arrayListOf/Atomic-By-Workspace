package org.myrtle.atomic.typeas

import java.io.Serial
import java.io.Serializable

class DefenseElement<T>: Serializable {
	val value: T?
	val exception: Exception?
	constructor(value: T? = null, exception: Exception? = null) {
		this.value = value
		this.exception = exception
	}
	companion object {
		@Serial
		private const val serialVersionUID = 2678874713600932163

		fun <T> of(value: T?=null, exception: Exception?=null): DefenseElement<T> {
			return DefenseElement(value, exception)
		}

	}

	fun OK(block:(DefenseElement<T>)->T):T{
		return value ?: block(this)
	}

	fun Error(block:(DefenseElement<T>)->T):T{
		return value ?: block(this)
	}
}