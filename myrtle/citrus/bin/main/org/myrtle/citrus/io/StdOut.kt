package org.myrtle.citrus.io

import java.io.OutputStream

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class StdOut(private val taskName: String, private val output: OutputStream, auto: Boolean) : OutputStream() {

	companion object {
		@JvmStatic
		fun now(taskName: String, output: OutputStream, auto: Boolean): StdOut {
			return StdOut(taskName, output, auto)
		}
	}

	@get:Synchronized
	private val array = arrayListOf<ByteArray>()
	private val tack: StdOutTask = StdOutTask(taskName, array, output, auto)

	init {
		tack.isDaemon = true
		tack.name = "Task-Sout: $taskName"
		tack.start()
	}

	override fun write(b: Int) {
		synchronized(array) {
			val ba = ByteArray(1)
			ba[0] = b.toByte()
			array.add(ba)
		}
	}

	override fun write(b: ByteArray) {
		synchronized(array) {
			array.add(b)
		}
	}

	override fun write(b: ByteArray, off: Int, len: Int) {
		synchronized(array) {
			val byteArray = ByteArray(len)
			System.arraycopy(b, off, byteArray, 0, len)
			array.add(byteArray)
		}
	}

	override fun flush() {
		this.tack.flush()
	}

	override fun close() {
		//this.tack.interrupt()
		this.tack.close()
	}

}