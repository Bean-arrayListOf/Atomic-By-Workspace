package org.myrtle.citrus.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import org.apache.commons.lang3.StringUtils
import java.awt.image.BufferedImage
import java.io.File
import java.io.OutputStream
import javax.imageio.ImageIO
import javax.swing.filechooser.FileSystemView

/**
 * 二维码工具
 *
 * @author:debug (SteadyJack)
 *
 * @since 2020/11/16 22:38
 */
object QRCodeUtil {

	/**
	 * CODE_WIDTH：二维码宽度，单位像素
	 */
	private const val CODE_WIDTH = 400

	/**
	 * CODE_HEIGHT：二维码高度，单位像素
	 */
	private const val CODE_HEIGHT = 400

	/**
	 * FRONT_COLOR：二维码前景色，0x000000 表示黑色
	 */
	private const val FRONT_COLOR = 0x000000

	/**
	 * BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
	 * 演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
	 */
	private const val BACKGROUND_COLOR = 0xFFFFFF

	/**
	 * 从文件加载的数据类
	 * 该类封装了从文件中加载数据所需的必要信息
	 */
	class FromFileBean() {
	    /**
	     * 文件内容
	     * @return 文件内容字符串如果内容未设置，则抛出NullPointerException
	     */
	    var content: String?
	        get() = field ?: throw NullPointerException("content is null")

	    /**
	     * 保存代码图片文件的目录
	     */
	    var codeImgFileSaveDir: File?

	    /**
	     * 文件名
	     */
	    var fileName: String?

	    /**
	     * 初始化属性值为null
	     * 在类的实例化时，将所有属性初始化为null，以明确它们的初始状态
	     */
	    init {
	        content = null
	        codeImgFileSaveDir = null
	        fileName = null
	    }
	}

	/**
	 * 从文件生成代码和图片的高阶函数
	 *
	 * 此函数提供一个高阶编程接口，使得调用者可以通过Lambda表达式自定义如何读取和处理文件数据
	 * 它内部创建了一个FromFileBean实例，并将该实例作为参数传递给调用者提供的Lambda表达式
	 * 在Lambda表达式内部，调用者可以设置FromFileBean的属性，特别是content和codeImgFileSaveDir
	 * 最后，使用这些属性调用具体的fromFile函数，进行代码和图片的生成
	 *
	 * @param block 一个Lambda表达式，接收一个FromFileBean实例作为参数
	 */
	fun fromFile(block: (FromFileBean) -> Unit) {
	    // 创建FromFileBean实例
	    val bean = FromFileBean()
	    // 调用传入的Lambda表达式，并将FromFileBean实例作为参数传递给它
	    block(bean)
	    // 根据FromFileBean实例的属性值，调用具体的fromFile函数进行处理
	    fromFile(bean.content!!, bean.codeImgFileSaveDir, bean.fileName)
	}

	/**
	 * 从文本内容生成二维码图片并保存到指定目录
	 *
	 * @param content 待生成二维码的文本内容
	 * @param codeImgFileSaveDir 二维码图片保存的目录，如果未指定或指定为文件，则默认保存到桌面
	 * @param fileName 保存的图片文件名，如果为空或格式不正确，则不进行保存
	 */
	fun fromFile(content: String, codeImgFileSaveDir: File?, fileName: String?) {
	    var content = content
	    var codeImgFileSaveDir = codeImgFileSaveDir
	    try {
	        // 检查内容和文件名是否为空或无效
	        if (StringUtils.isBlank(content) || StringUtils.isBlank(fileName)) {
	            return
	        }
	        // 移除内容字符串两端的空白字符
	        content = content.trim { it <= ' ' }
	        // 检查并设置二维码图片保存的目录
	        if (codeImgFileSaveDir == null || codeImgFileSaveDir.isFile) {
	            // 如果未指定目录或指定的是文件，则默认保存到桌面
	            codeImgFileSaveDir = FileSystemView.getFileSystemView().homeDirectory
	        }
	        // 如果目录不存在，则创建目录
	        if (!codeImgFileSaveDir!!.exists()) {
	            codeImgFileSaveDir.mkdirs()
	        }

	        // 核心代码：生成二维码图片
	        val bufferedImage = getBufferedImage(content)

	        // 创建二维码图片文件
	        val codeImgFile = File(codeImgFileSaveDir, fileName)
	        // 将生成的二维码图片保存到指定文件
	        ImageIO.write(bufferedImage, "png", codeImgFile)
	    } catch (e: Exception) {
	        e.printStackTrace()
	    }
	}

