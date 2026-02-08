package org.myrtle.citrus.io

import org.myrtle.citrus.util.Citrus
import org.myrtle.citrus.util.Task
import org.slf4j.LoggerFactory
import java.io.Flushable
import java.io.OutputStream

/**
 * @author CitrusCat
 * @since 2024/12/26
 *
 * 【=◈︿◈=】
 */
class StdOutTask(
	private val taskName: String,
	@get:Synchronized
	private val cache: ArrayList<ByteArray>,
	@get:Synchronized
	private val outputStream: OutputStream,
	private val auto: Boolean
) : Task<ByteArray>(), Flushable {

	companion object {
		@JvmStatic
		fun now(taskName: String, cache: ArrayList<ByteArray>, outputStream: OutputStream, auto: Boolean): StdOutTask {
			return StdOutTask(taskName, cache, outputStream, auto)
		}
	}

	private val log = LoggerFactory.getLogger(this::class.java)
	private val color_egg_: Boolean
	private val color_egg = ByteArray(15)

	init {
		log.info("Start: Task: $taskName")
		printError = false
		loopBeforeNullDetection = false
		loopAfterContinue = false
		loopBeforeContinue = false
		errorDetection = false
		loopAfterClose = false
		loopBeforeSleep = -1
		loopAfterSleep = -1
		loopSleep = -1

		color_egg[0] = 'c'.code.toByte()
		color_egg[1] = 'a'.code.toByte()
		color_egg[2] = 't'.code.toByte()
		color_egg[3] = '='.code.toByte()
		color_egg[4] = '$'.code.toByte()
		color_egg[5] = '{'.code.toByte()
		color_egg[6] = 11
		color_egg[7] = 45
		color_egg[8] = 14
		color_egg[9] = '}'.code.toByte()
		color_egg[10] = '$'.code.toByte()
		color_egg[11] = '='.code.toByte()
		color_egg[12] = 'e'.code.toByte()
		color_egg[13] = 'n'.code.toByte()
		color_egg[14] = 'd'.code.toByte()

		color_egg_ = Citrus.jvmArgs.getBoolean("citrus.egg", false)
	}

	override fun loopBefore(): ByteArray? {
		synchronized(cache) {
			try {
				if (cache.isEmpty()) {
					return null
				}
				return cache.removeAt(0)
			} catch (_: NullPointerException) {
				return null
			}
		}
	}

	override fun loop(arg: ByteArray?) {
		synchronized(outputStream) {
			if (color_egg_) {
				if (arg?.size == 15) {
					if (arg.contentEquals(color_egg)) {
						outputStream.write("【=◈︿◈=】\n".toByteArray())
					} else {
						outputStream.write("Command: ".toByteArray())
					}
				} else {
					outputStream.write(arg ?: return)
				}
				return
			}
			outputStream.write(arg ?: return)
		}
	}

	override fun loopAfter(elapsedTime: Long): Boolean {
		synchronized(outputStream) {
			if (auto) {
				flush()
			}
			return false
		}
	}

	override fun flush() {
		synchronized(outputStream) {
			outputStream.flush()
		}
	}

	override fun close() {
		log.info("Close: Task: $taskName")
		this.flush()
	}

}