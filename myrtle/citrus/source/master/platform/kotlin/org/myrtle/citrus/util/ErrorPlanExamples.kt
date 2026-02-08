package org.myrtle.citrus.util

import org.myrtle.atomic.*

data class ErrorPlanExamples(
	val code: i64,
	val description: str,
	val tagDescription: str,
	val codeString: str,
	val lethalLevel: i64,
	val typesOfExceptions: Class<out Throwable>,
	val message: str? = null
) {
	fun codeEquals(code: i64): Boolean {
		return this.code == code
	}

	fun tagDescriptionEquals(tagDescription: str): Boolean {
		return this.tagDescription == tagDescription
	}

	fun copy(message: str?): ErrorPlanExamples {
		return ErrorPlanExamples(code, description, tagDescription, codeString, lethalLevel, typesOfExceptions,message)
	}
}