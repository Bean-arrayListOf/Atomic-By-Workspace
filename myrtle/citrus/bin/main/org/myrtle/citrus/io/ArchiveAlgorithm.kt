package org.myrtle.citrus.io

enum class ArchiveAlgorithm {
	GZip,
	BZip2,
	Deflate,
	BlockLZ4,
	FramedLZ4,
	LZMA,
	Pack200,
	Snappy,
	FramedSnappy,
	XZ,
	Zstd
}