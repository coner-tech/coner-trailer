package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.RootCommand

class ConfigCommand() : CliktCommand(
        name = "config",
        help = "Grouping of configuration commands"
), RootCommand.PermitNoDatabaseChosen {

    override fun run() = Unit
}