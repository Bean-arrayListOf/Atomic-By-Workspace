package org.myrtle.citrus.util

import java.io.FileNotFoundException
import java.io.IOException

object ErrorPlan {
	private val eps = ArrayList<ErrorPlanExamples>()

	init {
		// 主动触发
		eps.add(ErrorPlanExamples(1000L,"主动触发","Active trigger","ACTIVE_TRIGGER",1L, RuntimeException::class.java))
		// 被动触发
		eps.add(ErrorPlanExamples(1001L,"被动触发","Passive trigger","PASSIVE_TRIGGER",1L, RuntimeException::class.java))
		// 计划内错误
		eps.add(ErrorPlanExamples(1002L,"计划内错误","Plan error","PLAN_ERROR",1L, RuntimeException::class.java))
		// 运行时错误
		eps.add(ErrorPlanExamples(1003L,"运行时错误","Runtime error","RUNTIME_ERROR",1L, RuntimeException::class.java))
		// 计划外错误
		eps.add(ErrorPlanExamples(1004L,"计划外错误","Out of plan error","OUT_OF_PLAN_ERROR",1L, RuntimeException::class.java))
		// 空指针异常
		eps.add(ErrorPlanExamples(1005L,"空指针异常","Null pointer exception","NULL_POINTER_EXCEPTION",1L, NullPointerException::class.java))
		// 数组越界异常
		eps.add(ErrorPlanExamples(1006L,"数组越界异常","Array out of bounds exception","ARRAY_OUT_OF_BOUNDS_EXCEPTION",1L, ArrayIndexOutOfBoundsException::class.java))
		// 数组下标越界异常
		eps.add(ErrorPlanExamples(1007L,"数组下标越界异常","Array index out of bounds exception","ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION",1L, ArrayIndexOutOfBoundsException::class.java))
		// 数组大小异常
		eps.add(ErrorPlanExamples(1008L,"数组大小异常","Array size exception","ARRAY_SIZE_EXCEPTION",1L, ArrayIndexOutOfBoundsException::class.java))
		// 数组元素为空异常
		eps.add(ErrorPlanExamples(1009L,"数组元素为空异常","Array element is null exception","ARRAY_ELEMENT_IS_NULL_EXCEPTION",1L, ArrayIndexOutOfBoundsException::class.java))
		// 方法不存在
		eps.add(ErrorPlanExamples(1010L,"方法不存在","Method not found","METHOD_NOT_FOUND",1L, NoSuchMethodException::class.java))
		// 类不存在
		eps.add(ErrorPlanExamples(1011L,"类不存在","Class not found","CLASS_NOT_FOUND",1L, ClassNotFoundException::class.java))
		// 文件不存在
		eps.add(ErrorPlanExamples(1012L,"文件不存在","File not found","FILE_NOT_FOUND",1L, FileNotFoundException::class.java))
		// 输入输出错误
		eps.add(ErrorPlanExamples(1013L,"输入输出错误","Input/output error","INPUT_OUTPUT_ERROR",1L, IOException::class.java))
		// 文件访问错误
		eps.add(ErrorPlanExamples(1014L,"文件访问错误","File access error","FILE_ACCESS_ERROR",1L, IOException::class.java))
		// 权限不足
		eps.add(ErrorPlanExamples(1015L,"权限不足","Permission denied","PERMISSION_DENIED",1L, IOException::class.java))
		// 文件已存在
		eps.add(ErrorPlanExamples(1016L,"文件已存在","File already exists","FILE_ALREADY_EXISTS",1L, IOException::class.java))
		// 运行未达到预期
		eps.add(ErrorPlanExamples(1017L,"运行未达到预期","Run not reached expected","RUN_NOT_REACHED_EXPECTED",1L, RuntimeException::class.java))
		// 运行已超出预期
		eps.add(ErrorPlanExamples(1018L,"运行已超出预期","Run exceeded expected","RUN_EXCEEDED_EXPECTED",1L, RuntimeException::class.java))
		// 结果未达到预期
		eps.add(ErrorPlanExamples(1019L,"结果未达到预期","Result not reached expected","RESULT_NOT_REACHED_EXPECTED",1L, RuntimeException::class.java))
		// 超时
		eps.add(ErrorPlanExamples(1020L,"超时","Timeout","TIMEOUT",1L, RuntimeException::class.java))
		// 抽象方法没有实现
		eps.add(ErrorPlanExamples(1021L,"抽象方法没有实现","Abstract method not implemented","ABSTRACT_METHOD_NOT_IMPLEMENTED",1L, RuntimeException::class.java))
		// 接口没有实现
		eps.add(ErrorPlanExamples(1022L,"接口没有实现","Interface not implemented","INTERFACE_NOT_IMPLEMENTED",1L, RuntimeException::class.java))
	}
}