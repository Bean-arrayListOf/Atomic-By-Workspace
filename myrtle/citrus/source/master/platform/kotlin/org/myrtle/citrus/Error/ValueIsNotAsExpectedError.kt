package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
open class ValueIsNotAsExpectedError: org.myrtle.citrus.Error.ValueError {
    constructor() : super("值不符合预期")
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}