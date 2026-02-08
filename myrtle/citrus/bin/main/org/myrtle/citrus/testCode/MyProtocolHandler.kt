package org.myrtle.citrus.testCode

import org.slf4j.LoggerFactory
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.net.URLStreamHandler

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class MyProtocolHandler : URLStreamHandler() {
	private val log = LoggerFactory.getLogger(MyProtocolHandler::class.java)
	@Throws(IOException::class)
	override fun openConnection(u: URL): URLConnection {
		return object : URLConnection(u) {
			@Throws(IOException::class)
			override fun connect() {
				log.info("Connecting to $u")
			}

			@Throws(IOException::class)
			override fun getInputStream(): InputStream {
				log.info("get input stream")
				val buffer = ByteArray(1024)
				// 返回输入流，例如从文件系统或网络读取
				return ByteArrayInputStream(buffer)
			}

			@Throws(IOException::class)
			override fun getOutputStream(): OutputStream {
				log.info("get output stream")
				val ba = ByteArrayOutputStream()
				ba.write("Hello".toByteArray())
				ba.flush()
				// 返回输出流，例如写入文件系统或网络
				return ba
			}
		}
	}
}