package tech.coner.trailer.cli.command.event.participant

import com.github.ajalt.clikt.core.CliktCommand

class EventParticipantCommand : CliktCommand(
    name = "participants",
    help = "Manage the participants at an event"
) {
    override fun run() = Unit
}