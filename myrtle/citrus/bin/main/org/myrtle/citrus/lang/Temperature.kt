
/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus.lang

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
class Temperature {
	@Expose
	@SerializedName("Type")
	private var type: Type

	@Expose
	@SerializedName("Temperature")
	private val temperature: Float

	enum class Type(val MIN: Float, val MAX: Float, val format: String) {
		CELSIUS(-273.15f, Float.MAX_VALUE, "°C"), FAHRENHEIT(-459.67f, Float.MAX_VALUE, "°F");
	}

	constructor(type: Type, temperature: Float) {
		this.type = type
		this.temperature = temperature
	}

	companion object {
		@JvmStatic
		fun from(type: Type, temperature: Float): Temperature {
			return Temperature(type, temperature)
		}

		@JvmStatic
		fun formCelsius(temperature: Float): Temperature {
			return Temperature(Type.CELSIUS, temperature)
		}

		@JvmStatic
		fun formFahrenheit(temperature: Float): Temperature {
			return Temperature(Type.FAHRENHEIT, temperature)
		}

		@JvmStatic
		fun now(type: Type, temperature: Float): Temperature {
			return Temperature(type, temperature)
		}
	}

	fun add(temperature: Float): Temperature {
		val temp = this.temperature + temperature
		if (temp > this.type.MAX) {
			throw NumberFormatException("值超出了最大值[${temp}]")
		}
		return Temperature.from(type, temp)
	}

	fun sub(temperature: Float): Temperature {
		val temp = this.temperature - temperature
		if (temp < this.type.MIN) {
			throw NumberFormatException("值超出了最小值[${temp}]")
		}
		return Temperature.from(type, temp)
	}

	fun mul(temperature: Float): Temperature {
		val temp = this.temperature * temperature
		if (temp > this.type.MAX) {
			throw NumberFormatException("值超出了最大值[${temp}]")
		}
		return Temperature.from(type, temp)
	}

	fun div(temperature: Float): Temperature {
		val temp = this.temperature / temperature
		if (temp < this.type.MIN) {
			throw NumberFormatException("值超出了最小值[${temp}]")
		}
		return Temperature.from(type, temp)
	}

	fun toFahrenheit(): Temperature {
		return Temperature(Type.FAHRENHEIT, ((9.0f / 5.0f) * this.temperature + 32f))
	}

	fun toCelsius(): Temperature {
		return Temperature(Type.CELSIUS, ((5.0f / 9.0f) * (this.temperature - 32f)))
	}

	fun getInt(): Int {
		return this.temperature.toInt()
	}

	fun getString(): String {
		return this.toString()
	}

	fun toDouble(): Double {
		return this.temperature.toDouble()
	}

	fun toFloat(): Float {
		return this.temperature
	}

	fun toShoat(): Short {
		return this.temperature.toInt().toShort()
	}

	fun toLong(): Long {
		return this.temperature.toLong()
	}

	override fun toString(): String {
		return "${DecimalFormat("0.###").format(temperature)}${type.format}"
	}
}