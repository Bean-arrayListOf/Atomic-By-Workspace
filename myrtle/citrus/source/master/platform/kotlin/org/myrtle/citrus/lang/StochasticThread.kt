package org.myrtle.citrus.lang

import groovyjarjarpicocli.CommandLine.InitializationException
import org.myrtle.atomic.ByteCache
import org.myrtle.atomic.HashKit
import org.myrtle.atomic.HashType
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * 用于获取随机Int值，随机Array等
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
open class StochasticThread : Stochastic {
	private val ran: Random

	companion object {
		@JvmStatic
		fun now(): StochasticThread {
			return StochasticThread()
		}

		@JvmStatic
		fun now(ran: Random): StochasticThread {
			return StochasticThread(ran)
		}
	}

	constructor() {
		this.ran = ThreadLocalRandom.current()
	}

	constructor(ran: Random) {
		this.ran = ran
	}

	override fun nextHash(type: HashType, launch: Int): ByteCache {
		return ByteCache(HashKit.get(type, nextString(launch)))
	}

	override fun nextHash(): ByteCache {
		return nextHash(HashType.SHA512, 30)
	}

	/**
	 * 随机Int
	 * @return Int值不会超过Int最大值(包含负数)
	 */
	override fun nextInt(): Int {
		return ran.nextInt()
	}

	override fun nextLong(): Long {
		return ran.nextLong()
	}

	override fun nextLong(max: Long): Long {
		return ran.nextLong(max)
	}

	override fun nextLong(min: Long, max: Long): Long {
		return ran.nextLong(min, max)
	}

	/**
	 * 随机Int
	 * @param max Int最大值
	 * @return 返回Int值不会超过max
	 */
	override fun nextInt(max: Int): Int {
		return ran.nextInt(max)
	}

	/**
	 * 随机Int
	 * @param min Int最小值
	 * @param max Int最大值
	 * @return 返回Int不会超过max和低于min
	 * @throws NumberError 如果max小于min会抛出异常
	 */
	override fun nextInt(min: Int, max: Int): Int {
		return ran.nextInt(min, max)
	}

	override fun nextDouble(min: Double, max: Double): Double {
		return ran.nextDouble(min, max)
	}

	override fun nextDouble(max: Double): Double {
		return ran.nextDouble(max)
	}

	override fun nextDouble(): Double {
		return ran.nextDouble()
	}

	/**
	 * 随机Array
	 * @param size 随机长度
	 * @param array 字符表
	 * @return 返回随机集合，集合长度为size，集合内所有内容都区自与array
	 */
	override fun <T> nextArray(size: Int, array: Collection<T>): Collection<T> {
		val arg = arrayListOf<T>()
		arg.addAll(array)
		val temp = arrayListOf<T>()
		for (i in 0..<size) {
			temp.add(arg.elementAt(ran.nextInt(arg.size)))
		}
		return temp
	}

	/**
	 * 随机Array
	 * @param array 字符表
	 * @return 返回随机集合，集合长度为array的长度，集合内所有内容都区自与array
	 */
	override fun <T> nextArray(array: Collection<T>): Collection<T> {
		return nextArray(array.size, array)
	}

	/**
	 * 随机Array
	 * @param length 随机String长度
	 * @param array 字符表
	 * @return 返回随机String，集合长度为length，String内所有内容都区自与array
	 */
	open fun nextString(length: Int, array: Collection<Char>): String {
		return nextArray(length, array).joinToString("")
	}

	/**
	 * 随机Array
	 * @param length 随机String长度
	 * @return 随机String内包含大小写英文,数字
	 */
	open fun nextString(length: Int): String {
		return nextArray(length, TableCharacters.allEnglish().numeric().random()).joinToString("")
	}


	open fun nextUUIDv3(arg: String): String {
		return UUID.nameUUIDFromBytes(arg.toByteArray(Charset.defaultCharset())).toString()
	}


	open fun nextUUIDv3(arg: ByteArray): ByteArray {
		return UUID.nameUUIDFromBytes(arg).toString().toByteArray(Charset.defaultCharset())
	}


	open fun nextBytes(length: Int): ByteArray {
		val b = ByteArray(length)
		ran.nextBytes(b)
		return b
	}


	open fun nextHash(type: HashType): ByteArray {
		val ranStr = nextBytes(512)
		return HashKit.get(type, ranStr)
	}


	open fun nextBoolean(): Boolean {
		return ran.nextBoolean()
	}

	open fun <T> next(args: Array<T>): T {
		return args[nextInt(0, args.size)]
	}


	open fun nextError(s: String): Throwable {
		val array = arrayListOf(
			org.myrtle.citrus.Error.CacheError(s),
			org.myrtle.citrus.Error.ConnectionError(s),
			org.myrtle.citrus.Error.CoralUnknownError(s),
			org.myrtle.citrus.Error.ERROR(s),
			org.myrtle.citrus.Error.FileNotExistError(s),
			org.myrtle.citrus.Error.FileOrFolderDoesNotExistError(s),
			org.myrtle.citrus.Error.FileTypeError(s),
			org.myrtle.citrus.Error.JDBCError(s),
			org.myrtle.citrus.Error.JNACLibraryError(s),
			org.myrtle.citrus.Error.LockError(s),
			org.myrtle.citrus.Error.MailError(s),
			org.myrtle.citrus.Error.NewConnectionError(s),
			org.myrtle.citrus.Error.Notifications(s),
			org.myrtle.citrus.Error.NumberError(s),
			org.myrtle.citrus.Error.NumericError(s),
			org.myrtle.citrus.Error.PoolError(s),
			org.myrtle.citrus.Error.RSAError(s),
			org.myrtle.citrus.Error.SessionError(s),
			org.myrtle.citrus.Error.StatementError(s),
			org.myrtle.citrus.Error.StatementQueryError(s),
			org.myrtle.citrus.Error.StatementUpdateError(s),
			org.myrtle.citrus.Error.StreamError(s),
			org.myrtle.citrus.Error.StreamIllegalShutdownError(s),
			org.myrtle.citrus.Error.StreamNotClosedError(s),
			org.myrtle.citrus.Error.TestError(s),
			org.myrtle.citrus.Error.ValueError(s),
			org.myrtle.citrus.Error.ValueIsNotAsExpectedError(s),
			org.myrtle.citrus.Error.VerifyError(s),
			org.myrtle.citrus.Error.Warnings(s),
			IOException(s),
			NullPointerException(s),
			Exception(s),
			NumberFormatException(s),
			InitializationException(s),

			)
		return array[ran.nextInt(array.size)]
	}


	open fun nextError(): Throwable {
		return nextError("随机异常")
	}


	open fun nextCharSequence(launch: Int, table: Collection<Char>): CharSequence {
		val chars = nextString(launch, table)
		return chars.subSequence(0, chars.length)
	}
}