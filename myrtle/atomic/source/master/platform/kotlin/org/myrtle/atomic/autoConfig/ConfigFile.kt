package org.myrtle.atomic.autoConfig

import com.google.gson.Gson
import java.io.Serial
import java.io.Serializable

data class ConfigFile(
	val file: String,
	val classPathAt: Boolean,
	val fileType: ConfigFileType
): Serializable{
	companion object {
		@Serial
		private const val serialVersionUID = 5839204716253849201L
	}

	override fun toString(): String {
		return Gson().toJson(this)
	}
}