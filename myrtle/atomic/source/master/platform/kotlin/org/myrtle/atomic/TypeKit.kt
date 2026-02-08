package org.myrtle.atomic

import org.myrtle.atomic.typeas.DefenseElement
import org.myrtle.atomic.typeas.NullDefenseElement

object TypeKit {
	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> isNull(value: T?): Boolean {
		return value == null
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> isNotNull(value: T?): Boolean {
		return value != null
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> isEmpty(value: T?): Boolean {
		return value == null || value.toString().isEmpty()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> isNotEmpty(value: T?): Boolean {
		return value != null && value.toString().isNotEmpty()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> isBlank(value: T?): Boolean {
		return value == null || value.toString().isBlank()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> isNotBlank(value: T?): Boolean {
		return value != null && value.toString().isNotBlank()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun typeOf(value: Any): Class<*> {
		return value::class.java
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun Any.type(): Class<*> {
		return this::class.java
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val upClassName: String
		get() = Thread.currentThread().stackTrace[2].className

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun upClassName(index: Int): String{
		return Thread.currentThread().stackTrace[index].className
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun String.forName(): Class<*> = Class.forName(this)

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> block(code: () -> T): T {
		return code()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> block(arg: T,code: (arg: T) -> T): T {
		return code(arg)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> nullDefense(block: () -> T?): NullDefenseElement<T>{
		return try {
			NullDefenseElement.of(block(),null)
		}catch (e: NullPointerException){
			NullDefenseElement.of(null,e)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> defense(block:() -> T?): DefenseElement<T>{
		return try {
			DefenseElement.of(block(),null)
		}catch (e: Exception){
			DefenseElement.of(null,e)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> T?.notNull(block: () -> T): T {
		return this ?: block()
	}

}