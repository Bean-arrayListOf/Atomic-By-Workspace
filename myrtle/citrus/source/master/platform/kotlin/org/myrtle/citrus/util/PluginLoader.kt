package org.myrtle.citrus.util

import org.pf4j.DefaultPluginManager
import java.nio.file.Paths

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
object PluginLoader : AutoCloseable {

	val pluginManager: DefaultPluginManager = DefaultPluginManager(Paths.get(System.getProperty("citrus.plug.dir")))

	init {
		pluginManager.loadPlugins()
		pluginManager.startPlugins()
	}

	@JvmStatic
	fun <T> getPlugin(pluginId:String): T {
		return pluginManager.getPlugin(pluginId).plugin as T
	}

	override fun close() {
		pluginManager.stopPlugins()
		pluginManager.unloadPlugins()
	}


}