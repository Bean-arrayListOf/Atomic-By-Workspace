package org.myrtle.citrus.lang

/**
 * 生成字符表
 *
 * @author CitrusCat
 * @since 2024/12/26
 */
//@Deprecated("开发中,极其不稳定")
object TableCharacters {

	/**
	 * 缓存池
	 */
	@JvmStatic
	private var cache = arrayListOf<Char>()

	@JvmStatic
	fun TableCharacters.build(block: (TableCharacters) -> TableCharacters): Collection<Char> {
		return block(this).random()
	}

	/**
	 * 小写英文字母
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun englishLowerCase(): TableCharacters {
		cache.addAll(
			arrayOf(
				'a',
				'b',
				'c',
				'd',
				'e',
				'f',
				'g',
				'h',
				'i',
				'j',
				'k',
				'l',
				'm',
				'n',
				'o',
				'p',
				'q',
				'r',
				's',
				't',
				'u',
				'v',
				'w',
				'x',
				'y',
				'z'
			)
		)
		return this
	}

	/**
	 * 大写英文字母
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun englishUpperCase(): TableCharacters {
		cache.addAll(
			arrayOf(
				'A',
				'B',
				'C',
				'D',
				'E',
				'F',
				'G',
				'H',
				'I',
				'J',
				'K',
				'L',
				'M',
				'N',
				'O',
				'P',
				'Q',
				'R',
				'S',
				'T',
				'U',
				'V',
				'W',
				'X',
				'Y',
				'Z'
			)
		)
		return this
	}

	/**
	 * 数字
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun numeric(): TableCharacters {
		cache.addAll(
			arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')

		)
		return this
	}

	/**
	 * 大小写英文
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun allEnglish(): TableCharacters {
		//cache.addAll(EnglishLowerCaseCharactersTable)
		englishUpperCase()
		//cache.addAll(EnglishUpperCaseCharactersTable)
		englishLowerCase()
		return this
	}

	/**
	 * 半角全角符号
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun allSymbol(): TableCharacters {
		//cache.addAll(HalfWidthSymbolCharactersTable)
		halfWidthSymbol()
		//cache.addAll(FullWidthSymbolCharactersTable)
		return this
	}

	/**
	 * 半角字符
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun halfWidthSymbol(): TableCharacters {
		cache.addAll(
			arrayOf(
				'`',
				'~',
				'!',
				'@',
				'#',
				'$',
				'%',
				'^',
				'&',
				'*',
				'(',
				')',
				'-',
				'_',
				'=',
				'+',
				'{',
				'}',
				'[',
				']',
				'\\',
				'|',
				';',
				':',
				'"',
				'\'',
				'<',
				'>',
				',',
				'.',
				'/',
				'?'
			)
		)
		return this
	}


	/**
	 * 空格
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun space(): TableCharacters {
		cache.addAll(
			arrayOf(' ')
		)
		return this
	}

	/**
	 * 中文字符
	 * @return 返回添加此表后的此类
	 */
//	@JvmStatic
//	fun chinese(): TableCharacters {
//		cache.addAll(
//			arrayListOf()
//		)
//		return this
//	}

	/**
	 * 所有字符
	 * @return 返回添加此表后的此类
	 */
	@JvmStatic
	fun allCharacters(): TableCharacters {
		//cache.addAll(EnglishUpperCaseCharactersTable)
		englishUpperCase()
		//cache.addAll(EnglishLowerCaseCharactersTable)
		englishLowerCase()
		//cache.addAll(ChineseCharactersTable)
		//chinese()
		//cache.addAll(NumericCharactersTable)
		numeric()
		//cache.addAll(FullWidthSymbolCharactersTable)
		//cache.addAll(HalfWidthSymbolCharactersTable)
		halfWidthSymbol()
		//cache.addAll(SpaceCharactersTable)
		space()
		return this
	}

	/**
	 * 构建为集合
	 * @return 返回集合
	 */
	@JvmStatic
	fun builder(): Collection<Char> {
		return cache
	}

	/**
	 * 构建为随机集合
	 * @return 返回集合
	 */
	@JvmStatic
	fun random(): Collection<Char> {
		cache.shuffle()
		return cache
	}
}