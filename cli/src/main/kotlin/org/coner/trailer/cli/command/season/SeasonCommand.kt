package org.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole

class SeasonCommand(
        useConsole: CliktConsole
) : CliktCommand(
        help = "Manage seasons"
) {

    init {
        context {
            console = useConsole
        }
    }

    override fun run() = Unit
}