package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole

class EventCommand() : CliktCommand(
        name = "event",
        help = "Manage Events"
) {

    override fun run() = Unit
}