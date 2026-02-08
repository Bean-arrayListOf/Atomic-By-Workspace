package org.myrtle.citrus.lang

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("功能已弃用,将会在下一个版本删除", level = DeprecationLevel.WARNING)
class LayerHashMap : LayerMap {
	private val layers = HashMap<String, HashMap<String,ByteArray>>()

	fun layerSize() = layers.size
	fun size(layerName: String) = layers[layerName]?.size ?: 0
	fun getKey(layerName: String): List<String>{
		return layers[layerName]?.keys?.toList() as List<String>
	}
}