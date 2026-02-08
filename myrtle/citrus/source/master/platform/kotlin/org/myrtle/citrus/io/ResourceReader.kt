package org.myrtle.citrus.io

import org.myrtle.citrus.lang.StochasticThread
import org.myrtle.citrus.util.Citrus
import org.mapdb.*
import java.io.*
import java.nio.charset.Charset

/**
 * @author CitrusCat
 * @since 2024/12/26
 *
 * ResourceReader 类用于读取资源文件
 * 它实现了 Closeable 接口，因此可以使用 try-with-resources 语句进行自动关闭
 */
class ResourceReader : Closeable {

	// 定义一个私有的变量mapName，用于存储资源映射的名称
	private var mapName: String = "ResourceMap"

	// 定义一个私有的变量db，用于存储数据库实例
	private lateinit var db: DB

	// 定义一个变量encoder，用于存储字符集编码器实例
	lateinit var encoder: Charset

	// 定义一个变量createTime，用于存储创建时间，初始值为0
	var createTime: Long = 0

	private var temp: File? = null

	/**
	 * 设置并使用指定的map名称
	 *
	 * @param mapName 需要设置的map名称
	 */
	fun use(mapName: String) {
		this.mapName = mapName
	}

	companion object {
		@JvmStatic
		fun now(file: String): ResourceReader {
			return ResourceReader(file)
		}

		@JvmStatic
		fun now(file: File): ResourceReader {
			return ResourceReader(file)
		}

		@JvmStatic
		fun now(stream: InputStream): ResourceReader {
			return ResourceReader(stream)
		}
	}

	constructor(file: String) {
		Citrus.getInputStream(file).use { input ->
			temp = File.createTempFile("${StochasticThread().nextLong()}", ".rmap")
			FileOutputStream(temp!!).use { output ->
				input.copyTo(output)
			}
		}
		db = DBMaker.fileDB(temp!!).readOnly().fileMmapEnable().make()
		readConfig()
	}

	constructor(file: File) {
		db = DBMaker.fileDB(file).readOnly().fileMmapEnable().make()
		readConfig()
	}

	constructor(stream: InputStream) {
		stream.use { input ->
			temp = File.createTempFile("${StochasticThread().nextLong()}", ".rmap")
			FileOutputStream(temp!!).use { output ->
				input.copyTo(output)
			}
		}
		db = DBMaker.fileDB(temp!!).readOnly().fileMmapEnable().make()
		readConfig()
	}

	/**
	 * 加载数据库文件并进行初始化配置
	 *
	 * 此函数用于指定数据库文件路径，并通过一系列配置项来创建和打开数据库
	 * 它确保了数据库以只读方式打开，并启用了内存映射视图，以优化数据读取性能
	 * 同时，通过调用`initConfig`函数进行进一步初始化配置
	 *
	 * @param file 数据库文件的路径
	 */
	@Deprecated("已弃用请使用构造函数")
	fun load(file: String) {
		// 创建数据库实例，指定数据库文件路径，设置只读模式和启用内存映射视图
		db = DBMaker.fileDB(file).readOnly().fileMmapEnable().make()
		// 初始化数据库配置
		readConfig()
	}

	/**
	 * 从指定文件加载数据库
	 *
	 * 此方法使用DBMaker创建一个只读的内存映射数据库，该数据库将存储在指定的文件中
	 * 在数据库加载后，会进行初始化配置
	 *
	 * @param file 数据库文件路径，用于指定数据库存储的位置和文件名
	 */
	@Deprecated("已弃用请使用构造函数")
	fun load(file: File) {
		// 创建一个只读的内存映射数据库，使用指定的文件作为存储
		db = DBMaker.fileDB(file).readOnly().fileMmapEnable().make()
		// 初始化数据库的配置，确保数据库按照预期的方式运行
		readConfig()
	}

