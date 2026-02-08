package org.myrtle.citrus.io

import org.mapdb.DB
import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import java.io.File
import java.io.Flushable
import java.io.InputStream
import java.io.Serializable
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class FS : AutoCloseable, Flushable {

	companion object {
		@JvmStatic
		fun now(root: File): FS {
			return FS(root)
		}
	}

	constructor(root: File) {
		if (!root.exists()) {
			root.mkdirs()
		}
		this.root = root
		val path = Paths.get(root.path, "meta.dat").toFile()
		if (!path.exists()) {
			path.parentFile.mkdirs()
		}
		db = DBMaker.fileDB(path).make()
		this.meta = db.hashMap("meta").keySerializer(org.mapdb.Serializer.STRING).valueSerializer(org.mapdb.Serializer.STRING).createOrOpen()
	}

	data class FileSpace(
		val path: Path,
	) : Serializable {
		fun toFile(): File {
			return path.toFile()
		}

		fun toPath(): Path {
			return path
		}

		fun toStream():InputStream{
			return path.inputStream()
		}

		override fun toString(): String {
			return path.pathString
		}
	}

	private val root: File
	private val meta: HTreeMap<String, String>
	private val db: DB

	override fun close() {
		flush()
		meta.close()
		db.close()
	}

	fun put(key: String): FileSpace {
		val path = Paths.get(root.absolutePath, UUID.randomUUID().toString().uppercase(Locale.getDefault()), "meta.dat")
		if (!path.exists()) {
			if (!path.parent.exists()) {
				path.parent.createDirectories()
			}
			path.createFile()
		}
		val pathString = path.absolutePathString().replace(root.absolutePath + "/", "")
		meta[key] = pathString
		db.commit()
		return FileSpace(path)
	}

	fun exists(key: String): Boolean {
		val path = meta[key]?.let { Paths.get(root.absolutePath, it) }
		if (path == null) {
			return false
		}
		return path.exists()
	}

	fun delete(key: String) {
		val path = meta[key]?.let { Paths.get(root.absolutePath, it) }
		path?.deleteIfExists()
		meta.remove(key)
		db.commit()
	}

	fun get(key: String): FileSpace? {
		return meta[key]?.let { FileSpace(Paths.get(root.absolutePath, it)) }
	}

	fun getMap(): Map<String, String> {
		val maps = hashMapOf<String, String>()
		meta.forEach { (t, u) ->
			maps[t] = u
		}
		return maps
	}

	override fun flush() {
		val d = ArrayList<String>()
		meta.forEach { (k, v) ->
			val path = Paths.get(root.absolutePath, v)
			if (!path.exists()) {
				d.add(k)
			}
		}
		d.forEach {
			delete(it)
		}
		db.commit()
	}
}