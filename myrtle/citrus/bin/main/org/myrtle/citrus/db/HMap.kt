package org.myrtle.citrus.db

import org.myrtle.atomic.*

/**
 * @author cat
 * @since 2025-08-11
 **/
class HMap<K,V> : Map<K,V> {
	private val connect: java.sql.Connection
	val mapName: String
	val readOnly: Boolean

	constructor(connect: java.sql.Connection, mapName: String, readOnly: Boolean) {
		this.connect = connect
		this.mapName = mapName
		this.readOnly = readOnly

		if (!readOnly) {
			createMap()
		}
	}

	private fun createMap() {
		connect.prepareStatement("""
			create table if not exists "${mapName}"(
				"INDEX" integer primary key autoincrement not null unique,
				"KEY" blob not null unique,
				"VALUE" blob,
				"TIME" timestamp default current_timestamp
			);
		""".trimIndent()).use {
			it.executeUpdate()
		}
	}

	fun put(key: K, value: V){
		if (readOnly) {
			throw ReadOnly("Read-only map")
		}
		if (containsKey(key)) {
			connect.prepareStatement("""
				update "${mapName}" set "VALUE"=? where "KEY"=?;
			""".trimIndent()).use { pstat ->
				pstat.setBytes(1,sObject(value))
				pstat.setBytes(2,sObject(key))
				pstat.executeUpdate()
			}
		} else {
			connect.prepareStatement("""
				insert into "${mapName}"("KEY","VALUE") values(?,?);
			""".trimIndent()).use { pstat ->
				pstat.setBytes(1,sObject(key))
				pstat.setBytes(2,sObject(value))
				pstat.executeUpdate()
			}
		}
	}

	fun remove(key: K): V? {
		if (readOnly){
			throw ReadOnly("Read-only map")
		}
		if (containsKey(key)) {
			val sKey = sObject(key)
			val value = get(key)
			connect.prepareStatement("""
			delete from "${mapName}" where "KEY"=?;
		""".trimIndent()).use { pstat ->
				pstat.setBytes(1,sKey)
				pstat.executeUpdate()
			}
			return value
		}
		return null
	}

	override val size: Int
		get() {
			return connect.prepareStatement("select count(*) from \"${mapName}\";").use { pstat ->
				pstat.executeQuery().use { rs ->
					rs.next()
					rs.getInt(1)
				}
			}
		}
	override val keys: Set<K>
		get() {
			return connect.prepareStatement("select \"KEY\" from \"${mapName}\";").use { pstat ->
				pstat.executeQuery().use { rs ->
					val keys = mutableSetOf<K>()
					while (rs.next()) {
						keys.add(oObject(rs.getBytes(1)))
					}
					keys
				}
			}
		}
	override val values: Collection<V>
		get() {
			return connect.prepareStatement("select \"VALUE\" from \"${mapName}\";").use { pstat ->
				pstat.executeQuery().use { rs ->
					val values = mutableListOf<V>()
					while (rs.next()) {
						values.add(oObject(rs.getBytes(1)))
					}
					values
				}
			}
		}
	override val entries: Set<Map.Entry<K, V>>
		get() {
			return connect.prepareStatement("select \"KEY\",\"VALUE\" from \"${mapName}\";").use { pstat ->
				pstat.executeQuery().use { rs ->
					val entries = mutableSetOf<Map.Entry<K, V>>()
					while (rs.next()) {
						entries.add(object : Map.Entry<K, V> {
							override val key: K
								get() = oObject(rs.getBytes(1))
							override val value: V
								get() = oObject(rs.getBytes(2))
						})
					}
					entries
				}
			}
		}
	override fun isEmpty(): Boolean {
		return size==0
	}

	override fun containsKey(key: K): Boolean {
		val sKey = sObject(key)
		connect.prepareStatement("select count(*) from \"${mapName}\" where KEY=?;").use { pstat ->
			pstat.setBytes(1,sKey)
			return pstat.executeQuery().use { rs ->
				rs.next()
				rs.getInt(1) > 0
			}
		}
	}

	private fun <T> sObject(t:T): ByteArray{
		return BAOStream().use { baos ->
			OOStream(baos).use { oos ->
				oos.writeObject(t)
			}
			baos.toByteArray()
		}
	}

	private fun <T> oObject(bytes: ByteArray): T{
		BAIStream(bytes).use { bais ->
			OIStream(bais).use { ois ->
				return ois.readObject() as T
			}
		}
	}

	override fun containsValue(value: V): Boolean {
		val sValue = sObject(value)
		 connect.prepareStatement("select count(*) from \"${mapName}\" where \"VALUE\"=?;").use { pstat ->
			 pstat.setBytes(1,sValue)
			 return pstat.executeQuery().use { rs -> rs.next()
				rs.getInt(1) > 0
			 }
		 }
	}

	override fun get(key: K): V? {
		val sKey = sObject(key)
		connect.prepareStatement("select \"VALUE\" from \"${mapName}\" where \"KEY\"=?;").use { pstat ->
			pstat.setBytes(1,sKey)
			return pstat.executeQuery().use { rs ->
				if (rs.next()) {
					oObject(rs.getBytes(1))
				} else {
					null
				}
			}
		}
	}

	fun get(key: K,default: V): V {
		return get(key) ?: default
	}
}