package org.myrtle.citrus.util

import java.io.*
import java.net.URL
import java.net.URLClassLoader

class JarKit : AutoCloseable {
	private val classLoader: URLClassLoader

	constructor(vararg file: String){
		val urls = ArrayList<URL>()
		for (url in file){
			urls.add(File(url).toURI().toURL())
		}
		this.classLoader = URLClassLoader(urls.toTypedArray(), Thread.currentThread().contextClassLoader)
	}

	constructor(vararg urls: URL){
		val arrays = urls.toList()
		this.classLoader = URLClassLoader(arrays.toTypedArray(), Thread.currentThread().contextClassLoader)
	}

	constructor(vararg file: File){
		val urls = ArrayList<URL>()
		for (file in file){
			urls.add(file.toURI().toURL())
		}
		this.classLoader = URLClassLoader(urls.toTypedArray(), Thread.currentThread().contextClassLoader)
	}

	fun getResource(name: String): InputStream?{
		return classLoader.getResource(name)?.openStream()
	}

	fun <T> getJavaBean(name: String): T?{
		getResource(name).use { input ->
			ObjectInputStream(input).use {
				return it.readObject() as T
			}
		}
	}

	fun copy(name: String,output: OutputStream):Boolean{
		(getResource(name) ?: return false).use {
			it.copyTo(output)
		}
		return true
	}

	fun copy(name: String,file: File):Boolean{
		(getResource(name) ?: return false).use { input ->
			FileOutputStream(file).use { output ->
				input.copyTo(output)
			}
		}
		return true
	}

	fun copyToTemp(name: String): File?{
		(getResource(name) ?: return null).use { input ->
			val tempFile = Citrus.temp.nowTempFile()
			FileOutputStream(tempFile.toFile()).use { output ->
				input.copyTo(output)
			}
			return tempFile.toFile()
		}
	}

	override fun close() {
		this.classLoader.close()
	}
}