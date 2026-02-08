package org.myrtle.atomic.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenStream(val path: String,val atResource: Boolean)
