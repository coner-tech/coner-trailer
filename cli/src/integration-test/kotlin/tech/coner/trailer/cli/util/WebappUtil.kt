package tech.coner.trailer.cli.util

import java.io.InputStream
import tech.coner.trailer.cli.clikt.StringBufferConsole

private val webappPortPattern = Regex("Responding at .*:(\\d*)$")

private fun InputStream.findWebappPort() = bufferedReader()
    .use { reader ->
        reader
            .lineSequence()
            .firstNotNullOfOrNull { line: String ->
                webappPortPattern.find(line)
                    ?.groups
                    ?.get(1)
                    ?.value
            }
    }

fun Process.findWebappPort(): String? = inputStream.findWebappPort()
fun StringBufferConsole.findWebappPort(): String? = outputStream.findWebappPort()