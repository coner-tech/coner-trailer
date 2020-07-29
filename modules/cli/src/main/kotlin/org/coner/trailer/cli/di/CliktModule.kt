package org.coner.trailer.cli.di

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import org.coner.trailer.cli.command.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val cliktModule = DI.Module("clikt") {
    bind<CliktCommand>() with singleton { RootCommand(
            config = instance()
    )
            .subcommands(
                    instance<ConfigCommand>(),
                    StubCommand()
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