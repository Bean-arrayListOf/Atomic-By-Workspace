package org.myrtle.atomic

import org.apache.commons.configuration2.*

@Deprecated("此功能已弃用,目前为SystemConfig.of兼容层,完整功能见SystemConfig", ReplaceWith("SystemConfig.of"), DeprecationLevel.WARNING)
object OSConfig {

	@JvmStatic
	private val configs: SystemConfig = SystemConfig.of as SystemConfig

	@JvmStatic
	fun getString(key: String): String? = configs.getString(key)

	@JvmStatic
	fun getString(key: String,default: String): String = configs.getString(key,default)

	@JvmStatic
	fun getInt(key: String): Int? = configs.getInt(key)

	@JvmStatic
	fun getInt(key: String,default: Int): Int = configs.getInt(key,default)

	@JvmStatic
	fun getLong(key: String): Long? = configs.getLong(key)

	@JvmStatic
	fun getLong(key: String,default: Long): Long = configs.getLong(key,default)

	@JvmStatic
	fun getBoolean(key: String): Boolean? = configs.getBoolean(key)

	@JvmStatic
	fun getBoolean(key: String,default: Boolean): Boolean = configs.getBoolean(key,default)

	@JvmStatic
	fun getDouble(key: String): Double? = configs.getDouble(key)

	@JvmStatic
	fun getDouble(key: String,default: Double): Double = configs.getDouble(key,default)

	@JvmStatic
	fun getFloat(key: String): Float? = configs.getFloat(key)

	@JvmStatic
	fun getFloat(key: String,default: Float): Float = configs.getFloat(key,default)

	@JvmStatic
	fun <T> getList(key: String,type: Class<T>): List<T>? = configs.getList(type,key)

	@JvmStatic
	fun <T> getList(key: String,default: List<T>,type: Class<T>): List<T>? = configs.getList(type,key,default)

	@JvmStatic
	fun <T : Enum<T>> getEnum(key: String,enum: Class<T>): T? = configs.getEnum(key,enum)

	@JvmStatic
	fun <T : Enum<T>> getEnum(key: String,default: T,enum: Class<T>): T = configs.getEnum(key,enum,default)

	@JvmStatic
	fun getConfig(): CombinedConfiguration = configs.getConfig()

}