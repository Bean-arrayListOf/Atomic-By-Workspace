package org.myrtle.citrus.db.MapDB.Pojo

import java.io.Serializable

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
data class sqlite_master (
	var type: String?,
	var name: String?,
	var tbl_name: String?,
	var rootpage: Int?,
	var sql: String?
) : Serializable {
}