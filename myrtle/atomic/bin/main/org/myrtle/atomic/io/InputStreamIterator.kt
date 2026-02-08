package org.myrtle.atomic.io

import java.io.BufferedReader
import java.io.Reader

class InputStreamIterator : Iterator<String> {
	private val stream: BufferedReader
	private var nextLine: String? = null
	constructor(stream: Reader){
		this.stream = BufferedReader(stream)
	}

	override fun next(): String {
		return nextLine!!
	}

	override fun hasNext(): Boolean {
		nextLine = stream.readLine()
		return nextLine != null
	}
}