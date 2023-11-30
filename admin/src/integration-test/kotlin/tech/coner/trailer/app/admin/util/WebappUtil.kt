package tech.coner.trailer.app.admin.util

import java.io.InputStream

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