	/**
	 * 从输入流加载数据并初始化数据库
	 *
	 * 此方法首先将输入流中的数据写入到一个临时文件中，然后使用这个临时文件创建一个内存映射的只读数据库
	 * 这样做有几个好处：首先，可以将数据持久化到磁盘上，避免在程序运行期间的数据丢失；
	 * 其次，通过内存映射可以提高数据访问速度；最后，设置数据库为只读状态可以提高数据安全性
	 *
	 * @param stream 输入流，包含需要加载到数据库的数据
	 */
	@Deprecated("已弃用请使用构造函数")
	fun load(stream: InputStream) {
		// 创建一个临时文件，以当前系统时间戳命名，附加“.ojb”后缀
		val tempFile = File.createTempFile(System.currentTimeMillis().toString(), ".resource.org.myrtle.obj")

		// 使用临时文件创建一个文件输出流，并在使用完毕后自动关闭
		FileOutputStream(tempFile).use {
			// 读取输入流中的所有字节，并写入到文件输出流中
			it.write(stream.readBytes())
		}

		// 使用临时文件创建一个内存映射的只读数据库，并启用文件内存映射
		db = DBMaker.fileDB(tempFile).readOnly().fileMmapEnable().make()

		// 初始化数据库配置
		readConfig()
	}

