package org.myrtle.citrus.mapdb

import org.myrtle.citrus.mapdb.stream.MapDBAtOutputStreamRealization
import java.io.InputStream
import java.io.OutputStream
import java.sql.Connection

class MapDB {
    private val connection: Connection
    constructor(connect: Connection){
        this.connection = connect
    }
    constructor(block: ()->Connection){
        connection = block()
    }

    fun pushStream(schema: String, key: String): OutputStream{
        connection.prepareStatement("""
            create table if not exists "TABLE_DATA"(
                "indexOf" integer unique not null,
                "data" blob
            );
        """.trimIndent()).executeUpdate()

        return MapDBAtOutputStreamRealization(connection.prepareStatement("""
            insert into "TABLE_DATA"("indexOf","data") values (?,?);
        """.trimIndent()),1024)
    }

    fun getStream(schema: String, key: String): InputStream {}
}