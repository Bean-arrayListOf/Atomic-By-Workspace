package org.myrtle.citrus.network.tcp

import java.net.ServerSocket

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("功能已弃用,将会在下一个版本删除", level = DeprecationLevel.WARNING)
class TCPNetwork (private val port:Int): TCP {
	override fun start() {
		ServerSocket(port).use { server ->
			while (true){
				server.accept().use { socket ->

				}
			}
		}
	}
}