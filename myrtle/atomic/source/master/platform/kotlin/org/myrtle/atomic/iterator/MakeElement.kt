package org.myrtle.atomic.iterator

object MakeElement {

	@JvmStatic
	fun <T> list(list: List<T>,value: T,index: Int): ListElement<T> {
		return ListElementMake(index,list,value)
	}

	@JvmStatic
	fun <T> array(array: Array<T>,value: T,index: Int): ArrayElement<T> {
		return ArrayElementMake(index,array,value)
	}

	@JvmStatic
	fun <K,V> map(map: Map<K,V>,key: K,value: V,index: Int): MapElement<K, V> {
		return MapElementMake(index, map,key,value)
	}
}