package org.myrtle.citrus.lang

import org.myrtle.atomic.ByteCache
import org.myrtle.atomic.HashType

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface Stochastic {
	fun nextHash(type: HashType, launch: Int): ByteCache
	fun nextHash(): ByteCache
	fun nextInt(min: Int, max: Int): Int
	fun nextInt(max: Int): Int
	fun nextInt(): Int
	fun nextLong(min: Long, max: Long): Long
	fun nextLong(max: Long): Long
	fun nextLong(): Long
	fun nextDouble(min: Double, max: Double): Double
	fun nextDouble(max: Double): Double
	fun nextDouble(): Double
	fun <T> nextArray(size: Int, array: Collection<T>): Collection<T>
	fun <T> nextArray(array: Collection<T>): Collection<T>


}