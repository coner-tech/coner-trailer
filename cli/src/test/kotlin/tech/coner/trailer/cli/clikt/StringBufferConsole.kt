package tech.coner.trailer.cli.clikt

import com.github.ajalt.clikt.output.CliktConsole

class StringBufferConsole : CliktConsole {

    private var out = StringBuffer()
    private var err = StringBuffer()
    private var prompting: Boolean = false
    private var input: String? = null

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
        out = StringBuffer()
        err = StringBuffer()
    }
}