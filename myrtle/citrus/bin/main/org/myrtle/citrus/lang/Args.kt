package org.myrtle.citrus.lang

import java.util.function.Consumer

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Args : List<String> {

	companion object {
		@JvmStatic
		private fun getFullArgs(): ArrayList<String> {
			val arg = arrayListOf<String>()
			arg.addAll(System.getProperty("sun.java.command").split(" "))
			return arg
		}

		@JvmStatic
		fun nowAutoGet(): Args {
			val arg = getFullArgs()
			arg.removeAt(0)
			return Args(arg)
		}

		@JvmStatic
		fun nowAutoFullGet(): Args {
			return Args(getFullArgs())
		}

		@JvmStatic
		fun now(args: List<String>): Args {
			return Args(args)
		}

		@JvmStatic
		fun now(vararg args: String): Args {
			return Args(*args)
		}
	}

	private val args: List<String>

	constructor(args: List<String>) {
		this.args = args
	}

	constructor(vararg args: String) {
		this.args = args.toList()
	}

	override val size: Int
		get() = args.size

	override fun get(index: Int) = args[index]

	override fun isEmpty() = args.isEmpty()

	override fun iterator() = args.iterator()

	override fun listIterator() = args.listIterator()

	override fun listIterator(index: Int) = args.listIterator(index)

	override fun subList(fromIndex: Int, toIndex: Int) = args.subList(fromIndex, toIndex)

	override fun lastIndexOf(element: String) = args.lastIndexOf(element)

	override fun indexOf(element: String) = args.indexOf(element)

	override fun containsAll(elements: Collection<String>) = args.containsAll(elements)

	override fun contains(element: String) = args.contains(element)

	override fun forEach(action: Consumer<in String>?) {
		args.forEach(action)
	}

	override fun parallelStream() = args.parallelStream()

	override fun spliterator() = args.spliterator()

	override fun stream() = args.stream()

	override fun toString() = args.toString()

	override fun equals(other: Any?) = args == other

	override fun hashCode() = args.hashCode()
}