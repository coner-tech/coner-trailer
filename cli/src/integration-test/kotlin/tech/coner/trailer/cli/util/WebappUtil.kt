package tech.coner.trailer.cli.util

import java.io.InputStream
import tech.coner.trailer.cli.clikt.StringBuilderConsole

private val webappPortPattern = Regex("Responding at .*:(\\d*)$")

private fun InputStream.findWebappPort() = bufferedReader()
    .useLines {
        it.firstNotNullOfOrNull { line: String ->
            webappPortPattern.find(line)
                ?.groups
                ?.get(1)
                ?.value
        }
    }

fun Process.findWebappPort(): String? = inputStream.findWebappPort()
fun LogbackBuffer.findWebappPort(): String? = inputStream.findWebappPort()