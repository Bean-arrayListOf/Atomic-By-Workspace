package org.myrtle.citrus.db

interface MapStorage : AutoCloseable {
	fun contains(db: String): Boolean
	fun contains(db: String,key: String): Boolean
	fun remove(db: String,key: String)
	fun get(db: String,key: String): ByteArray?
	fun getString(db: String,key: String): String?
	fun getStrings(db: String,key: String): List<String>
	fun gets(db: String,key: String): List<ByteArray>
	fun getKeys(db: String): List<String>
	fun put(db: String,key: String,value: String)
	fun put(db: String,key: String,values: List<String>)
	fun put(db:String, key: String, value: ByteArray)
	fun add(db: String,key: String,value: String)
	fun add(db: String,key: String,value: ByteArray)
	fun puts(db: String,key: String,values: List<ByteArray>)

}