package org.myrtle.citrus.io

import com.sun.jna.Platform
import org.myrtle.citrus.util.Citrus.getAllFile
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarBuilder
import me.tongfei.progressbar.ProgressBarStyle
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.io.path.isExecutable

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class CPackage {
	var config = hashMapOf<String, String>()
	private var packageSize: Int = 1024
	private var inputPath: Path? = null
	private var outputPath: Path? = null

	//private var compression: Boolean = true
	val version: Long = 240311

	@JvmField
	val name: String = "CPackage"
	private var ck = CPackageKit<Any>(CPackageCompressionType.CBZip2)

	fun setPackageSize(packageSize: Int) {
		this.packageSize = packageSize
	}

	fun setType(cct: CPackageCompressionType) {
		this.ck = CPackageKit<Any>(cct)
	}

	fun setInput(input: Path) {
		this.inputPath = input
	}

	fun setOutput(output: Path) {
		this.outputPath = output
	}

	constructor()
	constructor(config: HashMap<String, String>) {
		this.config = config
	}

	constructor(config: HashMap<String, String>, packageSize: Int) {
		this.config = config
		this.packageSize = packageSize
	}

	constructor(input: Path, output: Path) {
		this.inputPath = input
		this.outputPath = output
	}

	constructor(packageSize: Int, input: Path, output: Path) {
		this.packageSize = packageSize
		this.inputPath = input
		this.outputPath = output
	}

	constructor(config: HashMap<String, String>, input: Path, output: Path) {
		this.config = config
		this.inputPath = input
		this.outputPath = output
	}

	private fun setDConfig(map: HashMap<String, String>) {
		map[format("Size")] = packageSize.toString()
		map[format("Type")] = ck.cct.name
		//map[format("UID")] =
		//UUID.nameUUIDFromBytes(FileUtils.sizeOf(input!!.toFile()).toString().toByteArray()).toString()
		map[format("Version")] = version.toString()
	}

	fun format(k: String): String {
		return "${name}.${k}"
	}

	fun archiving(): Boolean {
		//// _log.info("[ 进行 ]: 压缩")
		val connection = initPackage(outputPath ?: throw NullPointerException("参数output未空。"))
			?: throw SQLException("CPackage初始化失败.")
		setDConfig(config)
		addConfig(connection, config)
		val files = arrayListOf<File>()
		if (inputPath == null) {
			throw NullPointerException("参数input未空")
		}
		getAllFile(inputPath!!.toFile().absoluteFile, files)
		addData(connection, packageSize, files, inputPath!!.toFile())
		//// _log.info("[ 完成 ]: 压缩")
		return true
	}

	fun initPackage(output: Path): Connection? {
		// // _log.info("[ 进行 ]: 初始化Package数据库")
		return try {
			//// _log.info("初始化CPackage...")
			Class.forName("org.sqlite.JDBC")
			val connection = DriverManager.getConnection( "JDBC:SQLite:${output.toFile().absoluteFile}")
			//// _log.info("初始化CPackage.完成")
			//// _log.info("[ 完成 ]: 初始化Package数据库")
			connection
		} catch (error: Exception) {
			//// _log.org.myrtle.error("初始化CPackage.失败")
			// _log.info("[ 错误 ]: 初始化Package数据库 [${org.myrtle.error.toString()}]", org.myrtle.error)
			error.printStackTrace()
			null
		}
	}

	fun addConfig(connection: Connection, config: HashMap<String, String>) {
		// _log.info("[ 进行 ]: 写入Config")
		createConfigTable(connection)
		connection.prepareStatement("insert into \"{0xC:${this.name}}:{!Config}\"(\"Key\",\"Value\") values (?,?);")
			.use {
				config.forEach { (k, v) ->
					// _log.info("[ 写入 ]: [${k}] [${v.toChars("*")}]")
					it.setString(1, k)
					it.setBytes(2, v.toByteArray(Charset.defaultCharset()))
					it.executeUpdate()
				}
			}
		// _log.info("[ 完成 ]: 写入Config")
	}

	fun createConfigTable(connection: Connection) {
		// _log.info("[ 进行 ]: 创建Config表")
		connection.createStatement().use {
			it.executeUpdate("create table \"{0xC:${this.name}}:{!Config}\"(\"Key\" varchar(500) unique not null primary key ,\"Value\" blob);")
		}
		// _log.info("[ 完成 ]: 创建Config表")
	}

	fun addData(connection: Connection, size: Int, files: ArrayList<File>, input: File) {
		val bar = ProgressBar("archiving", files.size.toLong())
		// _log.info("[ 写入 ]: 到CPackage")
		createIndexTable(connection)
		connection.prepareStatement("insert into \"{0xC:${this.name}}:{Data}:{!Index}\"(\"Path\",\"size\") values (?,?);")
			.use { index ->
				files.forEach { it1 ->
					//// _log.info("写入文件[${it1}]...")
					val fh: String = if (Platform.isWindows()) {
						"\\"
					} else {
						"/"
					}
					val file = it1.absolutePath.replace(input.absolutePath + fh, "")
					connection.createStatement().use { s ->
						s.executeUpdate("create table \"{0xC:${this.name}}:{Data}:{${file}}\"(\"Index\" integer not null unique primary key autoincrement ,\"Data\" blob);")
					}
					val name = this.name
					val into = object {
						val s =
							connection.prepareStatement("insert into \"{0xC:${name}}:{Data}:{${file}}\"(\"Data\") values (?);")

						fun add(byteArray: ByteArray) {
							s.setBytes(1, byteArray)
							s.executeUpdate()
						}
					}
					//val into = connection.prepareStatement("insert into \"CPackage.Data:${org.myrtle.file}\"(\"Data\") values (?);")
					val fis = FileInputStream(it1)
					val buffer = ByteArray(size)
					var n: Int
					var num: Long = 0
					while (fis.read(buffer).also { n = it } != -1) {
						//// _log.info("分段读取[${it1}] 缓存[${buffer.size}] 分段[${num}]")
						ByteArrayOutputStream().use { baos ->
							baos.write(buffer, 0, n)
							into.add(ck.compression(baos.toByteArray()))
							//into.setBytes(1, compression(baos.toByteArray()))
							//into.executeUpdate()
						}
						num++
					}
					index.setString(1, file)
					index.setLong(2, num)
					index.executeUpdate()
					into.s.close()
					//// _log.info("[ 写入 ]: 文件[${it1}]")
					bar.step()
				}
			}
		bar.close()
		//// _log.info("[ 完成 ]: 写入到CPackage")
	}

	fun createIndexTable(connection: Connection) {
		// _log.info("[ 创建 ]: 创建Index")
		connection.createStatement().use {
			it.executeUpdate("create table \"{0xC:${this.name}}:{Data}:{!Index}\"(\"Path\" varchar(500) unique not null primary key ,\"size\" org.myrtle.long);")
		}
	}

	fun unPackage(): HashMap<String, String> {
		// _log.info("[ 解压 ]: 正在解压")
		if (inputPath == null) {
			throw NullPointerException("参数input未空")
		}
		if (outputPath == null) {
			throw NullPointerException("参数input未空")
		}
		if (inputPath!!.isExecutable()) {
			println(false)
		}
		if (!outputPath!!.isExecutable()) {
			Files.createDirectories(outputPath!!)
		}
		val connection = initPackage(inputPath!!) ?: throw SQLException("null")
		val config = readConfig(connection)
		val index = readIndex(connection)
		if (config[format("Size")] == null) {
			throw NullPointerException("CPackage.Size不存在无法拆包")
		}
		writingLocally(connection, index, outputPath!!, config[format("Size")]!!.toInt())
		// _log.info("[ 完成 ]: 正在解压")
		return config
	}

	fun readConfig(connection: Connection): HashMap<String, String> {
		// _log.info("[ 进行 ]: 读取Config")
		val map = hashMapOf<String, String>()
		connection.createStatement().use {
			it.executeQuery("select \"Key\",\"Value\" from \"{0xC:${this.name}}:{!Config}\";").let { rs ->
				while (rs.next()) {
					val k = rs.getString("Key")
					val v = String(rs.getBytes("Value"), Charset.defaultCharset())
					// _log.info("[ 读取 ]: [${k}] [${v.toChars("*")}]")
					map[k] = v
				}
			}
		}
		ck = CPackageKit<Any>(CPackageCompressionType.valueOf(config[format("Type")] ?: ck.cct.name))
		// _log.info("[ 完成 ]: 读取Config")
		return map
	}

	fun readIndex(connection: Connection): HashMap<String, Long> {
		// _log.info("[ 进行 ]: 读取Index")
		val map = hashMapOf<String, Long>()
		connection.createStatement().use {
			it.executeQuery("select \"Path\",\"size\" from \"{0xC:${this.name}}:{Data}:{!Index}\";").let { rs ->
				while (rs.next()) {
					map[rs.getString("Path")] = rs.getLong("size")
				}
			}
		}
		// _log.info("[ 解压 ]: 读取Index")
		return map
	}

	fun writingLocally(connection: Connection, index: HashMap<String, Long>, output: Path, size: Int): Boolean {
		val br = ProgressBarBuilder().setStyle(ProgressBarStyle.UNICODE_BLOCK).setInitialMax(index.size.toLong())
			.setTaskName("unPackage").build()
		//val br = ProgressBar("unPackage", index.size.toLong())
		var num = 0
		val clist = arrayListOf<CUNPakcageThread>()
		index.forEach { (k, v) ->
			val cp = CUNPakcageThread(
				k,
				v,
				connection,
				output,
				size,
				num,
				this.ck.cct,
				this.name
			)
			cp.run()
			clist.add(cp)
			num++
		}
		// _log.info("[ 等待 ]: 等待全部线程运行完成")
		clist.forEach {
			while (!it.status) {
				Thread.sleep(10)
			}
			br.step()
		}
		// _log.info("[ 完成 ]: 等待全部线程运行完成")
		br.close()
		return true
	}
}