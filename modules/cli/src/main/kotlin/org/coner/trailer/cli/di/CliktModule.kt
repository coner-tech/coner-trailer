package org.coner.trailer.cli.di

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.defaultCliktConsole
import org.coner.trailer.cli.command.*
import org.coner.trailer.cli.command.config.*
import org.coner.trailer.cli.command.participanteventresultpointscalculator.*
import org.coner.trailer.cli.command.rankingsort.*
import org.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorCommand
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
                    instance<ConfigCommand>(),
                    instance<ParticipantEventResultPointsCalculatorCommand>(),
                    instance<RankingSortCommand>(),
                    instance<SeasonPointsCalculatorCommand>()
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
                            useConsole = instance(),
                            view = instance(),
                            config = instance()
                    ),
                    ConfigDatabaseGetCommand(
                            config = instance(),
                            view = instance(),
                            useConsole = instance()
                    ),
                    ConfigDatabaseSetCommand(
                            useConsole = instance(),
                            view = instance(),
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
    bind<ParticipantEventResultPointsCalculatorCommand>() with singleton { ParticipantEventResultPointsCalculatorCommand()
            .subcommands(
                    ParticipantEventResultPointsCalculatorAddCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    ParticipantEventResultPointsCalculatorGetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    ParticipantEventResultPointsCalculatorListCommand(
                            di = di,
                            useConsole = instance(),
                            view = instance()
                    ),
                    ParticipantEventResultPointsCalculatorSetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    ParticipantEventResultPointsCalculatorDeleteCommand(
                            di = di,
                            useConsole = instance()
                    )
            )
    }
    bind<RankingSortCommand>() with singleton { RankingSortCommand()
            .subcommands(
                    RankingSortAddCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    RankingSortStepsAppendCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    RankingSortListCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    RankingSortGetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    RankingSortSetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    RankingSortDeleteCommand(
                            di = di,
                            useConsole = instance()
                    )
            )
    }
    bind<SeasonPointsCalculatorCommand>() with singleton { SeasonPointsCalculatorCommand()
            .subcommands()
    }
}