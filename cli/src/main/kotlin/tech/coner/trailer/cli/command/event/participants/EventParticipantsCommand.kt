package tech.coner.trailer.cli.command.event.participants

import com.github.ajalt.clikt.core.CliktCommand

class EventParticipantsCommand : CliktCommand(
    name = "participants",
    help = "Manage Event Participants"
) {
    override fun run() = Unit
}