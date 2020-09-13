package org.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole

class PersonCommand(
        useConsole: CliktConsole
) : CliktCommand(
        help = "Manage people (records)"
) {

    init {
        context {
            console = useConsole
        }
    }

    override fun run() = Unit
}