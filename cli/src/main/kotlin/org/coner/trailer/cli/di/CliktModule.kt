package org.coner.trailer.cli.di

import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.defaultCliktConsole
import org.coner.trailer.cli.command.RootCommand
import org.coner.trailer.cli.command.config.*
import org.coner.trailer.cli.command.event.*
import org.coner.trailer.cli.command.eventpointscalculator.*
import org.coner.trailer.cli.command.motorsportreg.*
import org.coner.trailer.cli.command.person.*
import org.coner.trailer.cli.command.policy.*
import org.coner.trailer.cli.command.rankingsort.*
import org.coner.trailer.cli.command.season.*
import org.coner.trailer.cli.command.seasonpointscalculator.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import kotlin.io.path.ExperimentalPathApi

@OptIn(ExperimentalPathApi::class)
val cliktModule = DI.Module("coner.trailer.cli.clikt") {
        bind<CliktConsole>() with singleton { defaultCliktConsole() }
        bind<RootCommand>() with singleton { RootCommand(di = di) }

        // Config commands
        bind<ConfigCommand>() with singleton { ConfigCommand() }
        bind<ConfigDatabaseCommand>() with singleton { ConfigDatabaseCommand() }
        bind<ConfigDatabaseListCommand>() with singleton { ConfigDatabaseListCommand(di = di) }
        bind<ConfigDatabaseGetCommand>() with singleton { ConfigDatabaseGetCommand(di = di) }
        bind<ConfigDatabaseAddCommand>() with singleton { ConfigDatabaseAddCommand(di = di) }
        bind<ConfigDatabaseSetDefaultCommand>() with singleton { ConfigDatabaseSetDefaultCommand(di = di) }
        bind<ConfigDatabaseRemoveCommand>() with singleton { ConfigDatabaseRemoveCommand(di = di) }

        // Event Points Calculator commands
        bind<EventPointsCalculatorCommand>() with singleton { EventPointsCalculatorCommand() }
        bind<EventPointsCalculatorAddCommand>() with singleton {
                EventPointsCalculatorAddCommand(di = di, useConsole = instance())
        }
        bind<EventPointsCalculatorGetCommand>() with singleton {
                EventPointsCalculatorGetCommand(di = di, useConsole = instance())
        }
        bind<EventPointsCalculatorListCommand>() with singleton {
                EventPointsCalculatorListCommand(di = di, useConsole = instance(), view = instance())
        }
        bind<EventPointsCalculatorSetCommand>() with singleton {
                EventPointsCalculatorSetCommand(di = di, useConsole = instance())
        }
        bind<EventPointsCalculatorDeleteCommand>() with singleton {
                EventPointsCalculatorDeleteCommand(di = di, useConsole = instance())
        }

        // Ranking Sort commands
        bind<RankingSortCommand>() with singleton { RankingSortCommand() }
        bind<RankingSortAddCommand>() with singleton {
                RankingSortAddCommand(di = di, useConsole = instance())
        }
        bind<RankingSortDeleteCommand>() with singleton {
                RankingSortDeleteCommand(di = di, useConsole = instance())
        }
        bind<RankingSortGetCommand>() with singleton {
                RankingSortGetCommand(di = di, useConsole = instance())
        }
        bind<RankingSortListCommand>() with singleton {
                RankingSortListCommand(di = di, useConsole = instance())
        }
        bind<RankingSortSetCommand>() with singleton {
                RankingSortSetCommand(di = di, useConsole = instance())
        }
        bind<RankingSortStepsAppendCommand>() with singleton {
                RankingSortStepsAppendCommand(di = di, useConsole = instance())
        }

        // Season Points Calculator commands
        bind<SeasonPointsCalculatorParameterMapper>() with singleton { SeasonPointsCalculatorParameterMapper(
                eventPointsCalculatorService = instance()
        ) }
        bind<SeasonPointsCalculatorCommand>() with singleton { SeasonPointsCalculatorCommand() }
        bind<SeasonPointsCalculatorAddCommand>() with singleton {
                SeasonPointsCalculatorAddCommand(di = di, useConsole = instance())
        }
        bind<SeasonPointsCalculatorGetCommand>() with singleton {
                SeasonPointsCalculatorGetCommand(di = di, useConsole = instance())
        }
        bind<SeasonPointsCalculatorListCommand>() with singleton {
                SeasonPointsCalculatorListCommand(di = di, useConsole = instance())
        }
        bind<SeasonPointsCalculatorSetCommand>() with singleton {
                SeasonPointsCalculatorSetCommand(di = di, useConsole = instance())
        }
        bind<SeasonPointsCalculatorDeleteCommand>() with singleton {
                SeasonPointsCalculatorDeleteCommand(di = di, useConsole = instance())
        }

        // Person commands
        bind<PersonCommand>() with singleton { PersonCommand(useConsole = instance()) }
        bind<PersonAddCommand>() with singleton {
                PersonAddCommand(di = di, useConsole = instance())
        }
        bind<PersonGetCommand>() with singleton {
                PersonGetCommand(di = di, useConsole = instance())
        }
        bind<PersonListCommand>() with singleton {
                PersonListCommand(di = di, useConsole = instance())
        }
        bind<PersonSearchCommand>() with singleton {
                PersonSearchCommand(di = di, useConsole = instance())
        }
        bind<PersonSetCommand>() with singleton {
                PersonSetCommand(di = di, useConsole = instance())
        }
        bind<PersonDeleteCommand>() with singleton {
                PersonDeleteCommand(di = di, useConsole = instance())
        }

        // MotorsportReg commands
        bind<MotorsportRegCommand>() with singleton { MotorsportRegCommand() }
        bind<MotorsportRegMemberCommand>() with singleton { MotorsportRegMemberCommand()
        }
        bind<MotorsportRegMemberListCommand>() with singleton {
                MotorsportRegMemberListCommand(di = di, useConsole = instance())
        }
        bind<MotorsportRegMemberImportCommand>() with singleton {
                MotorsportRegMemberImportCommand(di = di, useConsole = instance())
        }
        bind<MotorsportRegMemberImportSingleCommand>() with singleton {
                MotorsportRegMemberImportSingleCommand(di = di, useConsole = instance())
        }

        // Season commands
        bind<SeasonCommand>() with singleton { SeasonCommand(useConsole = instance()) }
        bind<SeasonAddCommand>() with singleton { SeasonAddCommand(di = di, useConsole = instance()) }
        bind<SeasonGetCommand>() with singleton { SeasonGetCommand(di = di, useConsole = instance()) }
        bind<SeasonListCommand>() with singleton { SeasonListCommand(di = di, useConsole = instance()) }
        bind<SeasonDeleteCommand>() with singleton { SeasonDeleteCommand(di = di, useConsole = instance()) }
        bind<SeasonSetCommand>() with singleton { SeasonSetCommand(di = di, useConsole = instance()) }

        // Policy commands
        bind<PolicyCommand>() with singleton { PolicyCommand() }
        bind<PolicyAddCommand>() with singleton { PolicyAddCommand(di = di) }
        bind<PolicyListCommand>() with singleton { PolicyListCommand(di = di) }
        bind<PolicyGetCommand>() with singleton { PolicyGetCommand(di = di) }
        bind<PolicySetCommand>() with singleton { PolicySetCommand(di = di) }
        bind<PolicyDeleteCommand>() with singleton { PolicyDeleteCommand(di) }

        // Event commands
        bind<EventCommand>() with singleton { EventCommand() }
        bind<EventAddCommand>() with singleton { EventAddCommand(di = di) }
        bind<EventCheckCommand>() with singleton { EventCheckCommand(di = di) }
        bind<EventCrispyFishPersonMapAddCommand>() with singleton { EventCrispyFishPersonMapAddCommand(di = di) }
        bind<EventCrispyFishPersonMapAssembleCommand>() with singleton { EventCrispyFishPersonMapAssembleCommand(di = di) }
        bind<EventCrispyFishPersonMapRemoveCommand>() with singleton { EventCrispyFishPersonMapRemoveCommand(di = di) }
        bind<EventDeleteCommand>() with singleton { EventDeleteCommand(di = di) }
        bind<EventGetCommand>() with singleton { EventGetCommand(di = di) }
        bind<EventListCommand>() with singleton { EventListCommand(di = di) }
        bind<EventSetCommand>() with singleton { EventSetCommand(di = di) }

        // Event Results commands
        bind<EventResultsCommand>() with singleton { EventResultsCommand() }
        bind<EventResultsOverallCommand>() with singleton { EventResultsOverallCommand(di = di) }
}