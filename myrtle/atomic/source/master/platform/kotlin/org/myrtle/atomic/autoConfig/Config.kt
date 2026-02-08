package org.myrtle.atomic.autoConfig

import org.apache.commons.configuration2.AbstractConfiguration
import org.apache.commons.configuration2.ConfigurationDecoder
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Duration
import java.util.*

interface Config {
	fun containsKey(key: String): Boolean
	fun containsValue(value: Any): Boolean
	fun <T> get(cls: Class<T>, key: String): T?
	fun <T> get(cls: Class<T>, key: String, default: T): T
	fun getBigDecimal(key: String): BigDecimal?
	fun getBigDecimal(key: String, default: BigDecimal): BigDecimal
	fun getBigInteger(key: String): BigInteger?
	fun getBigInteger(key: String, default: BigInteger): BigInteger
	fun getBoolean(key: String): Boolean?
	fun getBoolean(key: String, default: Boolean): Boolean
	fun getByte(key: String): Byte?
	fun getByte(key: String, default: Byte): Byte
	fun <T> getCollection(cls: Class<T>, key: String,target: Collection<T>): Collection<T>?
	fun <T> getCollection(cls: Class<T>, key: String,target: Collection<T>,default: Collection<T>): Collection<T>
	fun getDouble(key: String): Double?
	fun getDouble(key: String, default: Double): Double
	fun getDuration(key: String): Duration?
	fun getDuration(key: String, default: Duration): Duration
	fun getEncodedString(key: String, decoder: ConfigurationDecoder): String?
	fun getFloat(key: String): Float?
	fun getFloat(key: String, default: Float): Float
	fun getInt(key: String): Int?
	fun getInt(key: String, default: Int): Int
	fun getKeys(): Iterator<String?>?
	fun getKeys(prefix: String): Iterator<String?>?
	fun getKeys(prefix: String, delimiter: String): Iterator<String>
	fun <T> getList(cls: Class<T>, key: String): List<T>?
	fun <T> getList(cls: Class<T>, key: String, default: List<T>): List<T>
	fun getLong(key: String): Long?
	fun getLong(key: String, default: Long): Long
	fun getProperties(key: String): Properties?
	fun getProperties(key: String,default: Properties): Properties
	fun getShort(key: String): Short?
	fun getShort(key: String, default: Short): Short
	fun getString(key: String): String?
	fun getString(key: String, default: String): String
	fun <T : Enum<T>> getEnum(key: String,cls: Class<T>): T?
	fun <T : Enum<T>> getEnum(key: String,cls: Class<T>,default: T): T

	fun addConfig(config: AbstractConfiguration, name: String)
	fun addConfig(file: ConfigFile)

}
