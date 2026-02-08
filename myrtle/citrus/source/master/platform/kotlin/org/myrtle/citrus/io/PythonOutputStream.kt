package org.myrtle.citrus.io

import java.io.Closeable
import java.io.File
import java.io.Flushable
import java.io.OutputStream
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class PythonOutputStream : OutputStream, Closeable, Flushable {
	private val se: ScriptEngine
	private val open: Any

	companion object {
		@JvmStatic
		fun now(file: File): PythonOutputStream {
			return PythonOutputStream(file)
		}
	}

	constructor(file: File) {
		val manager = ScriptEngineManager()
		se = manager.getEngineByName("jython")
		//se.eval(String(this::class.java.classLoader.getResourceAsStream("citrus/jse/io.py")!!.readAllBytes()))
		open = se.eval("open('${file.path}', 'wb')")
	}

	override fun write(b: Int) {
		se.put("stream", open)
		se.put("org.myrtle.byte", b)
		se.eval("stream.write(bytearray(org.myrtle.byte))")
	}

	override fun flush() {
		se.put("stream", open)
		se.eval("stream.flush()")
	}

	override fun close() {
		se.put("stream", open)
		se.eval("stream.org.myrtle.close()")
	}

	override fun toString(): String {
		return super.toString()
	}

	override fun write(b: ByteArray) {
		se.put("stream", open)
		se.put("org.myrtle.byte", b)
		se.eval("stream.write(bytearray(org.myrtle.byte))")
	}

	override fun write(b: ByteArray, off: Int, len: Int) {
		se.put("stream", open)
		se.put("bytesarray", b)
		se.put("offset", off)
		se.put("length", len)
		se.eval(
			"byte_array=bytearray(bytesarray)\n" +
					"if offset < 0 or length <= 0 or offset + length > len(byte_array):\n" +
					"\traise ValueError(\"Invalid offset or length.\")\n" +
					"segment_to_write = byte_array[offset:offset+length]\n" +
					"stream.write(segment_to_write)"
		)
	}
}