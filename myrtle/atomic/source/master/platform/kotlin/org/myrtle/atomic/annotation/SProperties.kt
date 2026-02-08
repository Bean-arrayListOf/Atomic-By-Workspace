package org.myrtle.atomic.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SProperties(val key: String)
