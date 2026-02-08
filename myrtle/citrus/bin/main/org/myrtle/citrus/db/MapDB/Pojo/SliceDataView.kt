package org.myrtle.citrus.db.MapDB.Pojo

import org.myrtle.citrus.PRTS.Companion.toJsonString
import java.nio.charset.Charset

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class SliceDataView(
		private val index: Int,
		private val key: String,
		private val data: ByteArray,
		private val time: Long
	) {

		fun getIndex():Int{
			return index
		}

		fun getKey():String{
			return key
		}

		fun getData():ByteArray{
			return data
		}

		fun getDataFormat(charset: Charset):String{
			return String(data,charset)
		}

		fun getTime():Long{
			return time
		}

		fun toView():String{
			return this.toString()
		}

		override fun toString(): String {
			return "${this.javaClass.typeName}=${this.toJsonString()}"
		}

	}