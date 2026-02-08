package org.myrtle.atomic

import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.impl.StandardFileSystemManager
import org.apache.commons.vfs2.provider.ram.RamFileProvider

object VirtualFileSystem {
	private var manager: StandardFileSystemManager? = null

	fun manager(): StandardFileSystemManager{
		if(manager == null){
			this.manager = StandardFileSystemManager()
			this.manager!!.init()

		}
		return this.manager!!
	}

	fun getRam(path: String): FileObject{
		val manager = manager()
		val schemeName = "${SystemConfig.of.getString("org.myrtle.canary.VirtualFileSystem.getRam.schemeName","RamDisk")}.${this::class.simpleName}"
		if (!manager.schemes.contains(schemeName)){
			manager.addProvider(schemeName, RamFileProvider())
		}
		return manager.resolveFile("${schemeName}:///${path}")
	}

}