/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus.util

/**
 * @author cat
 * @since 2025-07-15
 **/
class MainLoader {
	companion object{
		@JvmStatic
		fun main(vararg args: String) {
			val mainCP = System.getProperty("ml.cp") ?: throw NullPointerException("Main ClassPath is null")

			val mainClass = Class.forName(mainCP)
			val constructors = mainClass.getDeclaredConstructor()
			val instance = constructors.newInstance()

		}
	}
}