package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand

class ConfigDatabaseCommand : CliktCommand(
        name = "database",
        help = "Grouping of database configuration commands"
) {

    override fun run() = Unit
}
