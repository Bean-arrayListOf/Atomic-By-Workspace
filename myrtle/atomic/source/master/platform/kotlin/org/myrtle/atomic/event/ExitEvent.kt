package org.myrtle.atomic.event

import org.myrtle.atomic.ErrorKit

typealias ExitHandle = (uid: String,cl: ClassLoader)->Unit

object ExitEvent {
    @JvmStatic
    private val handle: HashMap<String,ExitHandle> = hashMapOf()

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            run {
                this.invoke()
            }
        })
    }



    @JvmStatic
    fun remove(uid: String) {
        synchronized(this.handle) {
            this.handle.remove(uid)
        }
    }

    @JvmStatic
    fun invoke(){
        synchronized(this.handle) {
            this.handle.forEach {
                try {
                    it.value.invoke(it.key, Thread.currentThread().contextClassLoader)
                }catch (e: Exception){
                    ErrorKit.out(e)
                }
            }
        }
    }
}