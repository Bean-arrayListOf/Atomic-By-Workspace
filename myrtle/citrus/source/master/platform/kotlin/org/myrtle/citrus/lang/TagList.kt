/**************************************************************************************************
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                   *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.    *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                               *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                   *
 **************************************************************************************************/

package org.myrtle.citrus.lang

import java.util.function.Consumer


/**
 * @author cat
 * @since 2025-06-19
 **/
class TagList<E> {
	// 存储元素->标签的映射 (一个元素有多个标签)
	private val elementTags: HashMap<E, HashSet<String>> = HashMap<E, HashSet<String>>()

	// 存储标签->元素的映射 (一个标签对应多个元素)
	private val tagElements: HashMap<String, HashSet<E>> = HashMap<String, HashSet<E>>()

	/**
	 * 给元素添加标签
	 * @param element 要标记的元素
	 * @param tag 要添加的标签
	 */
	fun addTag(element: E, tag: String) {
		// 更新元素->标签映射
		elementTags.computeIfAbsent(element) { k -> HashSet() }.add(tag)

		// 更新标签->元素映射
		tagElements.computeIfAbsent(tag) { k -> HashSet() }.add(element)
	}

	/**
	 * 批量添加标签到元素
	 * @param element 要标记的元素
	 * @param tags 要添加的标签集合
	 */
	fun addTags(element: E, tags: Collection<String>) {
		tags.forEach(Consumer { tag -> addTag(element, tag) })
	}

	/**
	 * 从元素移除特定标签
	 * @param element 目标元素
	 * @param tag 要移除的标签
	 */
	fun removeTag(element: E, tag: String) {
		// 从元素->标签映射中移除
		if (elementTags.containsKey(element)) {
			val tags: HashSet<String> = elementTags.get(element)!!
			tags.remove(tag)

			if (tags.isEmpty()) {
				elementTags.remove(element)
			}
		}


		// 从标签->元素映射中移除
		if (tagElements.containsKey(tag)) {
			val elements: MutableSet<E> = tagElements.get(tag)!!
			elements.remove(element)

			if (elements.isEmpty()) {
				tagElements.remove(tag)
			}
		}
	}

	/**
	 * 移除元素的所有标签
	 * @param element 目标元素
	 */
	fun removeAllTags(element: E) {
		// 获取元素的所有标签
		val tags: HashSet<String> = elementTags.getOrDefault(element, HashSet())


		// 从所有相关标签中移除该元素
		for (tag in tags) {
			if (tagElements.containsKey(tag)) {
				val elements: HashSet<E> = tagElements.get(tag)!!
				elements.remove(element)

				if (elements.isEmpty()) {
					tagElements.remove(tag)
				}
			}
		}


		// 移除元素的所有标签记录
		elementTags.remove(element)
	}

	/**
	 * 重命名标签（修改标签名）
	 * @param oldTag 旧标签名
	 * @param newTag 新标签名
	 */
	fun renameTag(oldTag: String, newTag: String) {
		if (!tagElements.containsKey(oldTag) || oldTag == newTag) {
			return
		}


		// 获取使用旧标签的所有元素
		val taggedElements: HashSet<E> = tagElements.get(oldTag)!!


		// 更新每个元素的标签映射
		for (element in taggedElements) {
			val tags: HashSet<String> = elementTags.get(element)!!
			tags.remove(oldTag)
			tags.add(newTag)
		}


		// 更新标签->元素映射
		tagElements.put(newTag, taggedElements)
		tagElements.remove(oldTag)
	}

	/**
	 * 删除标签（包括所有使用此标签的关联）
	 * @param tag 要删除的标签
	 */
	fun deleteTag(tag: String?) {
		if (!tagElements.containsKey(tag)) {
			return
		}


		// 获取所有使用该标签的元素
		val taggedElements: HashSet<E> = tagElements.get(tag)!!


		// 从这些元素中移除该标签
		for (element in taggedElements) {
			val tags: HashSet<String>? = elementTags.get(element)
			if (tags != null) {
				tags.remove(tag)
				if (tags.isEmpty()) {
					elementTags.remove(element)
				}
			}
		}


		// 移除标签的所有记录
		tagElements.remove(tag)
	}

	/**
	 * 获取元素的所有标签
	 * @param element 目标元素
	 * @return 元素的标签集合（只读视图）
	 */
//	fun getTagsForElement(element: E?): MutableSet<String> {
//		return Collections.unmodifiableSet<T?>(
//			elementTags.getOrDefault(element, mutableSetOf<Any?>())
//		)
//	}

	/**
	 * 获取使用特定标签的所有元素
	 * @param tag 目标标签
	 * @return 使用标签的元素集合（只读视图）
	 */
//	fun getElementsWithTag(tag: String?): MutableSet<E?> {
//		return Collections.unmodifiableSet<T?>(
//			tagElements.getOrDefault(tag, mutableSetOf<Any?>())
//		)
//	}

	/**
	 * 获取系统中存在的所有标签
	 * @return 所有标签的集合（只读视图）
	 */
//	fun getAllTags(): MutableSet<String?> {
//		return Collections.unmodifiableSet<Any?>(tagElements.keys)
//	}

	/**
	 * 获取标签使用频率（按使用次数降序排序）
	 * @return 标签及其使用次数的映射（只读视图）
	 */
//	fun getTagUsageFrequency(): MutableMap<String?, Int?> {
//		return Collections.unmodifiableMap<K?, V?>(
//			tagElements.entries.stream()
//				.collect(
//					Collectors.toMap(
//						Function { Map.Entry.key },
//						Function { e: Any? -> e.getValue().size() }
//					))
//		)
//	}

}