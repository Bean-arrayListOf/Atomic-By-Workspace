package org.myrtle.citrus.io

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream
import org.apache.hadoop.io.compress.bzip2.CBZip2InputStream
import org.apache.hadoop.io.compress.bzip2.CBZip2OutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class CPackageKit<Myrtle> {
	var cct: CPackageCompressionType = CPackageCompressionType.BZip2

	constructor() {}
	constructor(cct: CPackageCompressionType) {
		this.cct = cct
	}

	fun setType(cct: CPackageCompressionType) {
		this.cct = cct
	}

	fun compression(bytes: ByteArray): ByteArray {
		ByteArrayOutputStream().use { baos ->
			when (cct) {
				CPackageCompressionType.GZip -> {
					GZIPOutputStream(baos).use { gzip ->
						gzip.write(bytes)
						gzip.finish()
					}
					baos.flush()
					return baos.toByteArray()
				}

				CPackageCompressionType.Zip -> {
					ZipOutputStream(baos).use { zip ->
						ZipEntry("zip").let { entry ->
							entry.size = bytes.size.toLong()
							zip.putNextEntry(entry)
							zip.write(bytes)
							zip.flush()
							zip.closeEntry()
							zip.close()
						}
					}
					baos.flush()
					return baos.toByteArray()
				}

				CPackageCompressionType.CBZip2 -> {
					CBZip2OutputStream(baos).use { bzip2 ->
						bzip2.write(bytes)
						bzip2.flush()
					}
					baos.flush()
					return baos.toByteArray()
				}

				CPackageCompressionType.BZip2 -> {
					BZip2CompressorOutputStream(baos).use { bz2cos ->
						bz2cos.write(bytes)
						bz2cos.finish()
					}
					baos.flush()
					return baos.toByteArray()
				}

				CPackageCompressionType.Zlib -> {
					val deflater = Deflater()
					deflater.setInput(bytes)
					val baos = ByteArrayOutputStream(bytes.size)
					val dos = DeflaterOutputStream(baos, deflater)
					dos.write(bytes, 0, baos.size())
					dos.close()
					baos.flush()
					return baos.toByteArray()
				}

				CPackageCompressionType.Nil -> return bytes
			}
		}

	}

	fun decompress(byte: ByteArray, compressedDataLength: Int): ByteArray {
		val bais = ByteArrayInputStream(byte)
		val buf = ByteArray(compressedDataLength)
		var num = -1
		val baos = ByteArrayOutputStream()
		when (cct) {
			CPackageCompressionType.GZip -> {
				val gzip = GZIPInputStream(bais)
				while ((gzip.read(buf, 0, buf.size).also { num = it }) != -1) {
					baos.write(buf, 0, num)
				}
				val temp = baos.toByteArray()
				baos.flush()
				baos.close()
				gzip.close()
				bais.close()
				return temp
			}

			CPackageCompressionType.Zip -> {
				val zip = ZipInputStream(bais)
				while (zip.nextEntry != null) {
					while ((zip.read(buf, 0, buf.size).also { num = it }) != -1) {
						baos.write(buf, 0, num)
					}
				}
				baos.flush()
				val temp = baos.toByteArray()
				baos.close()
				zip.close()
				bais.close()
				return temp
			}

			CPackageCompressionType.CBZip2 -> {
				val bzip2 = CBZip2InputStream(bais)
				while ((bzip2.read(buf, 0, buf.size).also { num = it }) != -1) {
					baos.write(buf, 0, num)
				}
				baos.flush()
				val temp = baos.toByteArray()
				baos.close()
				bzip2.close()
				bais.close()
				return temp
			}

			CPackageCompressionType.BZip2 -> {
				val bz2is = BZip2CompressorInputStream(bais)
				while ((bz2is.read(buf, 0, buf.size).also { num = it }) != -1) {
					baos.write(buf, 0, num)
				}
				baos.flush()
				val b = baos.toByteArray()
				baos.close()
				bz2is.close()
				bais.close()
				//log.info("[ 解压 ]: 解压[前:${input.size}] [后:${b.size}]")
				return b
			}

			CPackageCompressionType.Zlib -> return byte

			CPackageCompressionType.Nil -> return byte
		}
	}
}