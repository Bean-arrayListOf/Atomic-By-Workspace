package org.myrtle.citrus.Script

import groovy.lang.GroovyClassLoader
import java.io.File
import java.io.InputStream

/**
 * @author cat
 * @since 2025-08-27
 **/
class GroovyScriptLoader {
	val groovyClassLoader: GroovyClassLoader
	lateinit var classLoader: Class<*>
	constructor(classLoader: ClassLoader){
		this.groovyClassLoader = GroovyClassLoader(classLoader)
	}
	constructor(classLoader: ClassLoader,script: String){
		this.groovyClassLoader = GroovyClassLoader(classLoader)
		loadScript(script)
	}
	constructor(script: String){
		this.groovyClassLoader = GroovyClassLoader()
		loadScript(script)
	}
	constructor(classLoader: ClassLoader,script: File){
		this.groovyClassLoader = GroovyClassLoader(classLoader)
		loadScript(script)
	}
	constructor(script: File){
		this.groovyClassLoader = GroovyClassLoader()
		loadScript(script)
	}
	constructor(classLoader: ClassLoader,stream: InputStream){
		this.groovyClassLoader = GroovyClassLoader(classLoader)
		loadScript(stream)
	}
	constructor(){
		this.groovyClassLoader = GroovyClassLoader()
	}
	@Suppress("NOTHING_TO_INLINE")
	inline fun loadScript(script: String){
		classLoader = groovyClassLoader.parseClass(script)
	}
	@Suppress("NOTHING_TO_INLINE")
	inline fun loadScript(script: File){
		classLoader = groovyClassLoader.parseClass(script)
	}
	@Suppress("NOTHING_TO_INLINE")
	inline fun loadScriptFile(file: String) {
		classLoader = groovyClassLoader.parseClass(file)
	}
	@Suppress("NOTHING_TO_INLINE")
	inline fun loadScript(scriptStream: InputStream){
		classLoader = groovyClassLoader.parseClass(String(scriptStream.readAllBytes()))
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> inv(mName: String,vararg args: Any): T? {
		val constructor = classLoader.getConstructor()
		val instance = constructor.newInstance()
		val method = if (args.isEmpty()) {
			classLoader.getMethod(mName)
		}else {
			classLoader.getMethod(mName, *args.map { it::class.javaPrimitiveType }.toTypedArray())
		}
		return method.invoke(instance, *args) as? T?
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> invocable(mName: String,vararg args: Any): T? {
		return inv(mName,*args)
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> inv(mName: String,groovyCode: String,vararg args: Any?): T?{
		//val groovyClassLoader = GroovyClassLoader(Thread.currentThread().contextClassLoader)
		val classLoader = groovyClassLoader.parseClass(groovyCode)
		val constructor = classLoader.getConstructor()
		//val constructor = classLoader.getConstructor()
		val instance = constructor.newInstance()
		val method = if (args.isEmpty()) {
			classLoader.getMethod(mName)
		}else {
			classLoader.getMethod(mName, *args.map { it!!::class.javaPrimitiveType }.toTypedArray())
		}
		return method.invoke(instance, *args) as? T?
	}

	@Suppress("NOTHING_TO_INLINE")
	inline fun <T> inv(mName: String,vararg args: Any,default: T): T{
		return inv(mName,*args) ?: default
	}




}