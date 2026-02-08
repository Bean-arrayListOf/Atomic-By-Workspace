package org.myrtle.atomic.annotation

import java.lang.annotation.ElementType

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SConfig(val key: String)
