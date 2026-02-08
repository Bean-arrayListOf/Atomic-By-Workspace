package org.myrtle.citrus.JNA

import com.sun.jna.Library
import kotlin.Int as int
import kotlin.String as string

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
interface ANSIC : Library {

	object locale{
		const val LC_ALL = 1
		const val LC_COLLATE = 2
		const val LC_CTYPE = 3
		const val LC_MONETARY = 4
		const val LC_NUMERIC = 5
		const val LC_TIME = 6
		const val locale_t = 7
	}

	fun system(command: string): int
	fun isalnum(c: int): int
	fun isalpha(c: int): int
	fun iscntrl(c: int): int
	fun isdigit(c: int): int
	fun isgraph(c: int): int
	fun islower(c: int): int
	fun isprint(c: int): int
	fun ispunct(c: int): int
	fun isspace(c: int): int
	fun isupper(c: int): int
	fun isxdigit(c: int): int
	fun tolower(c: int): int
	fun toupper(c: int): int
	fun setlocale(category: int, locale: string): string
}