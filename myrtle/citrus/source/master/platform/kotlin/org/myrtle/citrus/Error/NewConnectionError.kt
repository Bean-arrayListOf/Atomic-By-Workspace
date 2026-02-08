package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class NewConnectionError: org.myrtle.citrus.Error.ConnectionError {
    constructor() : super("创建Connection失败")
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}