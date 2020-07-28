package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject

class StubCommand : CliktCommand(
        name = "stub",
        help = "Exploratory command stubbing out rootPayload access"
) {

    private val rootPayload: RootCommand.Payload by requireObject()

    override fun run() {
        echo("Stub found ${rootPayload.databaseConfiguration?.name}")
    }
}