	/**
	 * 读取配置信息
	 *
	 * 此函数负责从数据库的指定表中读取资源配置信息，主要包括编码方式和创建时间
	 * 首先尝试从配置中读取编码方式（encode），如果未指定，则使用系统默认编码
	 * 然后尝试读取创建时间（time），如果未指定，则默认为0
	 * 在读取过程中，如果发生任何异常，将默认使用UTF-8作为编码方式
	 */
	fun readConfig() {
		try {
			// 打开并使用配置表，使用字符串作为键和值的序列化器
			db.hashMap("ResourceConfig").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).open()
				.use { config ->
					// 从配置中获取编码方式，如果未设置，则使用系统默认编码
					var tencode = config["encode"]
					if (tencode == null) {
						tencode = Charset.defaultCharset().name()
					}
					encoder = Charset.forName(tencode)

					// 从配置中获取创建时间，如果未设置，则默认为0
					var time = config["time"]
					if (time == null) {
						time = "0"
					}
					createTime = time.toLong()
				}
		} catch (e: Exception) {
			// 如果读取配置时发生异常，使用UTF-8作为默认编码方式
			encoder = Charset.forName("UTF-8")
		}
	}

	/**
	 * 从输入流加载资源
	 *
	 * 此函数提供了一个封装，通过输入流参数来加载资源，而不是直接从文件系统或其他特定来源加载
	 * 它允许以更灵活的方式加载资源，例如从网络、文件或任何支持输入流的地方
	 *
	 * @param inputStream 资源的输入流从这里加载
	 */
	@Deprecated("已弃用请使用构造函数")
	fun loadResource(inputStream: InputStream) {
		return load(inputStream)
	}

	/**
	 * 加载资源文件
	 *
	 * 本函数通过类加载器获取资源文件的输入流，然后将其作为参数传递给另一个加载函数
	 * 它提供了一种从文件中加载资源的便捷方式，利用了Kotlin的反射功能
	 *
	 * @param file 要加载的资源文件，通过File对象表示
	 * @throws FileNotFoundException 如果资源文件不存在或无法通过类加载器访问，会抛出异常
	 */
	@Deprecated("已弃用请使用构造函数")
	fun loadResource(file: File) {
		load(
			this::class.java.classLoader.getResourceAsStream(file.path)
				?: throw FileNotFoundException("org.myrtle.citrus.testCode.Resource org.myrtle.file not found: $file")
		)
	}

	/**
	 * 加载资源文件
	 *
	 * 本函数通过类加载器获取资源文件的输入流，如果文件不存在，则抛出FileNotFoundException异常
	 * 使用场景包括但不限于读取配置文件、数据文件等资源
	 *
	 * @param file 资源文件的路径
	 * @throws FileNotFoundException 如果资源文件不存在
	 */
	@Deprecated("已弃用请使用构造函数")
	fun loadResource(file: String) {
		load(
			this::class.java.classLoader.getResourceAsStream(file)
				?: throw FileNotFoundException("org.myrtle.citrus.testCode.Resource org.myrtle.file not found: $file")
		)
	}

	/**
	 * 获取 HTreeMap
	 *
	 * 此方法用于从数据库中获取一个 HTreeMap 实例，该实例用于存储键值对，其中键为字符串类型，值为字节数组类型
	 * 它通过指定的 mapName 来访问特定的 hash map，如果没有指定，则使用类中预设的 mapName
	 *
	 * @param mapName Hash map 的名称，用于标识特定的 hash map，默认值为 this.mapName
	 * @return 返回一个打开状态的 HTreeMap 实例，该实例的键为字符串类型，值为字节数组类型
	 */
	protected fun getHTreeMap(mapName: String = this.mapName): HTreeMap<String, ByteArray> {
		return db.hashMap(mapName).keySerializer(Serializer.STRING).valueSerializer(Serializer.BYTE_ARRAY).open()
	}

	/**
	 * 根据给定的键获取BTreeMap实例
	 *
	 * 此函数用于从数据库中获取与指定资源键关联的BTreeMap对象该键由字符串表示
	 * 它在内部使用LONG序列化器对键进行处理，使用BYTE_ARRAY序列化器对值进行处理
	 *
	 * @param key 资源的唯一键，用于标识特定的BTreeMap
	 * @return 返回与指定键关联的BTreeMap，该Map的键类型为Long，值类型为ByteArray
	 */
	protected fun getBTreeMap(key: String): BTreeMap<Long, ByteArray> {
		return db.treeMap("\$[${mapName}]:[Stream]:[${key}]").keySerializer(Serializer.LONG)
			.valueSerializer(Serializer.BYTE_ARRAY).open()
	}

	/**
	 * 将当前对象转换为 Map<String, ByteArray> 类型
	 *
	 * 此函数提供了一种转换机制，使得可以将自定义对象根据其内部 mapName 属性关联的 HTreeMap 实例转换为标准的 Map 对象
	 * 主要用于需要将 HTreeMap 实例转换为标准 Map 类型时，提高了代码的灵活性和可用性
	 *
	 * @return Map<String, ByteArray> 转换后的标准 Map 对象，其中键为 String 类型，值为 ByteArray 类型
	 */
	fun toMap(): Map<String, ByteArray> {
		getHTreeMap(this.mapName).use {
			return it.toMap()
		}
	}

	/**
	 * 将指定名称的 Hash Tree (HTree) 转换为 Map 对象
	 * 此方法主要用途在于从特定的 Hash Tree 中获取数据，并将其格式转换为 Map 对象，以便于使用
	 *
	 * @param mapName Hash Tree 的名称，用于标识和获取特定的 Hash Tree 实例
	 * @return 返回一个 Map 对象，该对象包含了指定 Hash Tree 的所有键值对数据，其中键为 String 类型，值为 ByteArray 类型
	 */
	fun toMap(mapName: String): Map<String, ByteArray> {
		// 获取指定名称的 Hash Tree 实例，并在使用完毕后自动释放资源
		getHTreeMap(mapName).use {
			// 将 Hash Tree 实例转换为 Map 对象并返回
			return it.toMap()
		}
	}

	/**
	 * 根据给定的键获取资源输入流
	 *
	 * 此函数的目的是通过提供的键获取相应的资源输入流对象它隐藏了获取资源的内部细节，
	 * 使得外部只需提供键即可方便地获取到相应的输入流对象
	 *
	 * @param key 资源的键，用于定位特定的资源
	 * @return 返回一个InputStream对象，用于读取资源内容
	 */
	fun getStream(key: String): InputStream {
		return ResourceInputStream(getBTreeMap(key))
	}

	/**
	 * 获取所有Map名称
	 *
	 * 此函数从数据库中检索并返回所有地图的名称列表它不存在参数，只是简单地委托数据库操作来获取名称集合
	 *
	 * @return 返回一个包含所有地图名称的字符串列表
	 */
	fun getAllMapName(): List<String> {
		val list = arrayListOf<String>() // 创建一个空的字符串列表，用于存储地图名称
		list.addAll(db.getAllNames()) // 将数据库中获取的所有名称添加到列表中
		return list // 返回包含所有地图名称的列表
	}

	/**
	 * 获取哈希树映射的所有键集合
	 *
	 * 此函数提供了一种从哈希树映射中检索所有键的方法，返回一个集合，其中包含了映射中的所有键
	 * 使用`use`函数确保在使用完哈希树映射后，能够正确地释放相关资源
	 *
	 * @return Set<String> 返回一个包含所有键的集合
	 */
	fun keys(): Set<String> {
		getHTreeMap().use {
			return it.keys
		}
	}

	/**
	 * 获取指定名称的Map对象的所有键集合
	 *
	 * 此函数的目的是为了提供一个方便的方法来获取Map的键集合，避免调用者直接与Map对象交互
	 * 它内部使用了一个特定的机制来获取Map对象，并确保使用完毕后正确处理，这里使用了`use`函数来保证
	 *
	 * @param mapName Map的名称，用于内部查找和识别特定的Map对象
	 * @return 返回一个包含所有键的集合
	 */
	fun keys(mapName: String): Set<String> {
		// 获取指定名称的Map对象，并在使用完毕后正确处理
		getHTreeMap(mapName).use {
			// 返回Map对象的所有键集合
			return it.keys
		}
	}

	/**
	 * 返回哈希树映射中的所有值的集合
	 *
	 * @return Collection<ByteArray?> 哈希树映射中所有值的集合，可能包含null值
	 */
	fun values(): Collection<ByteArray?> {
		// 获取并使用哈希树映射对象
		getHTreeMap().use {
			// 直接返回哈希树映射中的所有值集合
			return it.values
		}
	}

	/**
	 * 根据指定的映射名称，获取该映射的所有值集合
	 *
	 * @param mapName 映射的名称，用于标识和获取特定的映射
	 * @return 返回一个包含指定映射所有值的集合，每个值都是一个字节数组
	 */
	fun values(mapName: String): Collection<ByteArray?> {
		// 获取名为mapName的Hierarchical Tree Map，并对其进行操作
		getHTreeMap(mapName).use {
			// 返回映射的所有值集合
			return it.values
		}
	}

	/**
	 * 返回当前Huffman树中节点的数量
	 *
	 * @return 当前Huffman树中节点的数量
	 */
	fun size(): Int {
		// 获取并使用Huffman树映射对象
		getHTreeMap().use {
			// 返回Huffman树映射对象的大小，即节点数量
			return it.size
		}
	}

	/**
	 * 获取指定名称的HashMap的大小
	 *
	 * @param mapName HashMap的名称，用于标识具体的HashMap
	 * @return 指定名称HashMap的大小
	 */
	fun size(mapName: String): Int {
		// 获取指定名称的HashMap，并在使用完毕后自动关闭，释放资源
		getHTreeMap(mapName).use {
			// 返回HashMap的大小
			return it.size
		}
	}

	/**
	 * 根据指定的键获取相应的字节数组值
	 *
	 * 此函数用于从HTreeMap中检索与给定键关联的字节数组值如果键不存在，返回null
	 * 使用了getHTreeMap()函数来获取HTreeMap实例，并尝试使用键获取相应的值
	 *
	 * @param key 要检索的键
	 * @return 与键关联的字节数组值，如果键不存在则返回null
	 */
	operator fun get(key: String): ByteArray? {
		getHTreeMap().use {
			return it[key]
		}
	}

	/**
	 * 从指定的Map中根据键获取字节数组值
	 *
	 * 此函数的目的是通过给定的Map名称和键来检索相应的字节数组值它使用了getHTreeMap函数来获取Map对象，
	 * 然后使用键从该Map中检索对应的值
	 *
	 * @param mapName 要检索的Map的名称
	 * @param key 用于从Map中检索值的键
	 * @return 返回与键关联的字节数组值，如果键不存在或者值为空，则返回null
	 */
	operator fun get(mapName: String, key: String): ByteArray? {
		// 使用mapName获取对应的Map对象，并在使用后自动关闭以释放资源
		getHTreeMap(mapName).use {
			// 尝试从Map中通过键获取对应的字节数组值
			return it[key]
		}
	}

	/**
	 * 从哈希树映射中根据键获取字节数组值如果键不存在，则返回默认值
	 *
	 * @param key 要获取值的键
	 * @param default 默认值，当键不存在时返回
	 * @return 对应于键的字节数组值，如果键不存在，则返回默认值
	 */
	operator fun get(key: String, default: ByteArray): ByteArray {
		getHTreeMap().use {
			return it[key] ?: default
		}
	}

	/**
	 * 从指定的Map中根据键获取字节数组值，如果键不存在，则返回默认值。
	 *
	 * @param mapName Map的名称，用于区分不同的Map集合。
	 * @param key 需要获取值的键。
	 * @param default 默认值，当键在Map中不存在时返回。
	 * @return 从Map中获取的字节数组值，如果键不存在，则返回默认值。
	 */
	operator fun get(mapName: String, key: String, default: ByteArray): ByteArray {
		// 获取指定名称的Map，并在其作用域内使用，确保资源的正确释放。
		getHTreeMap(mapName).use {
			// 尝试根据键获取值，如果键不存在，则返回默认值。
			return it[key] ?: default
		}
	}

	/**
	 * 检查哈希树映射中是否包含指定的键
	 *
	 * 此函数提供了一种检查哈希树映射（HTreeMap）中是否存在特定键的方法
	 * 它通过调用getHTreeMap()方法获取映射实例，并使用use方法确保在检查后正确释放资源
	 *
	 * @param key 要检查的键值
	 * @return 如果映射中存在该键，则返回true；否则返回false
	 */
	fun containsKey(key: String): Boolean {
		getHTreeMap().use {
			return it.containsKey(key)
		}
	}

	/**
	 * 检查指定名称的HashMap中是否包含给定的键
	 *
	 * @param mapName 要检查的HashMap的名称
	 * @param key 要检查的键
	 * @return 如果HashMap中包含给定的键，则返回true；否则返回false
	 */
	fun containsKey(mapName: String, key: String): Boolean {
		// 获取指定名称的HashMap，并在使用后自动关闭，以确保资源正确释放
		getHTreeMap(mapName).use {
			// 调用HashMap的containsKey方法来检查键是否存在
			return it.containsKey(key)
		}
	}

	/**
	 * 检查哈希树映射中是否包含指定的字节数组值
	 *
	 * @param value 要检查的字节数组值
	 * @return 如果哈希树映射中包含该值，则返回true；否则返回false
	 */
	fun containsValue(value: ByteArray): Boolean {
		getHTreeMap().use {
			return it.containsValue(value)
		}
	}

	/**
	 * 检查指定的字节数组是否存在于哈希树映射中
	 *
	 * @param mapName 哈希树映射的名称，用于标识和获取特定的哈希树映射
	 * @param value 要检查的字节数组，该数组是哈希树映射中的目标值
	 * @return 如果哈希树映射中存在该值，则返回true；否则返回false
	 */
	fun containsValue(mapName: String, value: ByteArray): Boolean {
		getHTreeMap(mapName).use {
			return it.containsValue(value)
		}
	}

	/**
	 * 检查当前对象是否为空
	 *
	 * @return 如果当前对象为空，则返回true；否则返回false
	 */
	fun isEmpty(): Boolean {
		getHTreeMap().use {
			return it.isEmpty()
		}
	}

	/**
	 * 检查指定名称的HashMap是否为空
	 *
	 * @param mapName HashMap的名称，用于标识和获取特定的HashMap
	 * @return 如果HashMap为空，返回true；否则返回false
	 */
	fun isEmpty(mapName: String): Boolean {
		// 获取指定名称的HashMap，并在使用完毕后自动释放资源
		getHTreeMap(mapName).use {
			// 检查HashMap是否为空
			return it.isEmpty()
		}
	}

	/**
	 * 返回一个包含所有键值对条目的可变集合
	 *
	 * 此函数提供了一个视图，允许访问底层映射的条目集合
	 * 通过返回一个 MutableSet 的实现，允许对映射的条目进行迭代和修改
	 *
	 * @return 一个包含所有键值对条目的可变集合，其中键类型为 String?，值类型为 ByteArray?
	 */
	fun entrySet(): Set<Map.Entry<String?, ByteArray?>> {
		getHTreeMap().use {
			return it.entries
		}
	}

	/**
	 * 返回指定名称的HashMap的entry集合作为MutableSet类型。
	 * 这个函数提供了一个简洁的方式来获取和使用指定HashMap的entry集合，而不需要直接与底层数据结构交互。
	 *
	 * @param mapName HashMap的名称，根据该名称获取对应的HashMap。
	 * @return 返回一个MutableSet，其中包含了指定HashMap的所有entry。
	 */
	fun entrySet(mapName: String): Set<Map.Entry<String?, ByteArray?>> {
		// 获取指定名称的HashMap，并在使用后自动关闭，避免资源泄露。
		getHTreeMap(mapName).use {
			// 返回HashMap的entry集合。
			return it.entries
		}
	}

	/**
	 * 关闭数据库连接
	 * 该方法覆盖了父类的close方法，用于释放资源，避免资源泄露
	 * 在不再需要使用数据库连接时，应当调用此方法
	 */
	override fun close() {
		db.close()
		temp?.deleteOnExit()
	}
}