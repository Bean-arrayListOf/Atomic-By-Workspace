package org.myrtle.citrus.io

import java.io.OutputStream
import java.io.PrintStream
import java.nio.charset.Charset

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class PrintSout(output: OutputStream, auto: Boolean, charset: Charset) : PrintStream(output, auto, charset) {
	companion object {
		@JvmStatic
		fun now(output: OutputStream, auto: Boolean, charset: Charset): PrintSout {
			return PrintSout(output, auto, charset)
		}
	}
}