package org.myrtle.atomic

import org.myrtle.atomic.autoConfig.Config
import java.io.File
import java.nio.charset.Charset
import java.util.*

object SystemKit {

	@Suppress("NOTHING_TO_INLINE")
	inline val config : Config
		get() = SystemConfig.of

	@Suppress("NOTHING_TO_INLINE")
	inline fun exit(status: Int) {
		kotlin.system.exitProcess(status)
	}


	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val locale: Locale
		get() = Locale.getDefault()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val encode: Charset
		get() = Charset.forName(config.getString("org.myrtle.canary.SystemKit.encode", Charset.defaultCharset().name()))

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val pathSeparator: String
		get() = config.getString("org.myrtle.canary.SystemKit.pathSeparator", File.pathSeparator)

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val lineSeparator: String
		get() = config.getString("org.myrtle.canary.SystemKit.lineSeparator", System.lineSeparator())

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val pathSeparatorChar: Char
		get() = config.getString("org.myrtle.canary.SystemKit.pathSeparatorChar", File.pathSeparatorChar.toString()).first()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val osProperties: Properties
		get() = os.getProperties()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val separator: String
		get() = config.getString("org.myrtle.canary.SystemKit.separator", File.separator)

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val separatorChar: Char
		get() = config.getString("org.myrtle.canary.SystemKit.separatorChar", File.separatorChar.toString()).first()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val scanner: Scanner
		get() = Scanner(System.`in`)

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val classPath: List<String>
		get() = classLoader.getResources("").asSequence().map { it.file }.toList()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val env: Map<String, String>
		get() = os.getenv()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val classLoader: ClassLoader
		get() = Thread.currentThread().contextClassLoader

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun osProperty(key: String): String? = osProperties.getProperty(key)

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val runtime: Runtime
		get() = Runtime.getRuntime()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val timeMillis: Long
		get() = os.currentTimeMillis()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline val getTimeNano: Long
		get() = os.nanoTime()

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun env(key: String): String? = env[key]

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun env(key: String, default: String): String = env[key] ?: default




}

