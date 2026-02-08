package org.myrtle.atomic

import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.random.RandomGeneratorFactory

object RandomKit {
	private val randomBean = ThreadLocalRandom.from(RandomGeneratorFactory.getDefault().create())

	val random: Random
		get() = randomBean

	fun nextInt(bound: Int): Int = randomBean.nextInt(bound)
	fun nextInt(startInclusive: Int, endExclusive: Int): Int = randomBean.nextInt(startInclusive, endExclusive)
	fun nextLong(bound: Long): Long = randomBean.nextLong(bound)
	fun nextLong(startInclusive: Long, endExclusive: Long): Long = randomBean.nextLong(startInclusive, endExclusive)
	fun nextDouble(bound: Double): Double = randomBean.nextDouble(bound)
	fun nextDouble(startInclusive: Double, endExclusive: Double): Double = randomBean.nextDouble(startInclusive, endExclusive)
	fun nextBoolean(): Boolean = randomBean.nextBoolean()
	fun nextBytes(array: ByteArray) {
		randomBean.nextBytes(array)
	}
}