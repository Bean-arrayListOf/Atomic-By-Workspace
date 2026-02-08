package org.myrtle.atomic

import org.myrtle.atomic.TypeKit.forName
import org.myrtle.atomic.autolog.LogBean
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker

object AutoLog : Logger {
	private val lMap = hashMapOf<String, LogBean>()
	private val classExplorationDepth: Int = SystemConfig.Companion.of.getInt("org.myrtle.canary.AutoLog.classExplorationDepth",2)

	fun getlMap(className: String): Logger{
		val log = lMap[className]
		if (log == null){
			lMap[className] = LogBean(LoggerFactory.getLogger(className.forName()))
		}
		return lMap[className]!!.log
	}

	fun getAllMap(): Map<String, LogBean>{
		return lMap
	}

	fun getAllMap(className: String): Logger{
		return getlMap(className)
	}

	override fun getName(): String? {
		return null
	}

	override fun isTraceEnabled(): Boolean {
		return true
	}

	override fun trace(msg: String?){
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(msg)
	}

	override fun trace(format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(format,arg)
	}

	override fun trace(format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(format,arg1,arg2)
	}

	override fun trace(format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(format,*arguments)
	}

	override fun trace(msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(msg,t)
	}

	override fun isTraceEnabled(marker: Marker?): Boolean {
		return true
	}

	override fun trace(marker: Marker?, msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,msg)
	}

	override fun trace(marker: Marker?, format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,format,arg)
	}

	override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,format,arg1,arg2)
	}

	override fun trace(marker: Marker?, format: String?, vararg argArray: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,format,*argArray)
	}

	override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,msg,t)
	}

	override fun isDebugEnabled(): Boolean {
		return true
	}

	override fun debug(msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(msg)
	}

	override fun debug(format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(format,arg)
	}

	override fun debug(format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(format,arg1,arg2)
	}

	override fun debug(format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(format,*arguments)
	}

	override fun debug(msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(msg,t)
	}

	override fun isDebugEnabled(marker: Marker?): Boolean {
		return true
	}

	override fun debug(marker: Marker?, msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(msg)
	}

	override fun debug(marker: Marker?, format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(marker,format,arg)
	}

	override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(marker,format,arg1,arg2)
	}

	override fun debug(marker: Marker?, format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(marker,format,*arguments)
	}

	override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).debug(marker,msg,t)
	}

	override fun isInfoEnabled(): Boolean {
		return true
	}

	override fun info(msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(msg)
	}

	override fun info(format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(format,arg)
	}

	override fun info(format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(format,arg1,arg2)
	}

	override fun info(format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(format,*arguments)
	}

	override fun info(msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(msg,t)
	}

	override fun isInfoEnabled(marker: Marker?): Boolean {
		return true
	}

	override fun info(marker: Marker?, msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(msg)
	}

	override fun info(marker: Marker?, format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,format,arg)
	}

	override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,format,arg1,arg2)
	}

	override fun info(marker: Marker?, format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,format,*arguments)
	}

	override fun info(marker: Marker?, msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).info(marker,msg,t)
	}

	override fun isWarnEnabled(): Boolean {
		return true
	}

	override fun warn(msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(msg)
	}

	override fun warn(format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(format,arg)
	}

	override fun warn(format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(format,*arguments)
	}

	override fun warn(format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(format,arg1,arg2)
	}

	override fun warn(msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(msg,t)
	}

	override fun isWarnEnabled(marker: Marker?): Boolean {
		return true
	}

	override fun warn(marker: Marker?, msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(marker,msg)
	}

	override fun warn(marker: Marker?, format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(marker,format,arg)
	}

	override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(marker,format,arg1,arg2)
	}

	override fun warn(marker: Marker?, format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(marker,format,*arguments)
	}

	override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).warn(marker,msg,t)
	}

	override fun isErrorEnabled(): Boolean {
		return true
	}

	override fun error(msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(msg)
	}

	override fun error(format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(format,arg)
	}

	override fun error(format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(format,arg1,arg2)
	}

	override fun error(format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(format,*arguments)
	}

	override fun error(msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(msg,t)
	}

	override fun isErrorEnabled(marker: Marker?): Boolean {
		return true
	}

	override fun error(marker: Marker?, msg: String?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(marker,msg)
	}

	override fun error(marker: Marker?, format: String?, arg: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(marker,format,arg)
	}

	override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(marker,format,arg1,arg2)
	}

	override fun error(marker: Marker?, format: String?, vararg arguments: Any?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(marker,format,*arguments)
	}

	override fun error(marker: Marker?, msg: String?, t: Throwable?) {
		getlMap(TypeKit.upClassName(classExplorationDepth)).error(marker,msg,t)
	}
}