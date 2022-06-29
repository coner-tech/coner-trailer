package tech.coner.trailer.cli.command.event.run

import com.github.ajalt.clikt.core.CliktCommand

class EventRunCommand() : CliktCommand(
    name = "run",
    help = "Manage the runs at an event"
) {
    override fun run() = Unit
}