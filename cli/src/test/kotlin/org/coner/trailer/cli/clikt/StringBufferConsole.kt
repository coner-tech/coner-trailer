package org.coner.trailer.cli.clikt

import com.github.ajalt.clikt.output.CliktConsole

class StringBufferConsole : CliktConsole {

    private val out = StringBuffer()
    private val err = StringBuffer()

    val output: String get() = out.toString().trim()
    val error: String get() = err.toString().trim()

    override val lineSeparator = requireNotNull(System.lineSeparator())

    override fun print(text: String, error: Boolean) {
        when (error) {
            false -> out.append(text)
            true -> err.append(text)
        }
    }

    override fun promptForLine(prompt: String, hideInput: Boolean): String? {
        throw UnsupportedOperationException()
    }
}