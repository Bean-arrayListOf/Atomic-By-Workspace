/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.myrtle.atomic.FOStream
import org.myrtle.atomic.StringKit.toUpperCase
import org.myrtle.atomic.str
import org.mapdb.DB
import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import org.mapdb.Serializer
import java.io.File
import java.nio.charset.Charset
import java.util.*

/**
 * @author cat
 * @since 2025-06-28
 **/
object Factory {
	private var gson: Gson? = null
	private var encode: Charset? = null
	private var pathSeparator: String? = null
	private var lineSeparator: String? = null
	private var pathSeparatorChar: Char? = null
	private var osProperties: Properties? = null
	private var separator: String? = null
	private var scanner: Scanner? = null
	private var classPath: List<String>? = null
	private var env: Map<String, String>? = null
	private var classLoader: ClassLoader? = null
	private var cacheManager: DB? = null
	private var tempCachePath: File? = null
	private var cacheMap: HTreeMap<String?, String?>? = null
	private var configManager: DB? = null
	private var configMap: HTreeMap<String?, ByteArray?>? = null
	private var charTable: List<String>? = null
	fun getGson(): Gson{
		if (gson == null){
			gson = GsonBuilder().setPrettyPrinting().create()
		}
		return gson!!
	}

	fun getEncode(): Charset{
		if (encode == null){
			encode = Charset.defaultCharset()
		}
		return encode!!
	}


	fun getPathSeparator(): String{
		if (pathSeparator == null){
			pathSeparator = File.pathSeparator
		}
		return pathSeparator!!
	}

	fun getLineSeparator(): String{
		if (lineSeparator == null){
			lineSeparator = System.lineSeparator()
		}
		return lineSeparator!!
	}

	fun getPathSeparatorChar(): Char{
		if (pathSeparatorChar == null){
			pathSeparatorChar = File.pathSeparatorChar
		}
		return pathSeparatorChar!!
	}

	fun getProperties(): Properties{
		if (osProperties == null){
			osProperties = System.getProperties()
		}
		return osProperties!!
	}

	fun getSeparator(): String{
		if (separator == null){
			separator = File.separator
		}
		return separator!!
	}

	fun getScanner(): Scanner{
		if (scanner == null){
			scanner = Scanner(System.`in`)
		}
		return scanner!!
	}

	fun getClassPath(): List<String>{
		if (classPath == null){
			classPath = System.getProperty("java.class.path").split(File.pathSeparator)
		}
		return classPath!!
	}

	fun getEnv(): Map<String, String>{
		if (env == null){
			env = System.getenv()
		}
		return env!!
	}

	fun getClassLoader(): ClassLoader{
		if (classLoader == null){
			classLoader = Thread.currentThread().contextClassLoader
		}
		return classLoader!!
	}

	fun getTempCachePath(): File{
		if (tempCachePath == null){
			tempCachePath = File("${UUID.randomUUID().toString().toUpperCase()}.cache.bin")
			Runtime.getRuntime().addShutdownHook(Thread( {
				run{
					try {
						tempCachePath!!.deleteOnExit()
					}catch (e: Exception){
						e.printStackTrace()
					}
				}
			},"ACSH-${HTreeMap::class.java.typeName}-${UUID.randomUUID().toString().toUpperCase()}"))
		}
		return tempCachePath!!
	}

	fun getCacheManager(): DB{
		if (cacheManager == null){
			cacheManager = DBMaker.fileDB(getTempCachePath()).fileMmapEnable().closeOnJvmShutdown().executorEnable().fileMmapEnableIfSupported().make()
			Runtime.getRuntime().addShutdownHook(Thread( {
				run{
					try {
						cacheManager!!.close()
					}catch (e: Exception){
						e.printStackTrace()
					}
				}
			},"ACSH-${HTreeMap::class.java.typeName}-${UUID.randomUUID().toString().toUpperCase()}"))
		}
		return cacheManager!!
	}

	fun getCacheMap(): HTreeMap<String?, String?>{
		if (cacheMap == null){
			cacheMap = getCacheManager().hashMap("CacheMap").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen()
			Runtime.getRuntime().addShutdownHook(Thread( {
				run{
					try {
						cacheMap!!.close()
					}catch (e: Exception){
						e.printStackTrace()
					}
				}
			},"ACSH-${HTreeMap::class.java.typeName}-${UUID.randomUUID().toString().toUpperCase()}"))
		}
		return cacheMap!!
	}

	public fun getConfigManager(): DB{
		if (configManager == null){
			val outPath = File.createTempFile(UUID.randomUUID().toString().toUpperCase(),".config.dat")
			println(outPath.absolutePath)
			this::class.java.getResourceAsStream("config/config.dat").use {istream ->
				FOStream(outPath).use { ostream->
					istream?.transferTo(ostream)
				}
			}
			configManager = DBMaker.fileDB(outPath).closeOnJvmShutdown().readOnly().make()
			Runtime.getRuntime().addShutdownHook(Thread( {
				run{
					try {
						configManager!!.close()
						outPath.deleteOnExit()
					}catch (e: Exception){
						e.printStackTrace()
					}
				}
			},"ACSH-${HTreeMap::class.java.typeName}-${UUID.randomUUID().toString().toUpperCase()}"))
		}
		return configManager!!
	}

	fun getConfigMap(): HTreeMap<String?, ByteArray?>{
		if (configMap == null){
			configMap = getConfigManager().hashMap("ConfigMap").keySerializer(Serializer.STRING).valueSerializer(Serializer.BYTE_ARRAY).open()
			Runtime.getRuntime().addShutdownHook(Thread( {
				run{
					try {
						configMap!!.close()
					}catch (e: Exception){
						e.printStackTrace()
					}
				}
			},"ACSH-${HTreeMap::class.java.typeName}-${UUID.randomUUID().toString().toUpperCase()}"))
		}
		return configMap!!
	}

	fun getCharTable(): List<str>{
		if (charTable == null){
			charTable = arrayListOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9")
		}
		return charTable!!
	}
}