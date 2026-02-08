package org.myrtle.citrus.Error

import java.io.IOException

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class StreamError:IOException {
    constructor() : super("流异常")
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}