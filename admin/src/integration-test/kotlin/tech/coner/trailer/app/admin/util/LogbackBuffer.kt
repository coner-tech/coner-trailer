package tech.coner.trailer.app.admin.util

import java.io.BufferedInputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream

class LogbackBuffer : AutoCloseable {
    val outputStream = PipedOutputStream()
    val inputStream = BufferedInputStream(PipedInputStream(outputStream))

    override fun close() {
        outputStream.close()
        inputStream.close()
    }
}

