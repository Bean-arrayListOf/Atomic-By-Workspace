package org.myrtle.citrus.lang

import org.apache.http.MethodNotSupportedException
import java.io.Closeable

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface UnixCloseable: Closeable {
	/**
	 * 尝试关闭当前对象并根据关闭操作的状态返回相应的状态码。
	 *
	 * 此方法尝试正常关闭对象。如果关闭操作成功，则返回0。
	 * 如果提供的状态码大于-1，则直接返回该状态码，不执行关闭操作。
	 * 如果在关闭过程中遇到不同的异常，将返回对应的状态码。
	 *
	 * @param status 关闭操作的状态码，如果大于-1，则直接返回该状态码。
	 * @return 关闭操作的状态码，根据不同的情况返回不同的值。
	 */
	fun close(status: Int): Int {
	    // 如果状态码大于-1，直接返回该状态码
	    if (status > -1) {
			try{close()}catch (_:Exception){}
	        return status
	    }
	    try {
	        // 尝试执行关闭操作
	        close()
	        // 关闭成功，返回0
	        return 0
	    } catch (e: NullPointerException) {
	        // 捕获空指针异常，打印堆栈跟踪并返回1
	        e.printStackTrace()
	        return 1
	    } catch (e: ClassNotFoundException) {
	        // 捕获类未找到异常，打印堆栈跟踪并返回404
	        e.printStackTrace()
	        return 404
	    } catch (e: MethodNotSupportedException) {
	        // 捕获方法不支持异常，打印堆栈跟踪并返回405
	        e.printStackTrace()
	        return 405
	    } catch (e: Exception) {
	        // 捕获其他所有异常，打印堆栈跟踪并返回127
	        e.printStackTrace()
	        return 127
	    }
	}
}