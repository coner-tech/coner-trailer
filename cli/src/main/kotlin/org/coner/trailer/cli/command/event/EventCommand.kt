package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole

class EventCommand(
        useConsole: CliktConsole
) : CliktCommand(
        name = "event",
        help = "Manage Events"
) {

    init {
        context {
            console = useConsole
        }
    }

    override fun run() = Unit
}