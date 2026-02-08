package org.myrtle.atomic

import org.myrtle.atomic.iterator.ArrayElement
import org.myrtle.atomic.iterator.ListElement
import org.myrtle.atomic.iterator.MakeElement
import org.myrtle.atomic.iterator.MapElement
import java.io.InputStream
import java.sql.ResultSet
import java.util.*
import java.util.stream.IntStream
import java.util.stream.LongStream

object IteratorKit {

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> foreach(list: List<T>, block: (ListElement<T>) -> Unit) {
		for (i in list.indices) {
			block(MakeElement.list(list,list[i],i))
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> foreach(list: List<T>, crossinline block: (List<T>, i32) -> Unit) {
		foreach(list) { element ->
			block(element.list, element.index)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> foreach(list: Array<T>, block: (ArrayElement<T>) -> Unit) {
		for (i in list.indices) {
			block(MakeElement.array(list,list[i],i))
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> foreach(list: Array<T>, crossinline block: (Array<T>, i32) -> Unit) {
		foreach(list) { element ->
			block(element.list, element.index)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <K,V> foreach(map: Map<K,V>, block: (MapElement<K, V>) ->Unit){
		val key = map.keys.toList()
		val value = map.values.toList()
		for (i in 0 until map.size) {
			block(MakeElement.map(map,key[i],value[i],i))
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun resultSet(rs: ResultSet, block: (ResultSet) -> Unit){
		while (rs.next()) {
			block(rs)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun foreach(begin: i32, over: i32, block: (i32) -> Unit){
		for (i in begin..over){
			block(i)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun foreach(over: i32, block: (i32) -> Unit){
		for (i in 0..over){
			block(i)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun foreach(begin: Long,over: Long,block: (Long) -> Unit){
		for (i in begin..over){
			block(i)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun foreach(over: Long,block: (Long) -> Unit){
		for (i in 0..over){
			block(i)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun loops(block: (Long) -> Boolean){
		var nums: Long = 0
		var continues = true
		do {
			continues = block(nums)
			nums++
		}while (continues)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun block(block: () -> Unit){
		block()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> code(block: () -> T): T {
		return block()
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun addJvmCloseTask(block: Runnable){
		Runtime.getRuntime().addShutdownHook(Thread(block))
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun streamForeach(stream: InputStream, block: (ByteArray, i32, i32) -> Unit){
		val buffer = ByteArray(1024)
		var n = -1
		while (stream.read(buffer).also { n = it }!=-1) {
			block(buffer,0,n)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun streamForeach(stream: InputStream, bufferSize: i32, block: (ByteArray, i32, i32) -> Unit){
		val buffer = ByteArray(1024)
		var n = -1
		while (stream.read(buffer).also { n = it }!=-1) {
			block(buffer,0,n)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun sForeach(stream: InputStream, block: (ByteArray, i32, i32) -> Unit){
		val buffer = ByteArray(1024)
		var n = -1
		while (stream.read(buffer).also { n = it }!=-1) {
			block(buffer,0,n)
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun assign(block: () -> bool) {
		try {
			block()
		}catch (export: error){
			export.printStackTrace()
		}
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun loop(block: () -> Boolean) {
		do {
			// 执行传入的 lambda 表达式，其结果用于决定是否继续循环
			val continueLoop = block()
		} while (continueLoop)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun range(startInclusive: Int, endExclusive: Int): IntStream{
		return IntStream.range(startInclusive,endExclusive)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun range(startInclusive: Long, endExclusive: Long): LongStream{
		return LongStream.range(startInclusive,endExclusive)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun range(startInclusive: Int, endExclusive: Int, step: Int): IntStream{
		return IntStream.range(startInclusive,endExclusive).filter { it % step == 0 }
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun range(startInclusive: Long, endExclusive: Long, step: Long): LongStream{
		return LongStream.range(startInclusive,endExclusive).filter { it % step == 0L }
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun rangeClosed(startInclusive: Int, endInclusive: Int): IntStream{
		return IntStream.rangeClosed(startInclusive,endInclusive)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun rangeClosed(startInclusive: Long, endInclusive: Long): LongStream{
		return LongStream.rangeClosed(startInclusive,endInclusive)
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun rangeClosed(startInclusive: Int, endInclusive: Int, step: Int): IntStream{
		return IntStream.rangeClosed(startInclusive,endInclusive).filter { it % step == 0 }
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun rangeClosed(startInclusive: Long, endInclusive: Long, step: Long): LongStream{
		return LongStream.rangeClosed(startInclusive,endInclusive).filter { it % step == 0L }
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun range(startInclusive: Int, endExclusive: Int, crossinline block: (i: Int) -> Unit){
		IntStream.range(startInclusive,endExclusive).forEach { block(it) }
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun range(startInclusive: Long, endExclusive: Long, crossinline block: (i: Long) -> Unit){
		LongStream.range(startInclusive,endExclusive).forEach { block(it) }
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <K,V> java.util.HashMap<K, V>.appends(block: (java.util.HashMap<K, V>)-> Unit): HashMap<K, V> {
		block(this)
		return this
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <K,V> newHMap(block: (java.util.HashMap<K, V>) -> Unit): java.util.HashMap<K, V> {
		val map = hashMapOf<K,V>()
		block(map)
		return map
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <K,V> newMap(block: (java.util.HashMap<K, V>) -> Unit): Map<K,V>{
		val map = hashMapOf<K,V>()
		block(map)
		return map
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <K,V> newLHMap(block: (java.util.LinkedHashMap<K, V>) -> Unit): java.util.LinkedHashMap<K, V> {
		val map = LinkedHashMap<K,V>()
		block(map)
		return map
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> newAList(block: (java.util.ArrayList<T>) -> Unit): java.util.ArrayList<T> {
		val list = ArrayList<T>()
		block(list)
		return list
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> newList(block: (ArrayList<T>) -> Unit): List<T>{
		val list = ArrayList<T>()
		block(list)
		return list
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> newLList(block: (LinkedList<T>) -> Unit): LinkedList<T>{
		val list = LinkedList<T>()
		block(list)
		return list
	}

	@JvmStatic
	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> newSet(block: (java.util.HashSet<T>) -> Unit): Set<T>{
		val set = HashSet<T>()
		block(set)
		return set
	}
}