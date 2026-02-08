package org.myrtle.citrus.util

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonWriter
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * JsonKit对象，继承自Raw类
 * 该对象提供了一系列处理JSON数据的工具方法
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
object JsonKit {
	/**
	 * 定义了数据解析的四种模式，用于指定如何对数据进行解析。
	 * 这些模式包括：
	 * - Json：JSON解析模式。
	 * - Jpp：JSON路径解析模式。
	 * - Efwf：每行固定宽度格式解析模式。
	 * - JppAndEfwf：结合了Jpp和Efwf特点的解析模式。
	 */
	enum class Mode {
	    Json, Jpp, Efwf, JppAndEfwf
	}

	/**
	 * 将对象转换为JSON格式的字节流
	 *
	 * @param mode 转换模式，决定了Gson对象的配置方式
	 * @param t 需要转换为JSON的任意对象
	 * @return 包含JSON数据的字节流 ByteArrayOutputStream
	 */
	private fun toJsonBAOS(mode: Mode, t: Any): ByteArrayOutputStream {
	    // 创建一个字节流，用于存储最终的JSON数据
	    val baos = ByteArrayOutputStream()
	    // 使用OutputStreamWriter包装baos，确保可以写入字符流
	    OutputStreamWriter(baos).use { osw ->
	        // 使用JsonWriter对osw进行进一步包装，以便进行JSON写入操作
	        JsonWriter(osw).use { jsonWriter ->
	            // 根据不同的模式，创建不同的Gson对象，并将对象t转换为JSON格式，写入jsonWriter
	            when (mode) {
	                Mode.Json -> {
	                    GsonBuilder().create().toJson(t, t::class.java, jsonWriter)
	                    jsonWriter.flush()
	                }

	                Mode.Jpp -> {
	                    GsonBuilder().setPrettyPrinting().create().toJson(t, t::class.java, jsonWriter)
	                    jsonWriter.flush()
	                }

	                Mode.Efwf -> {
	                    GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
	                        .toJson(t, t::class.java, jsonWriter)
	                    jsonWriter.flush()
	                }

	                Mode.JppAndEfwf -> {
	                    GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create()
	                        .toJson(t, t::class.java, jsonWriter)
	                    jsonWriter.flush()
	                }
	            }
	        }
	    }
	    // 返回存储了JSON数据的字节流
	    return baos
	}

	/**
	 * 将给定对象转换为JSON字符串
	 *
	 * 此函数的目的是将传入的对象转换为JSON格式的字符串
	 * 它首先将对象转换为字节流，然后将字节流转换为字符串
	 *
	 * @param mode 转换模式，决定了如何将对象转换为JSON
	 * @param obj 需要转换为JSON字符串的对象
	 * @return 转换后的JSON字符串
	 */
	fun toJsonString(mode: Mode, obj: Any): String {
	    return String(toJsonBAOS(mode, obj).toByteArray())
	}

	/**
	 * 将对象转换为JSON格式的字节数组。
	 *
	 * 此函数接受一个模式和一个对象作为参数，将对象转换为JSON格式的字节数组。
	 * 转换过程中使用的模式通过`mode`参数指定，转换后的JSON对象以字节数组的形式返回。
	 *
	 * @param mode 转换模式，决定了如何将对象转换为JSON。
	 * @param obj 需要转换为JSON格式的对象。
	 * @return 转换后的JSON对象的字节数组表示。
	 */
	fun toJsonBytes(mode: Mode, obj: Any): ByteArray {
	    return toJsonBAOS(mode, obj).toByteArray()
	}

	/**
	 * 将对象转换为JSON格式，并返回对应的输出流
	 *
	 * 此函数提供了一个将任意对象转换为JSON格式，并将其存储在一个输出流中的便捷方式
	 * 它封装了内部的转换过程，使外部调用更加简洁
	 *
	 * @param mode 转换模式，决定了如何将对象转换为JSON
	 * @param obj 需要转换为JSON的任意对象
	 * @return OutputStream 包含转换后的JSON数据的输出流
	 */
	fun toJsonStream(mode: Mode, obj: Any): OutputStream {
	    return toJsonBAOS(mode, obj)
	}

	/**
	 * 将对象转换为JSON格式的CharSequence字符串。
	 *
	 * 此函数接收一个模式和任意对象，将该对象转换为JSON格式的字符串，
	 * 并返回该字符串的CharSequence视图。
	 *
	 * @param mode 转换的模式，用于控制转换行为。
	 * @param obj 需要转换为JSON字符串的对象。
	 * @return 返回转换后的JSON字符串的CharSequence视图。
	 */
	fun toJsonCharSequence(mode: Mode, obj: Any): CharSequence {
	    // 将对象转换为JSON格式的字符串
	    val str = toJsonString(mode, obj)
	    // 返回字符串的CharSequence视图
	    return str.subSequence(0, str.length)
	}

	/**
	 * 将给定对象转换为字符数组。
	 *
	 * @param mode 指定转换模式，决定如何将对象转换为字符串。
	 * @param obj 待转换的任意对象。
	 * @return 返回转换后的字符串对应的字符数组。
	 *
	 * 此函数的目的是将任意对象首先按照指定模式转换为JSON格式的字符串，
	 * 然后将该字符串转换为字符数组。这样做可以在需要时，方便地将对象
	 * 转换为字符数组形式，便于进一步处理或分析。
	 */
	fun toChars(mode: Mode, obj: Any): CharArray {
	    return toJsonString(mode, obj).toCharArray()
	}

}