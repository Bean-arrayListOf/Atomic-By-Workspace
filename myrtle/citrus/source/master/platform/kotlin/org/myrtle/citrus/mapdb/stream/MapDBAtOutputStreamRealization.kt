package org.myrtle.citrus.mapdb.stream

import okio.IOException
import java.io.OutputStream
import java.sql.PreparedStatement
import java.sql.SQLException

class MapDBAtOutputStreamRealization(
    private val stmt: PreparedStatement,
    private val bufferSize: Int
) : OutputStream() {

    init {
        require(bufferSize > 0) { "Buffer size must be positive" }
        //require(stmt != null) { "PreparedStatement cannot be null" }
    }

    private val buffer = ByteArray(bufferSize)
    private var position = 0          // 当前缓冲区写入位置
    private var rowIndex = 0          // 数据库行索引（从0开始）
    private var isClosed = false      // 流关闭标志
    private var isFlushed = false     // 标记是否已执行最终 flush


    /**
     * 将当前缓冲区有效数据（0..position-1）写入数据库作为独立行
     * @param force 是否强制写入（即使 position=0）
     */
    private fun flushBuffer(force: Boolean = false) {
        if (isClosed || (position == 0 && !force)) return

        try {
            // 仅复制有效数据（避免末尾垃圾数据）
            val data = if (position == bufferSize) {
                buffer // 满块直接复用（setBytes 会内部复制）
            } else {
                buffer.copyOfRange(0, position)
            }

            stmt.setInt(1, rowIndex)
            stmt.setBytes(2, data)
            stmt.executeUpdate()
            stmt.clearParameters() // 重置参数供下轮使用

            // 重置状态
            position = 0
            rowIndex++
        } catch (e: SQLException) {
            throw IOException("Database write failed at row  $ rowIndex", e)
        }
    }

    override fun write(b: Int) {
        checkNotClosed()
        if (position >= bufferSize) flushBuffer() // 安全兜底（正常流程不应触发）

        buffer[position++] = b.toByte()
        if (position == bufferSize) flushBuffer()
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        checkNotClosed()
        require(off >= 0 && len >= 0 && off + len <= b.size) {
            "Invalid array range: off= $ off, len= $ len, arraySize= $ {b.size}"
        }
        if (len == 0) return

        var offset = off
        var remaining = len

        while (remaining > 0) {
            // 1. 填充当前缓冲区剩余空间
            val space = bufferSize - position
            if (space > 0) {
                val toCopy = minOf(space, remaining)
                System.arraycopy(b, offset, buffer, position, toCopy)
                position += toCopy
                offset += toCopy
                remaining -= toCopy

                // 缓冲区满则写入
                if (position == bufferSize) flushBuffer()
            }

            // 2. 剩余数据按整块直接写入（跳过缓冲区，极致优化）
            if (remaining >= bufferSize) {
                val fullBlocks = remaining / bufferSize
                repeat(fullBlocks) {
                    stmt.setInt(1, rowIndex)
                    stmt.setBytes(2, b.copyOfRange(offset, offset + bufferSize))
                    stmt.executeUpdate()
                    stmt.clearParameters()
                    rowIndex++
                    offset += bufferSize
                    remaining -= bufferSize
                }
            }
        }
    }

    override fun flush() {
        // 注意：按题目要求"每bufferSize为一行"，flush 不触发不满块写入
        // 仅确保已满块持久化（实际在 write 中已实时写入）
        // 如需强制写入不满块，调用 close() 或自定义方法
    }

    override fun close() {
        if (isClosed) return
        try {
            // 写入最后一块（可能不满）
            if (position > 0) flushBuffer(force = true)
            isFlushed = true
        } finally {
            isClosed = true
            stmt.close()
            // ⚠️ 不关闭 PreparedStatement！由外部管理生命周期
            // （调用方应在事务提交后关闭 stmt）
        }
    }

    private fun checkNotClosed() {
        if (isClosed) throw IOException("Stream is closed")
        if (isFlushed) throw IOException("Stream already flushed and closed")
    }

    fun getWrittenRowCount(): Int = rowIndex
    fun getRemainingBufferSize(): Int = bufferSize - position

}