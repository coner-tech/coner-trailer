package org.coner.trailer.cli.di

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.defaultCliktConsole
import org.coner.trailer.cli.command.*
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val cliktModule = DI.Module("clikt") {
    bind<CliktConsole>() with singleton { defaultCliktConsole() }
    bind<CliktCommand>() with singleton { RootCommand(
            di = di
    )
            .subcommands(
                    instance<ConfigCommand>()
            )
    }
    bind<ConfigCommand>() with singleton { ConfigCommand()
            .subcommands(
                    instance<ConfigDatabaseCommand>()
            )
    }
    bind<ConfigDatabaseCommand>() with singleton { ConfigDatabaseCommand()
            .subcommands(
                    ConfigDatabaseListCommand(
                            config = instance()
                    ),
                    ConfigDatabaseGetCommand(
                            config = instance()
                    ),
                    ConfigDatabaseSetCommand(
                            config = instance()
                    ),
                    ConfigDatabaseSetDefaultCommand(
                            config = instance()
                    ),
                    ConfigDatabaseRemoveCommand(
                            config = instance()
                    )
            )
    }
}