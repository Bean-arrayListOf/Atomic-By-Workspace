package org.myrtle.citrus.util

import org.myrtle.atomic.*
import java.io.Closeable


/**
 * 任务类抽象定义，用于封装需要在独立线程中执行的任务。
 * 继承自Thread，使得Task可以被作为一个线程独立运行。
 * 同时实现了Closeable接口，支持任务的关闭操作。
 *
 * @param T 任务的返回类型，泛型设计使得Task类可以支持不同类型的返回结果。
 *
 * @author CitrusCat
 * @since 2024/10/26
 */
abstract class Task<T> : Thread(), Closeable {
	/**
	 * 是否打印错误信息，默认为true
	 */
	protected var printError: bool = true

	/**
	 * 指示在检测到null之后是否循环执行
	 */
	protected var loopBeforeNullDetection: bool = false

	/**
	 * 错误检测标志，用于开启或关闭错误检测机制
	 */
	protected var errorDetection: bool = false

	/**
	 * 是否调用loopAfter()方法
	 */
	protected var loopAfterClose: bool = false

	/**
	 * loopBefore()休眠
	 */
	protected var loopBeforeSleep: i64 = -1

	/**
	 * loopAfter()休眠
	 */
	protected var loopAfterSleep: i64 = -1

	/**
	 * org.myrtle.loop()休眠
	 */
	protected var loopSleep: i64 = -1

	/**
	 * 是否在loopAfter()方法中继续执行
	 */
	protected var loopAfterContinue: bool = false

	/**
	 * 是否在loopBefore()方法中继续执行
	 */
	protected var loopBeforeContinue: bool = false

	/**
	 * 是否打印耗时
	 */
	protected var loopElapsedTime: bool = true
	protected var loopBeforeNullContinue: bool = true

	/**
	 * 初始化任务配置
	 */
	init {
		this.isDaemon = true
		this.name = "Task-${this.javaClass.simpleName}"
	}

	/**
	 * 在循环开始前被调用的函数。
	 *
	 * 此函数在循环结构开始之前调用，可用于进行一些初始化操作。
	 * 默认情况下，该函数返回null，不执行任何操作。
	 *
	 * @return 返回null，表示没有特定的返回值。
	 */
	open fun loopBefore(): T? {
	    return null
	}

	/**
	 * 在指定时间后执行循环
	 *
	 * 此函数被设计为在某种定时器或循环机制中使用，当经过的时间满足一定条件时，返回true以指示可以继续执行循环中的操作
	 *
	 * @param elapsedTime 已经过的时间，以毫秒为单位此参数用于判断是否已经达到了可以继续执行的时刻
	 * @return Boolean值，如果返回true，则表示可以继续执行；如果返回false，则表示不继续执行
	 *
	 * 注意：具体使用场景和逻辑需要根据实际的业务需求来实现此注释提供了基本的框架和参数说明
	 */
	open fun loopAfter(elapsedTime: Long): Boolean {
	    return true
	}

	/**
	 * 一个开放函数，用于提供循环执行的逻辑。
	 * 该函数在基类中抛出一个TODO异常，提示子类需要重写该方法。
	 *
	 * @param arg 循环过程中使用的参数，可以为null。
	 * @throws NotImplementedError 当子类未重写此方法时，将抛出该异常。
	 */
	open fun loop(arg: T?) {
	    TODO("loopMethod() 没有重写. 方法是空体")
	}

	/**
	 * 线程的执行逻辑
	 */
	override fun run() {
	    // 主循环，直到线程被中断
	    w1@ while (!this.isInterrupted) {
	        try {
	            // 定义循环开始和结束时间变量
	            var start: Long
	            var end: Long
	            // 根据loopBeforeContinue的值决定是否在循环前执行特定逻辑
	            if (!loopBeforeContinue) {
	                // 执行循环前的操作，并获取结果
	                val before = loopBefore()
	                // 根据loopBeforeNullDetection的值决定是否在检测到null时中断循环
		            if (this.loopBeforeNullDetection) {
			            if (before == null) {
				            break@w1
			            }
		            }
		            if (this.loopBeforeNullContinue) {
			            if (before == null) {
				            continue@w1
			            }
		            }
	                // 如果设置了循环前的延时，则进行延时
	                if (this.loopBeforeSleep != (-1).toLong()) {
	                    sleep(this.loopBeforeSleep)
	                }
	                // 记录循环开始时间
	                start = System.currentTimeMillis()
	                // 执行核心循环逻辑
	                loop(before)
	                // 记录循环结束时间
	                end = System.currentTimeMillis()
	            } else {
	                // 如果loopBeforeContinue为true，直接记录开始时间，执行核心循环逻辑，并记录结束时间
	                start = System.currentTimeMillis()
	                loop(null)
	                end = System.currentTimeMillis()
	            }
	            // 如果设置了循环中的延时，则进行延时
	            if (this.loopSleep != (-1).toLong()) {
	                sleep(this.loopSleep)
	            }
	            // 根据loopAfterContinue的值决定是否在循环后执行特定逻辑
	            if (!loopAfterContinue) {
	                // 执行循环后的操作，并根据返回值判断是否需要再次延时或中断循环
	                if (!loopAfter(end - start)) {
	                    if (this.loopAfterSleep != (-1).toLong()) {
	                        sleep(this.loopAfterSleep)
	                    }
	                    if (loopAfterClose) {
	                        break@w1
	                    }
	                }
	            }
	        } catch (ex: Exception) {
	            // 在打印错误信息的标志为true时，输出异常信息
	            if (printError) {
	                ex.printStackTrace()
	            }
	            // 在错误检测标志为true时，中断循环
	            if (errorDetection) {
	                break@w1
	            }
	        }
	    }
	}

	/**
	 * 关闭当前对象的执行。
	 * 通过中断当前对象的执行来实现关闭操作。
	 */
	override fun close() {
	    this.interrupt()
	}

}