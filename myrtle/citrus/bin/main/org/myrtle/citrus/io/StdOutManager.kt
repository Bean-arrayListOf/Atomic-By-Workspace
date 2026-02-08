package org.myrtle.citrus.io

import java.io.Closeable
import java.io.Flushable
import java.io.OutputStream
import java.io.PrintStream
import java.nio.charset.Charset

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class StdOutManager(private val taskName: String, stream: OutputStream, val autoFlush: Boolean, charset: Charset) :
	Closeable, Flushable {
	val output = StdOut(taskName, stream, autoFlush)
	val printStream: PrintStream = PrintStream(output, autoFlush, charset)

	companion object {
		@JvmStatic
		fun now(taskName: String, stream: OutputStream, autoFlush: Boolean, charset: Charset): StdOutManager {
			return StdOutManager(taskName, stream, autoFlush, charset)
		}
	}

	override fun flush() {
		output.flush()
	}

	override fun close() {
		output.close()
	}
}