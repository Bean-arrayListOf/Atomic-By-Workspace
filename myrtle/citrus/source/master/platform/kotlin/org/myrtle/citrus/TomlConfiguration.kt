package org.myrtle.citrus

import org.apache.commons.configuration2.BaseHierarchicalConfiguration
import org.apache.commons.configuration2.FileBasedConfiguration
import org.apache.commons.configuration2.io.InputStreamSupport
import java.io.InputStream
import java.io.Reader
import java.io.Writer

class TomlConfiguration : BaseHierarchicalConfiguration(),FileBasedConfiguration, InputStreamSupport {
	override fun read(`in`: Reader?) {
		TODO("Not yet implemented")
	}

	override fun write(out: Writer?) {
		TODO("Not yet implemented")
	}

	override fun read(`in`: InputStream?) {
		TODO("Not yet implemented")
	}
}