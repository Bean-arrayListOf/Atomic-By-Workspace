package org.myrtle.citrus.Error

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Oops : Exception {
	private val oops = "   ___                  _ \n" +
			"  / _ \\  ___  _ __  ___| |\n" +
			" | | | |/ _ \\| '_ \\/ __| |\n" +
			" | |_| | (_) | |_) \\__ \\_|\n" +
			"  \\___/ \\___/| .__/|___(_)\n" +
			"             |_|          "

	constructor():super("Oops!"){
		System.err.println(oops)
	}

	constructor(message: String) : super("Oops! [$message]"){
		System.err.println(oops)
	}
	constructor(message: String, cause: Throwable) : super("Oops! [$message]", cause){
		System.err.println(oops)
	}
	constructor(cause: Throwable) : super("Oops!",cause){
		System.err.println(oops)
	}
}