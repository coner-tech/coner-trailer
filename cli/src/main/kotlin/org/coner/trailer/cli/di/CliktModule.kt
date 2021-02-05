package org.coner.trailer.cli.di

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.defaultCliktConsole
import org.coner.trailer.cli.command.RootCommand
import org.coner.trailer.cli.command.config.*
import org.coner.trailer.cli.command.event.*
import org.coner.trailer.cli.command.eventpointscalculator.*
import org.coner.trailer.cli.command.motorsportreg.*
import org.coner.trailer.cli.command.person.*
import org.coner.trailer.cli.command.rankingsort.*
import org.coner.trailer.cli.command.season.*
import org.coner.trailer.cli.command.seasonpointscalculator.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import kotlin.io.path.ExperimentalPathApi

@OptIn(ExperimentalPathApi::class)
val cliktModule = DI.Module("clikt") {
        bind<CliktConsole>() with singleton { defaultCliktConsole() }
        bind<RootCommand>() with singleton { RootCommand() }

        // Config commands
        bind<ConfigCommand>() with singleton { ConfigCommand() }
        bind<ConfigDatabaseCommand>() with singleton { ConfigDatabaseCommand() }
        bind<ConfigDatabaseListCommand>() with singleton {
                ConfigDatabaseListCommand(useConsole = instance(), view = instance(), config = instance())
        }
        bind<ConfigDatabaseGetCommand>() with singleton {
                ConfigDatabaseGetCommand(service = instance(), view = instance(), useConsole = instance())
        }
        bind<ConfigDatabaseAddCommand>() with singleton {
                ConfigDatabaseAddCommand(useConsole = instance(), view = instance(), config = instance())
        }
        bind<ConfigDatabaseSetDefaultCommand>() with singleton {
                ConfigDatabaseSetDefaultCommand(service = instance())
        }
        bind<ConfigDatabaseRemoveCommand>() with singleton { ConfigDatabaseRemoveCommand(
                service = instance()
        ) }

        // Event Points Calculator commands
        bind<EventPointsCalculatorCommand>() with singleton { EventPointsCalculatorCommand() }
        bind<EventPointsCalculatorAddCommand>() with singleton { EventPointsCalculatorAddCommand(
                di = di,
                useConsole = instance()
        ) }
        bind<EventPointsCalculatorGetCommand>() with singleton { EventPointsCalculatorGetCommand(
                di = di,
                useConsole = instance()
        ) }
        bind<EventPointsCalculatorListCommand>() with singleton { EventPointsCalculatorListCommand(
                di = di,
                useConsole = instance(),
                view = instance()
        ) }
        bind<EventPointsCalculatorSetCommand>() with singleton { EventPointsCalculatorSetCommand(
                di = di,
                useConsole = instance()
        ) }
        bind<EventPointsCalculatorDeleteCommand>() with singleton { EventPointsCalculatorDeleteCommand(
                di = di,
                useConsole = instance()
        ) }

        // Ranking Sort commands
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
                        ),
                        PersonListCommand(
                                di = di,
                                useConsole = instance()
                        ),
                        PersonSearchCommand(
                                di = di,
                                useConsole = instance()
                        ),
                        PersonSetCommand(
                                di = di,
                                useConsole = instance()
                        ),
                        PersonDeleteCommand(
                                di = di,
                                useConsole = instance()
                        )
                )
        }
        bind<MotorsportRegCommand>() with singleton { MotorsportRegCommand(useConsole = instance(), di = di)
                .subcommands(MotorsportRegMemberCommand(di = di, useConsole = instance())
                        .subcommands(
                                MotorsportRegMemberListCommand(di = di, useConsole = instance()),
                                MotorsportRegMemberImportCommand(di = di, useConsole = instance()),
                                MotorsportRegMemberImportSingleCommand(di = di, useConsole = instance())
                        )
                )
        }
        bind<SeasonCommand>() with singleton { SeasonCommand(useConsole = instance())
                .subcommands(
                        SeasonAddCommand(di = di, useConsole = instance()),
                        SeasonGetCommand(di = di, useConsole = instance()),
                        SeasonListCommand(di = di, useConsole = instance()),
                        SeasonSetCommand(di = di, useConsole = instance()),
                        SeasonDeleteCommand(di = di, useConsole = instance())
                )
        }
        bind<EventCommand>() with singleton { EventCommand()
                .subcommands(
                        EventAddCommand(di = di),
                        EventGetCommand(di = di),
                        EventListCommand(di = di),
                        EventSetCommand(di = di),
                        EventCrispyFishPersonMapAssembleCommand(di = di),
                        EventCrispyFishPersonMapAddCommand(di = di),
                        EventCrispyFishPersonMapRemoveCommand(di = di),
                        EventCheckCommand(di = di),
                        EventDeleteCommand(di = di)
                )
        }
}