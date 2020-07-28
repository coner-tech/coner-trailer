package org.coner.trailer.cli

import com.github.ajalt.clikt.core.subcommands
import org.coner.trailer.cli.command.*

fun main(args: Array<out String>) {
    val app = RootCommand().subcommands(
            ConfigCommand().subcommands(
                    ConfigDatabase().subcommands(
                            ConfigDatabaseListCommand(),
                            ConfigDatabaseGetCommand(),
                            ConfigDatabaseSetCommand(),
                            ConfigDatabaseSetDefaultCommand(),
                            ConfigDatabaseRemoveCommand()
                    )
            ),
            StubCommand()
    )
    app.main(args)
}