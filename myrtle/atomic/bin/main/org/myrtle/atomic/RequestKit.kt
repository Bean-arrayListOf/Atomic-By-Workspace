package org.myrtle.atomic

import org.myrtle.atomic.IOKit.openIStream
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.channels.Channels
import java.time.Duration
import javax.net.ssl.HttpsURLConnection

object RequestKit {
	fun sendGetRequest(url: String, version: HttpClient.Version = HttpClient.Version.HTTP_2, timeout: Duration = Duration.ofSeconds(10), headers: Map<String, String> = mapOf()): RequestResult {
		val httpClient =
			HttpClient.newBuilder().version(version).connectTimeout(timeout).followRedirects(HttpClient.Redirect.NORMAL)
				.build()

		val heads = arrayListOf<String>()
		for (i in headers) {
			heads.add("${i.key}:${i.value}")
		}

		val httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).headers(*heads.toTypedArray()).GET().build()

		val response: HttpResponse<String> = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

		return RequestResult(response.statusCode(), response.body())
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun connectGetRequest(url: URI,getRequest: Map<String, String>): RequestResult {

		val rs = arrayListOf<String>()
		HttpsURLConnection.HTTP_OK

		getRequest.forEach { (key, value) ->
			rs.add("${key}=${value}")
		}

		val uri = "${url.toString()}?${rs.joinToString("&")}"
		HttpClients.createDefault().use { httpClient ->
			val httpGet = HttpGet(uri)
			httpClient.execute(httpGet).use { response ->
				return RequestResult(response.statusLine.statusCode, EntityUtils.toString(response.entity))
			}
		}

	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun getMavenURL(mirror: URI, group: String, artifact: String, version: String): URI{
		val mirrorStr = mirror.toString()
		val groups = group.split(".")
		return URI("${mirrorStr}${groups.joinToString("/")}/${artifact}/${version}/${artifact}-${version}.jar")
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun downloadUsingNIO(uri: URI, file: File){
		Channels.newChannel(uri.openIStream()).use { rbc ->
			FileOutputStream(file).use { fos ->
				fos.channel.transferFrom(rbc,0, Long.MAX_VALUE)
			}
		}
	}
}