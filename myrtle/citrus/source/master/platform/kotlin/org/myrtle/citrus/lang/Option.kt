package org.myrtle.citrus.lang

/**
 * Option类用于封装可能为空或具有值的情况
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
class Option {
    /**
     * Value枚举用于表示存在值或空值的两种状态
     */
    enum class Value {
        Some, None
    }

    companion object {
        /**
         * 根据传入对象是否为null，返回对应的Value枚举值
         *
         * @param it 待判断的对象，可能为null
         * @return Value枚举值，Some表示对象非null，None表示对象为null
         */
        @JvmStatic
        fun <T> ofNull(it: T?): Value {
            if (it == null) {
                return Value.None
            }
            return Value.Some
        }

        /**
         * 如果传入对象为null，则抛出NullPointerException异常
         *
         * @param it 待校验的对象，可能为null
         * @return 非null的对象
         * @throws NullPointerException 如果传入对象为null
         */
        @JvmStatic
        @Throws(NullPointerException::class)
        fun <T> notNullThrows(it: T?): T {
            return it ?: throw NullPointerException()
        }
    }
}
