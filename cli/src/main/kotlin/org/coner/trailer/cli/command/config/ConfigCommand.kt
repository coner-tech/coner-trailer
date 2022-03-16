package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.PermitNoDatabaseChosen

class ConfigCommand: CliktCommand(
        name = "config",
        help = "Grouping of configuration commands"
), PermitNoDatabaseChosen {

    override fun run() = Unit
}