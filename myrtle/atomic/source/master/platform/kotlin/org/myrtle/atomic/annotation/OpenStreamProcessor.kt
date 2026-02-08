package org.myrtle.atomic.annotation

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.Reader
import java.io.Writer

class OpenStreamProcessor : AnnotationProcessor {
	private val cl = Thread.currentThread().contextClassLoader
	override fun invoke(target: Any){
		val clazz = target.javaClass
		val fields = clazz.getDeclaredFields()
		for (field in fields){
			if (field.isAnnotationPresent(OpenStream::class.java)){
				val annotation = field.getAnnotation(OpenStream::class.java)
				val path = annotation.path
				val atResource = annotation.atResource
				try {
					field.isAccessible = true
					val t = field.type
					field.set(target, when (t){
						InputStream::class.java -> {
							if (atResource){
								cl.getResourceAsStream(path)
 							}else{
								 java.io.FileInputStream(path) as InputStream
							}
						}
						OutputStream::class.java -> {
							if (atResource){
								throw RuntimeException("Resource can not be OutputStream")
 							}else{
								 java.io.FileOutputStream(path)
							}
						}
						Reader::class.java -> {
							if (atResource){
								BufferedReader(InputStreamReader(cl.getResourceAsStream(path)))
 							}else{
								 java.io.FileReader(path)
							}
						}
						Writer::class.java -> {
							if (atResource){
								throw RuntimeException("Resource can not be Writer")
 							}else{
								 java.io.FileWriter(path)
							}
						}
						else -> {
							throw RuntimeException("not support type")
						}
					})
				}catch (e:IllegalAccessException){
					e.printStackTrace()
					throw RuntimeException(e)
				}
			}
		}
	}
}