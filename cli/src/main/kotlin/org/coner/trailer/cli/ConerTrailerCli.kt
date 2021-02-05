package org.coner.trailer.cli

import com.github.ajalt.clikt.core.subcommands
import org.coner.trailer.cli.command.RootCommand
import org.coner.trailer.cli.command.config.*
import org.coner.trailer.cli.command.eventpointscalculator.*
import org.coner.trailer.cli.di.cliktModule
import org.coner.trailer.cli.di.ioModule
import org.coner.trailer.cli.di.viewModule
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

fun main(args: Array<out String>) {
    val di = DI.from(listOf(
        viewModule,
        ioModule,
        cliktModule
    ))
    val rootCommand: RootCommand = di.direct.instance()
    rootCommand.subcommands(
        di.direct.instance<ConfigCommand>().subcommands(
            di.direct.instance<ConfigDatabaseCommand>().subcommands(
                di.direct.instance<ConfigDatabaseAddCommand>(),
                di.direct.instance<ConfigDatabaseGetCommand>(),
                di.direct.instance<ConfigDatabaseListCommand>(),
                di.direct.instance<ConfigDatabaseRemoveCommand>(),
                di.direct.instance<ConfigDatabaseSetDefaultCommand>()
            )
        ),
        di.direct.instance<EventPointsCalculatorCommand>().subcommands(
            di.direct.instance<EventPointsCalculatorAddCommand>(),
            di.direct.instance<EventPointsCalculatorDeleteCommand>(),
            di.direct.instance<EventPointsCalculatorGetCommand>(),
            di.direct.instance<EventPointsCalculatorListCommand>(),
            di.direct.instance<EventPointsCalculatorSetCommand>()
        )
    )
    rootCommand.main(args)
}