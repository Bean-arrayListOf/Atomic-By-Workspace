package org.myrtle.atomic.typeas

import java.io.Serial
import java.io.Serializable

class NullDefenseElement<T>: Serializable{
	val value: T?
	val exception: Exception?

	constructor(value: T? = null, exception: Exception? = null){
		this.value = value
		this.exception = exception
	}

	companion object{
		@Serial
		private const val serialVersionUID = 4517810029038663672
		fun <T> of(value: T?=null, exception: Exception?=null): NullDefenseElement<T>{
			return NullDefenseElement<T>(value,exception)
		}
	}

	fun OK(block:(NullDefenseElement<T>)->T):T{
		return value ?: block(this)
	}

	fun Error(block:(NullDefenseElement<T>)->T):T{
		return value ?: block(this)
	}
}