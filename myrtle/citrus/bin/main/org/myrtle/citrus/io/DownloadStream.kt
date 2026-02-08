package org.myrtle.citrus.io

import me.tongfei.progressbar.ProgressBarBuilder
import me.tongfei.progressbar.ProgressBarStyle
import org.apache.http.HttpException
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import java.io.OutputStream

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class DownloadStream {
	private var bufferSize: Int = 1024
	private val pbb: ProgressBarStyle?

	companion object {
		@JvmStatic
		fun now(bufferSize: Int, pbb: ProgressBarStyle?): DownloadStream {
			return DownloadStream(bufferSize, pbb)
		}
	}

	constructor(bufferSize: Int, pbb: ProgressBarStyle?) {
		this.bufferSize = bufferSize
		this.pbb = pbb
	}

	open fun download(taskName: String, url: String, stream: OutputStream) {
		HttpClients.createDefault().use { httpClient ->
			val httpGet = HttpGet(url)
			httpClient.execute(httpGet).use { response ->
				val statusCode = response.statusLine.statusCode
				if (statusCode == 200) {
					val entity = response.entity
					entity.content.use { input ->
						stream.use { output ->
							val pb = pbb?.let {
								ProgressBarBuilder().setStyle(it).setInitialMax(entity.contentLength)
									.setTaskName(taskName).build()
							}
							pb?.start
							val buffer = ByteArray(bufferSize)
							var n = -1
							while (input.read(buffer).also { n = it } != -1) {
								output.write(buffer, 0, n)
								pb?.stepBy(bufferSize.toLong())
							}
							pb?.close()
						}
					}
				} else {
					throw HttpException("imci-dd-e: status code $statusCode")
				}
			}
		}

	}
}