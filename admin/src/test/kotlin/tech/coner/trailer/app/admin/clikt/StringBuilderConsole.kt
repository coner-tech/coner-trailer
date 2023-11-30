package tech.coner.trailer.app.admin.clikt

import assertk.Assert
import assertk.assertions.prop
import com.github.ajalt.clikt.output.CliktConsole

class StringBuilderConsole : CliktConsole {

    private var out = StringBuilder()
    private var err = StringBuilder()
    private var prompting: Boolean = false
    private var input: String? = null

    val output: String get() = out.render()
    val error: String get() = err.render()

    private fun StringBuilder.render() = toString()
        .trim()
        .replace("\r\n", "\n") // tests expect unix style line-endings

    override val lineSeparator = requireNotNull(System.lineSeparator())

    override fun print(text: String, error: Boolean) {
        when (error) {
            false -> out.append(text)
            true -> err.append(text)
        }
    }

    override fun promptForLine(prompt: String, hideInput: Boolean): String? {
        prompting = true
        out.append(prompt)
        while (input == null) {
            Thread.sleep(1)
        }
        val received = input
        if (!hideInput) {
            out.appendLine(received)
        }
        input = null
        prompting = false
        return received
    }

    fun writeInput(input: String) {
        check(prompting)
        this.input = input
    }

    fun clear() {
        out = StringBuilder()
        err = StringBuilder()
    }
}


fun Assert<StringBuilderConsole>.output() = prop("output") { it.output }
fun Assert<StringBuilderConsole>.error() = prop("error") { it.error }
