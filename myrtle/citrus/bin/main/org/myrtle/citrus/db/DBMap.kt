package org.myrtle.citrus.db

import org.myrtle.citrus.db.MapDB.Pojo.SliceDataView
import java.nio.charset.Charset
import java.util.function.BiConsumer

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class DBMap(private val map: MapDBSQLite, private val db: String) : HashMap<String, SliceDataView>() {

	override val entries: MutableSet<MutableMap.MutableEntry<String, SliceDataView>>
		get() {
			val list = HashMap<String, SliceDataView>()
			val all = map.getAll(db)
			for (entry in all) {
				list[entry.getKey()] = entry
			}
			return list.entries
		}

	override val keys: MutableSet<String>
		get() = map.keys(db).toMutableSet()

	override val values: MutableCollection<SliceDataView>
		get() {
			val list = ArrayList<SliceDataView>()
			val maps = entries
			for (i in maps) {
				list.add(i.value)
			}
			return list
		}

	override val size: Int
		get() = keys.size

	override fun isEmpty(): Boolean {
		return keys.isEmpty()
	}

	override fun containsKey(key: String): Boolean {
		return keys.contains(key)
	}

	override fun containsValue(value: SliceDataView): Boolean {
		return values.contains(value)
	}

	override fun get(key: String): SliceDataView? {
		return map.get(db,key)
	}

	override fun forEach(action: BiConsumer<in String, in SliceDataView>) {
		map.getAll(db).forEach { sdv ->
			action.accept(sdv.getKey(),sdv)
		}
	}

	override fun getOrDefault(key: String, defaultValue: SliceDataView): SliceDataView {
		return get(key) ?: defaultValue
	}

	override fun put(key: String, value: SliceDataView): SliceDataView? {
		map.put(db,key,value.getDataFormat(Charset.defaultCharset()))
		return map.get(db,key)
	}

	override fun putAll(from: Map<out String, SliceDataView>) {
		from.forEach { (key, value) ->
			map.put(db,key,value.getDataFormat(Charset.defaultCharset()))
		}
	}

	override fun remove(key: String): SliceDataView? {
		val d = map.get(db,key)
		map.remove(db,key)
		return d
	}

	override fun remove(key: String, value: SliceDataView): Boolean {
		return remove(key) != null
	}

	override fun replace(key: String, value: SliceDataView): SliceDataView? {
		map.put(db,key,value.getDataFormat(Charset.defaultCharset()))
		return map.get(db,key)
	}
}