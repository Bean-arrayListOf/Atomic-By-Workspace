package org.myrtle.atomic

import com.google.gson.GsonBuilder

object JsonKit {
	val gson = GsonBuilder().setPrettyPrinting().create()
}