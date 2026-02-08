package org.myrtle.citrus.io

import java.io.Serial
import java.io.Serializable
import java.util.*

data class CByteArray(
	val compressedData: ByteArray,
	val sourceDataSize: Int,
	val archiveAlgorithm: ArchiveAlgorithm,
	): Serializable{
	companion object{
		@Serial
		private const val serialVersionUID = 6551532269474955301L
	}

	override fun equals(other: Any?): Boolean {
		if (other !is CByteArray){
			return false
		}
		return compressedData.contentEquals(other.compressedData) && sourceDataSize == other.sourceDataSize && archiveAlgorithm == other.archiveAlgorithm
	}

	override fun toString(): String {
		return "CByteArray(compressedData=${Base64.getEncoder().encodeToString(compressedData)}, sourceDataSize=$sourceDataSize, archiveAlgorithm=$archiveAlgorithm)"
	}

	override fun hashCode(): Int {
		var result = sourceDataSize
		result = 31 * result + compressedData.contentHashCode()
		result = 31 * result + archiveAlgorithm.hashCode()
		return result
	}
}