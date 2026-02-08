package org.myrtle.citrus.testCode

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Test1(private val i:Int){
	fun access(i:Int):Int{
		return this.i + i
	}
}