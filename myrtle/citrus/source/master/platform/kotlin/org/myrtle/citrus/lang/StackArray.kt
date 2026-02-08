package org.myrtle.citrus.lang

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * @author CitrusCat
 * @since 2024/12/26
 */
@Deprecated("开发中,不稳定",level = DeprecationLevel.WARNING)
class StackArray<T> : Iterable<T>,Iterator<T>,Serializable {
    private val serialVersionUID = 7486232611119566153L
    @SerializedName("Stack")
    @Expose
    private val stacks: ArrayList<T> = arrayListOf()
    private val has2 = 0
    private var has: Int = -1
    private var view: Boolean = false

    constructor()

    private constructor(view: Boolean){
        this.view = view
    }

    override fun hasNext(): Boolean {
        if (view && has==stacks.size-1 || has2 == stacks.size){
            return false
        }else{
            has++
            return true
        }
    }

    override fun next(): T{
        return if (view){
            stacks[has]
        }else{
            stacks.removeAt(0)
        }
    }

    fun size():Int{
        return stacks.size
    }

    fun add(t:T){
        stacks.add(0,t)
    }

    fun view():T?{
        return try {
            stacks[0]
        }catch (_:IndexOutOfBoundsException){
            null
        }
    }

    fun get():T?{
        return try {
            stacks.removeAt(0)
        }catch (_:IndexOutOfBoundsException){
            null
        }
    }

    fun copy():StackArray<T>{
        val reversed = stacks.reversed()
        val list = stackArrayOf<T>()
        for (i in reversed){
            list.add(i)
        }
        return list
    }

    fun toList():List<T>{
        return stacks
    }

    fun toArrayList():ArrayList<T>{
        return stacks
    }

    /**
     * 重写迭代器方法
     *
     * 该方法返回一个迭代器对象，用于遍历集合中的元素
     * 在这里，由于该类已经实现了Iterator接口，所以可以直接返回this对象
     *
     * @return Iterator<T> 迭代器对象，用于遍历集合中的元素
     * @see Iterator
     * @author arrayListOf
     * @since 24.10.05.01
     */
    override fun iterator(): Iterator<T> {
        return this
    }
    /**
     * 返回一个迭代器，用于遍历当前集合的元素
     * 这个迭代器基于栈数组实现，可以决定是否以反向顺序遍历集合
     *
     * @param view 一个布尔值，如果为true，则迭代器将以反向顺序遍历集合；如果为false，则以正向顺序遍历
     * @return 返回一个迭代器，用于遍历当前集合的元素
     * @see Iterator
     * @author arrayListOf
     * @since 24.10.05.01
     */
    fun iterator(view: Boolean): Iterator<T> {
        // 创建一个栈数组实例，用于存储集合的元素
        val value = StackArray<T>(view)
        // 获取当前集合中所有栈的列表，并将其反转，以便能够按反向顺序遍历集合
        val list = stacks.reversed()
        // 遍历反转后的栈列表
        for (i in list) {
            // 将每个栈的元素添加到栈数组实例中
            value.add(i)
        }
        // 返回栈数组实例作为迭代器
        return value
    }

    /**
     * 将当前对象转换为Stack集合
     *
     * 此方法用于将当前对象（假设当前对象是一个集合类型，如List或Set）中的元素添加到一个Stack集合中
     * 它首先创建一个类型为T的Stack实例，然后将当前对象中的所有元素添加到这个Stack中
     * 最后，返回这个包含了当前对象所有元素的Stack实例
     *
     * @return Stack<T> 包含了当前对象所有元素的Stack集合
     * @see Stack
     * @author arrayListOf
     * @since 24.10.05.01
     */
    fun toStack(): Stack<T> {
        // 创建一个类型为T的Stack实例
        val s = Stack<T>()
        // 将当前对象中的所有元素添加到Stack集合中
        s.addAll(stacks)

        // TODO: ByteKit可能是一个工具类，这里可能使用了其中的某个方法或特性，但具体用途需要更多上下文才能明确
	    //ByteKit

        // 返回包含当前对象所有元素的Stack集合
        return s
    }

}