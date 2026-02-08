package org.myrtle.citrus.util

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class ForEach {

	class Range: ForRange<Int> {
		private val begin: Int
		private val finish: Int
		private val add: Int
		private var i : Int
		constructor(begin: Int,finish: Int){
			this.begin = begin
			this.finish = finish
			this.i = begin
			this.add = 1
		}
		constructor(begin: Int,add: Int,finish: Int){
			this.begin = begin
			this.finish = finish
			this.i = begin
			this.add = add
		}
		override fun hasNext(): Boolean {
			if ((i+add)>finish) {
				return false
			}
			return finish >= i
		}

		override fun next(): Int {
			i += add
			return (i-add)
		}
	}

	fun range(begin: Int,finish: Int): ForRange<Int>{
		return Range(begin,finish)
	}
	fun range(begin: Int,add:Int,finish: Int): ForRange<Int>{
		return Range(begin,add,finish)
	}
}