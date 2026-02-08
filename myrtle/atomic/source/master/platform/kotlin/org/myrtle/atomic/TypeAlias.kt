@file:JvmName("TypeAlias")
package org.myrtle.atomic

import org.slf4j.LoggerFactory
import java.io.*
import java.math.BigInteger
import java.sql.*

// 类型别名 Byte = i8
typealias i8 = Byte
// 类型别名 Short = i16
typealias i16 = Short
// 类型别名 Int = i32
typealias i32 = Int
// 类型别名 Long = i64
typealias i64 = Long
// 类型别名 i128 = BigInteger
typealias i128 = BigInteger

// 类型别名 u8 = UByte
typealias u8 = UByte
// 类型别名 u16 = UShort
typealias u16 = UShort
// 类型别名 u32 = UInt
typealias u32 = UInt
// 类型别名 u64 = ULong
typealias u64 = ULong
// 类型别名 i128 = BigInteger
typealias u128 = BigInteger

// 类型别名 kotlin.Byte = byte
typealias byte = Byte
// 类型别名 kotlin.UByte = ubyte
typealias ubyte = UByte
// 类型别名 kotlin.Short = short
typealias short = Short
// 类型别名 kotlin.UShort = ushort
typealias ushort = UShort
// 类型别名 kotlin.Int = int
typealias int = Int
// 类型别名 kotlin.UInt = uint
typealias uint = UInt
// 类型别名 kotlin.Long = long
typealias long = Long
// 类型别名 kotlin.ULong = ulong
typealias ulong = ULong
// 类型别名 kotlin.Float = float
typealias float = Float
// 类型别名 kotlin.Double = double
typealias double = Double
// 类型别名 kotlin.Boolean = boolean
typealias boolean = Boolean
// 类型别名 kotlin.Char = char
typealias char = Char

// 类型别名 Boolean = bool
typealias bool = Boolean
// 类型别名 String = str
typealias str = String
// 类型别名 Any = any
typealias any = Any
// 类型别名 Any = obj
typealias obj = Any
// 类型别名 Unknown = Any?
typealias Unknown = Any?
// 类型别名 unknown = Any?
typealias unknown = Any?
// 类型别名 everyone = Any
typealias everyone = Any
// 类型别名 void = Unit
typealias void = Unit
// 类型别名 Void = Unit
typealias Void = Unit
// 类型别名 nil = Unit
typealias nil = Unit
// 类型别名 None = Unit
typealias None = Unit
// 类型别名 Nil = Unit
typealias Nil = Unit
// 类型别名 none = Unit
typealias none = Unit

typealias Type = Class<*>

typealias type = Class<*>

// 类型别名 os = System
typealias os = System
// 类型别名 system = System
typealias system = System
// 类型别名 error = Exception
typealias error = Exception

typealias Connect = Connection
typealias Stmt = Statement
typealias RSet = ResultSet
typealias PStmt = PreparedStatement
typealias Callable = CallableStatement
typealias Tstamp = TimestampKit
typealias DManager = DriverManager
typealias Log = LoggerFactory
typealias IStream = InputStream
typealias OStream = OutputStream
typealias BIStream = BufferedInputStream
typealias FIStream = FileInputStream
typealias FOStream = FileOutputStream
typealias BOStream = BufferedOutputStream
typealias PStream = PrintStream
typealias BAOStream = ByteArrayOutputStream
typealias BAIStream = ByteArrayInputStream
typealias OIStream = ObjectInputStream
typealias OOStream = ObjectOutputStream
typealias IReader = InputStreamReader
typealias OWriter = OutputStreamWriter
typealias FWriter = FileWriter
typealias FReader = FileReader
typealias BWriter = BufferedWriter
typealias BReader = BufferedReader
typealias cl = ClassLoader
