package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand

class EventCommand : CliktCommand(
        name = "event",
        help = "Manage Events"
) {

    override fun run() = Unit
}