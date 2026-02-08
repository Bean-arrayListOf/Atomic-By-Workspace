package org.myrtle.citrus.io

import org.myrtle.atomic.BAIStream
import org.myrtle.atomic.BAOStream
import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream
import org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream
import org.apache.commons.compress.compressors.snappy.SnappyCompressorOutputStream
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream

object Archive {
	@JvmStatic
	fun compressBytes(algorithm: ArchiveAlgorithm,data: ByteArray): CByteArray{
		return CByteArray(
			compressedData = BAOStream().use { baos ->
				when (algorithm) {
					ArchiveAlgorithm.GZip -> GzipCompressorOutputStream(baos)
					ArchiveAlgorithm.BZip2 -> BZip2CompressorOutputStream(baos)
					ArchiveAlgorithm.Deflate -> DeflateCompressorOutputStream(baos)
					ArchiveAlgorithm.FramedLZ4 -> FramedLZ4CompressorOutputStream(baos)
					ArchiveAlgorithm.BlockLZ4 -> BlockLZ4CompressorOutputStream(baos)
					ArchiveAlgorithm.LZMA -> LZMACompressorOutputStream(baos)
					ArchiveAlgorithm.Pack200 -> Pack200CompressorOutputStream(baos)
					ArchiveAlgorithm.Snappy -> SnappyCompressorOutputStream(baos,data.size.toLong())
					ArchiveAlgorithm.FramedSnappy -> FramedLZ4CompressorOutputStream(baos)
					ArchiveAlgorithm.XZ -> XZCompressorOutputStream(baos)
					ArchiveAlgorithm.Zstd -> ZstdCompressorOutputStream(baos)
				}.use { cos ->
					cos.write(data)
					cos.finish()
				}
				baos.toByteArray()
			},
			sourceDataSize = data.size,
			archiveAlgorithm = algorithm
		)
	}

	@JvmStatic
	fun decompressBytes(algorithm: ArchiveAlgorithm,cbytes: ByteArray,sourceDataSize: Int): ByteArray{
		return BAIStream(cbytes).use { bais ->
			when(algorithm){
				ArchiveAlgorithm.GZip -> GzipCompressorInputStream(bais)
				ArchiveAlgorithm.BZip2 -> BrotliCompressorInputStream(bais)
				ArchiveAlgorithm.Deflate -> DeflateCompressorInputStream(bais)
				ArchiveAlgorithm.FramedLZ4 -> FramedLZ4CompressorInputStream(bais)
				ArchiveAlgorithm.BlockLZ4 -> BlockLZ4CompressorInputStream(bais)
				ArchiveAlgorithm.LZMA -> LZMACompressorInputStream(bais)
				ArchiveAlgorithm.Pack200 -> Pack200CompressorInputStream(bais)
				ArchiveAlgorithm.Snappy -> SnappyCompressorInputStream(bais)
				ArchiveAlgorithm.FramedSnappy -> FramedLZ4CompressorInputStream(bais)
				ArchiveAlgorithm.XZ -> XZCompressorInputStream(bais)
				ArchiveAlgorithm.Zstd -> ZstdCompressorInputStream(bais)
			}.use { cis ->
				cis.readNBytes(sourceDataSize)
			}
		}
	}

	@JvmStatic
	fun decompressBytes(cb: CByteArray): ByteArray{
		return decompressBytes(cb.archiveAlgorithm,cb.compressedData,cb.sourceDataSize)
	}
}