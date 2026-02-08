package org.myrtle.citrus.network.tcp

import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("功能已弃用,将会在下一个版本删除", level = DeprecationLevel.WARNING)
open class TCPTask(protected val connect: Socket, val uuid: String) : Thread() {
	protected val input: InputStream = connect.getInputStream()
	protected val output: OutputStream = connect.getOutputStream()

	override fun run() {
		TODO("not implemented")
	}
}