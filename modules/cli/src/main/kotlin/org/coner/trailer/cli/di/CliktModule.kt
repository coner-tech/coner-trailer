package org.coner.trailer.cli.di

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.defaultCliktConsole
import org.coner.trailer.cli.command.*
import org.coner.trailer.cli.command.config.*
import org.coner.trailer.cli.command.eventpointscalculator.*
import org.coner.trailer.cli.command.person.PersonAddCommand
import org.coner.trailer.cli.command.person.PersonCommand
import org.coner.trailer.cli.command.person.PersonGetCommand
import org.coner.trailer.cli.command.rankingsort.*
import org.coner.trailer.cli.command.seasonpointscalculator.*
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
                    instance<EventPointsCalculatorCommand>(),
                    instance<RankingSortCommand>(),
                    instance<SeasonPointsCalculatorCommand>(),
                    instance<PersonCommand>()
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
    bind<EventPointsCalculatorCommand>() with singleton { EventPointsCalculatorCommand()
            .subcommands(
                    EventPointsCalculatorAddCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    EventPointsCalculatorGetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    EventPointsCalculatorListCommand(
                            di = di,
                            useConsole = instance(),
                            view = instance()
                    ),
                    EventPointsCalculatorSetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    EventPointsCalculatorDeleteCommand(
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
    bind<SeasonPointsCalculatorParameterMapper>() with singleton { SeasonPointsCalculatorParameterMapper(
            eventPointsCalculatorService = instance()
    ) }
    bind<SeasonPointsCalculatorCommand>() with singleton { SeasonPointsCalculatorCommand()
            .subcommands(
                    SeasonPointsCalculatorAddCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    SeasonPointsCalculatorGetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    SeasonPointsCalculatorListCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    SeasonPointsCalculatorSetCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    SeasonPointsCalculatorDeleteCommand(
                            di = di,
                            useConsole = instance()
                    )
            )
    }
    bind<PersonCommand>() with singleton { PersonCommand(useConsole = instance())
            .subcommands(
                    PersonAddCommand(
                            di = di,
                            useConsole = instance()
                    ),
                    PersonGetCommand(
                            di = di,
                            useConsole = instance()
                    )
            )
    }
}