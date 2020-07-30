package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand

class ConfigCommand() : CliktCommand(
        name = "config",
        help = "Grouping of configuration commands"
), RootCommand.PermitNoDatabaseChosen {

    override fun run() = Unit
}