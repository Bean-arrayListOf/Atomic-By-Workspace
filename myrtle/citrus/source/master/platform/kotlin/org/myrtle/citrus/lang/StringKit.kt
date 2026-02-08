package org.myrtle.citrus.lang

import java.io.StringReader
import java.io.StringWriter

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class StringKit {
	//    class S{
//        class t{
//            class r{
//                class i{
//                    class n{
//                        companion object{
//                            fun g():String?{
//                                val r = Random()
//                                if (r.nextInt(100)==r.nextInt(100)){
//                                    return ByteKit.Base64.doFind(ByteKit.Mode.Encoder,"恭喜你发现了彩蛋[彩蛋]${System.currentTimeMillis()}")
//                                }
//                                return null
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
	companion object {
		@JvmStatic
		fun toBuilder() = StringBuilder()

		@JvmStatic
		fun toBuilder(str: String) = StringBuilder(str)

		@JvmStatic
		fun toBuilder(capacity: Int) = StringBuilder(capacity)

		@JvmStatic
		fun toBuilder(seq: CharSequence) = StringBuilder(seq)

		@JvmStatic
		fun toBuffer() = StringBuffer()

		@JvmStatic
		fun toBuffer(str: String) = StringBuffer(str)

		@JvmStatic
		fun toBuffer(capacity: Int) = StringBuffer(capacity)

		@JvmStatic
		fun toBuffer(seq: CharSequence) = StringBuffer(seq)

		@JvmStatic
		fun toReader(str: String) = StringReader(str)

		@JvmStatic
		fun toWriter() = StringWriter()

		@JvmStatic
		fun toWriter(initialSize: Int) = StringWriter(initialSize)

		@JvmStatic
		fun join(str1: String, str2: String) = toBuffer(str1).append(str2).toString()

		@JvmStatic
		fun join(char: String, iter: Iterable<Any>): String {
			val s = toBuffer()
			for (i in iter) {
				s.append(i.toString()).append(char)
			}
			return s.toString().substring(0, s.length - 1)
		}

		@JvmStatic
		fun isAscii(str: String): Boolean {
			return str.all { it.code <= 127 }
		}

		@JvmStatic
		fun join(char: String, vararg t: Any): String {
			val s = toBuffer()
			for (i in t) {
				s.append(i.toString()).append(char)
			}
			return s.toString().substring(0, s.length - 1)
		}

		@JvmStatic
		fun getTree(e: Exception) {
			format(org.myrtle.citrus.Error.Markers("Markers", e)).forEach {
				System.err.println(it)
			}
		}

		@JvmStatic
		fun format(e: Throwable): List<String> {
			val stack = e.stackTrace
			val list = arrayListOf<String>()
			stack.reverse()
			var cau: Throwable? = e
			while (true) {
				if (cau != null) {
					val stack1 = cau.stackTrace
					stack1.reverse()
					for (i in stack1.indices) {
						val str = StringBuffer("|\t - ${i + 1}.at ")
						stack1[i].fileName?.let { str.append(it) }
						if (stack1[i].className.isNotBlank() || stack1[i].className.isNotEmpty()) {
							str.append(" < ")
							str.append(stack1[i].className)
						}
						if (stack1[i].methodName.isNotBlank() || stack1[i].methodName.isNotEmpty()) {
							str.append(" < ")
							str.append(stack1[i].methodName)
						}
						if (stack[i].lineNumber != -1) {
							str.append(" < ")
							str.append(stack1[i].lineNumber)
						}
						list.add(str.toString())
					}
					list.add("+ ${cau::class.java.canonicalName}")
					cau = cau.cause
				} else {
					break
				}
			}
			list.reverse()
			list.add("|")
			var ausm: Throwable? = e
			while (true) {
				if (ausm != null) {
					ausm.message?.let {
						list.add("+ ${ausm!!::class.java.canonicalName}")
						val s = it.split("\n")
						for (i in s.indices) {
							list.add("|\t - ${i + 1}.msg ${s[i]}")
						}
					}
					ausm = ausm.cause
				} else {
					break
				}
			}
			return list
		}
	}
}