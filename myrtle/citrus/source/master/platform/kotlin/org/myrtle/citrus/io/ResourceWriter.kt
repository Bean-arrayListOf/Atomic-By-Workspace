package org.myrtle.citrus.io

import org.mapdb.*
import java.io.Closeable
import java.io.File
import java.io.OutputStream

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class ResourceWriter : Closeable {

	private val db: DB
	private var mapName: String = "ResourceMap"
	private val encode = Charsets.UTF_8

	private var temp: File? = null

	fun use(mapName: String) {
		this.mapName = mapName
	}

	companion object {
		@JvmStatic
		fun now(file: String): ResourceWriter {
			return ResourceWriter(file)
		}

		@JvmStatic
		fun now(file: File): ResourceWriter {
			return ResourceWriter(file)
		}

		@JvmStatic
		fun now(): ResourceWriter {
			return ResourceWriter()
		}
	}

	constructor() {
		db = DBMaker.memoryDB().make()
	}

	constructor(file: File) {
		db = DBMaker.fileDB(file).fileMmapEnable().make()
	}

	constructor(file: String) {
		db = DBMaker.fileDB(file).fileMmapEnable().make()
	}

	fun initConfig() {
		db.hashMap("ResourceConfig").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen()
			.use { config ->
				config["encode"] = encode.name()
				config["time"] = System.currentTimeMillis().toString()
			}
	}

	protected fun getHTreeMap(mapName: String = this.mapName): HTreeMap<String, ByteArray> {
		return db.hashMap(mapName).keySerializer(Serializer.STRING).valueSerializer(Serializer.BYTE_ARRAY)
			.createOrOpen()
	}

	protected fun getBTreeMap(key: String): BTreeMap<Long, ByteArray> {
		return db.treeMap("\$[${mapName}]:[Stream]:[${key}]").keySerializer(Serializer.LONG)
			.valueSerializer(Serializer.BYTE_ARRAY).createOrOpen()
	}

	fun putStream(key: String): OutputStream {
		return ResourceOutputStream(getBTreeMap(key))
	}

	fun put(key: String, value: ByteArray) {
		getHTreeMap().use {
			it[key] = value
		}
	}

	fun put(mapName: String, key: String, value: ByteArray) {
		getHTreeMap(mapName).use {
			it[key] = value
		}
	}

	fun put(key: String, value: String) {
		getHTreeMap().use {
			it[key] = value.toByteArray(encode)
		}
	}

	fun put(mapName: String, key: String, value: String) {
		getHTreeMap(mapName).use {
			it[key] = value.toByteArray(encode)
		}
	}

	fun get(key: String): ByteArray? {
		getHTreeMap().use {
			return it[key]
		}
	}

	fun get(mapName: String, key: String): ByteArray? {
		getHTreeMap(mapName).use {
			return it[key]
		}
	}

	fun get(key: String, default: ByteArray): ByteArray {
		getHTreeMap().use {
			return it[key] ?: default
		}
	}

	fun get(mapName: String, key: String, default: ByteArray): ByteArray? {
		getHTreeMap(mapName).use {
			return it[key] ?: default
		}
	}

	fun putAll(map: Map<String, ByteArray>) {
		getHTreeMap().use {
			it.putAll(map)
		}
	}

	fun putAll(mapName: String, map: Map<String, ByteArray>) {
		getHTreeMap(mapName).use {
			it.putAll(map)
		}
	}

	fun toMap(): Map<String, ByteArray> {
		getHTreeMap().use {
			return it.toMap()
		}
	}

	fun toMao(mapName: String): Map<String, ByteArray> {
		getHTreeMap(mapName).use {
			return it.toMap()
		}
	}

	fun containsKey(key: String): Boolean {
		getHTreeMap().use {
			return it.containsKey(key)
		}
	}

	fun containsKey(mapName: String, key: String): Boolean {
		getHTreeMap(mapName).use {
			return it.containsKey(key)
		}
	}

	fun containsValue(value: ByteArray): Boolean {
		getHTreeMap().use {
			return it.containsValue(value)
		}
	}

	fun containsValue(mapName: String, value: ByteArray): Boolean {
		getHTreeMap(mapName).use {
			return it.containsValue(value)
		}
	}

	fun isEmpty(): Boolean {
		getHTreeMap().use {
			return it.isEmpty()
		}
	}

	fun isEmpty(mapName: String): Boolean {
		getHTreeMap(mapName).use {
			return it.isEmpty()
		}
	}

	fun entrySet(): MutableSet<MutableMap.MutableEntry<String?, ByteArray?>> {
		getHTreeMap().use {
			return it.entries
		}
	}

	fun entrySet(mapName: String): MutableSet<MutableMap.MutableEntry<String?, ByteArray?>> {
		getHTreeMap(mapName).use {
			return it.entries
		}
	}

	override fun close() {
		db.close()
		temp?.deleteOnExit()
	}

}