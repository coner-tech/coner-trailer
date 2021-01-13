package org.coner.trailer.cli.di

import com.github.ajalt.clikt.core.CliktCommand
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
    bind<CliktCommand>() with singleton { RootCommand(
            di = di
    )
            .subcommands(
                    instance<ConfigCommand>(),
                    instance<EventCommand>(),
                    instance<EventPointsCalculatorCommand>(),
                    instance<RankingSortCommand>(),
                    instance<SeasonPointsCalculatorCommand>(),
                    instance<PersonCommand>(),
                    instance<MotorsportRegCommand>(),
                    instance<SeasonCommand>(),
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
                            service = instance(),
                            view = instance(),
                            useConsole = instance()
                    ),
                    ConfigDatabaseAddCommand(
                            useConsole = instance(),
                            view = instance(),
                            config = instance()
                    ),
                    ConfigDatabaseSetDefaultCommand(
                            service = instance()
                    ),
                    ConfigDatabaseRemoveCommand(
                            service = instance()
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
    bind<EventCommand>() with singleton { EventCommand(useConsole = instance())
            .subcommands(
                    EventAddCommand(di = di),
                    EventGetCommand(di = di),
                    EventListCommand(di = di),
                    EventSetCommand(di = di),
                    EventCrispyFishForcePersonAssembleCommand(di = di),
                    EventCrispyFishForcePersonAddCommand(di = di),
                    EventCrispyFishForcePersonRemoveCommand(di = di)
            )
    }
}