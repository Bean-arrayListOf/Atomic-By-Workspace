package org.myrtle.atomic

import java.io.PrintStream
import java.util.*

class Console(
	private val sout: PrintStream,
	private val eout: PrintStream,
	private val sin: Scanner
) {
	companion object{
		@JvmStatic
		val INSTANCE = Console(System.out, System.err, Scanner(System.`in`))

		@JvmStatic
		fun Any?.println() {
			INSTANCE.sout.println(this)
		}

		@JvmStatic
		fun Any?.print() {
			INSTANCE.sout.print(this)
		}
		fun error(message: Any?) {
			INSTANCE.eout.println(message)
		}
		fun readLine(): String? {
			return INSTANCE.sin.nextLine()
		}
		fun readInt(): Int {
			return INSTANCE.sin.nextInt()
		}
		fun readLong(): Long {
			return INSTANCE.sin.nextLong()
		}
		fun readDouble(): Double {
			return INSTANCE.sin.nextDouble()
		}
		fun readBoolean(): Boolean {
			return INSTANCE.sin.nextBoolean()
		}
		fun readChar(): Char {
			return INSTANCE.sin.next().single()
		}
		fun readChars(array: CharArray) {
			INSTANCE.sin.next().toCharArray(array)
		}
		fun read(): String {
			return INSTANCE.sin.next()
		}
		fun write(message: Any?) {
			INSTANCE.sout.print(message)
		}
		fun writeLine(message: Any?) {
			INSTANCE.sout.println(message)
		}
		fun writeLine() {
			INSTANCE.sout.println()
		}
		fun writeError(message: Any?) {
			INSTANCE.eout.print(message)
		}
		fun writeErrorLine(message: Any?) {
			INSTANCE.eout.println(message)
		}
		fun writeErrorLine() {
			INSTANCE.eout.println()
		}
		fun flush() {
			INSTANCE.sout.flush()
			INSTANCE.eout.flush()
		}
	}
}