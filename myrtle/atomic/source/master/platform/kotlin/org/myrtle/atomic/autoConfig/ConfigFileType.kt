package org.myrtle.atomic.autoConfig

import java.io.Serializable

enum class ConfigFileType : Serializable {
	ENV,
	Properties,
	JSON,
	XML,
	INI,
	YAML,
	PropertyList,
	XMLPropertyList,
	XMLProperties;

	companion object {
		private const val serialVersionUID = 7362548190374628593L
	}
}