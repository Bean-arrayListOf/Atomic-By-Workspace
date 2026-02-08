package org.myrtle.atomic

import com.google.gson.JsonObject
import java.io.Serial
import java.io.Serializable

data class RequestResult(
	val statusCode: Int,
	val result: String
): Serializable{
	companion object{
		@Serial
		private const val serialVersionUID = 5232395497811765139
	}
	fun resultJson(): JsonObject{
		return JsonKit.gson.fromJson(result, JsonObject::class.java)
	}
}