	/**
	 * 从流处理相关的数据封装类
	 * 该类主要用于封装需要从输入流中读取的内容以及处理该内容的输出流
	 */
	class FromStreamBean() {
	    /**
	     * 封装的字符串内容
	     * 该内容在使用前不应为null，如果为null则表示内容未设置
	     */
	    var content: String?
	        get() = field ?: throw NullPointerException("content Is Null")

	    /**
	     * 用于输出处理结果的输出流
	     * 输出流在使用前不应为null，如果为null则表示未设置有效的输出流
	     */
	    var outputStream: OutputStream?
	        get() = field ?: throw NullPointerException("outputStream Is Null")

	    /**
	     * 初始化FromStreamBean实例时，将content和outputStream初始化为null
	     * 这样可以在实例化对象时，确保所有属性都有一个明确的初始状态
	     */
	    init {
	        this.content = null
	        outputStream = null
	    }
	}

	/**
	 * 从输入流中读取数据并进行处理
	 *
	 * 该函数提供一个回调机制，允许外部在处理数据前对FromStreamBean对象进行自定义操作
	 * 主要用于处理从外部输入流传入的数据，通过指定的处理逻辑进行加工或转换
	 *
	 * @param block 一个Lambda表达式，接收一个FromStreamBean类型的参数，用于在数据处理前进行自定义操作
	 */
	fun fromStream(block: (FromStreamBean) -> Unit) {
	    // 创建一个FromStreamBean实例，用于承载输入流中的数据及处理后的输出流
	    val bean = FromStreamBean()
	    // 执行传入的Lambda表达式，对FromStreamBean实例进行自定义操作
	    block(bean)
	    // 调用fromStream方法，传入处理后的FromStreamBean实例的内容和输出流，进行下一步的数据处理
	    fromStream(bean.content!!, bean.outputStream!!)
	}

	/**
	 * 根据提供的内容字符串生成二维码，并将其写入指定的输出流中
	 *
	 * 此函数首先检查内容字符串是否为空白，如果为空白，则不生成二维码，直接返回
	 * 如果内容字符串非空白，将其Trim后，使用核心代码生成二维码图像
	 * 最后，将生成的二维码图像写入指定的输出流中
	 *
	 * @param content 要生成二维码的内容字符串
	 * @param outputStream 要写入二维码图像的输出流
	 */
	fun fromStream(content: String, outputStream: OutputStream) {
	    var content = content
	    try {
	        // 检查内容字符串是否为空白，如果为空白，则不生成二维码，直接返回
	        if (StringUtils.isBlank(content)) {
	            return
	        }
	        // 去除内容字符串两端的空白字符
	        content = content.trim { it <= ' ' }
	        // 核心代码-生成二维码
	        val bufferedImage = getBufferedImage(content)
	        // 将生成的二维码图像写入指定的输出流中
	        ImageIO.write(bufferedImage, "png", outputStream)
	    } catch (e: Exception) {
	        // 异常处理：打印异常堆栈跟踪
	        e.printStackTrace()
	    }
	}

	/**
	 * 根据内容生成对应的 BufferedImage 二维码图像
	 * @param content 待生成二维码的字符串内容
	 * @return 生成的 BufferedImage 图像
	 * @throws WriterException 当内容编码或图像生成过程中发生错误时抛出
	 */
	@Throws(WriterException::class)
	private fun getBufferedImage(content: String): BufferedImage {
	    // 创建编码提示的哈希映射，用于设置二维码的编码选项
	    val hints = hashMapOf<EncodeHintType, Any>()

	    // 设置字符编码类型为 UTF-8，确保内容的正确编码
	    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

	    // 设置误差校正等级为 M，提高二维码的容错能力
	    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.M

	    // 设置二维码边距为 1 像素，使二维码更加紧凑
	    hints[EncodeHintType.MARGIN] = 1

	    // 创建 MultiFormatWriter 对象，用于二维码的生成
	    val multiFormatWriter = MultiFormatWriter()
	    // 使用 MultiFormatWriter 编码内容为二维码的矩阵形式
	    val bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints)
	    // 创建 BufferedImage 对象，用于绘制二维码图像
	    val bufferedImage = BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR)
	    // 遍历二维码矩阵，绘制二维码图像
	    for (x in 0 until CODE_WIDTH) {
	        for (y in 0 until CODE_HEIGHT) {
	            // 根据矩阵中每个点的状态设置图像颜色
	            bufferedImage.setRGB(x, y, if (bitMatrix[x, y]) FRONT_COLOR else BACKGROUND_COLOR)
	        }
	    }
	    // 返回绘制好的二维码图像
	    return bufferedImage
	}
}