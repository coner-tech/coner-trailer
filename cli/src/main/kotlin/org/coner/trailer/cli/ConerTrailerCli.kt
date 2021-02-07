package org.coner.trailer.cli

import com.github.ajalt.clikt.core.subcommands
import org.coner.trailer.cli.command.RootCommand
import org.coner.trailer.cli.command.config.*
import org.coner.trailer.cli.command.event.*
import org.coner.trailer.cli.command.eventpointscalculator.*
import org.coner.trailer.cli.command.motorsportreg.*
import org.coner.trailer.cli.command.person.*
import org.coner.trailer.cli.command.rankingsort.*
import org.coner.trailer.cli.command.season.*
import org.coner.trailer.cli.command.seasonpointscalculator.*
import org.coner.trailer.cli.di.cliktModule
import org.coner.trailer.cli.di.ioModule
import org.coner.trailer.cli.di.viewModule
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
object ConerTrailerCli {

    @JvmStatic
    fun main(args: Array<out String>) {
        factory().main(args)
    }

    fun factory(): RootCommand {
        val di = DI.from(listOf(viewModule, ioModule, cliktModule))
        val rootCommand: RootCommand = di.direct.run {
            instance<RootCommand>().subcommands(
                instance<ConfigCommand>().subcommands(
                    instance<ConfigDatabaseCommand>().subcommands(
                        instance<ConfigDatabaseAddCommand>(),
                        instance<ConfigDatabaseGetCommand>(),
                        instance<ConfigDatabaseListCommand>(),
                        instance<ConfigDatabaseRemoveCommand>(),
                        instance<ConfigDatabaseSetDefaultCommand>()
                    )
                ),
                instance<EventCommand>().subcommands(
                    instance<EventAddCommand>(),
                    instance<EventCheckCommand>(),
                    instance<EventCrispyFishPersonMapAddCommand>(),
                    instance<EventCrispyFishPersonMapAssembleCommand>(),
                    instance<EventCrispyFishPersonMapRemoveCommand>(),
                    instance<EventDeleteCommand>(),
                    instance<EventGetCommand>(),
                    instance<EventListCommand>(),
                    instance<EventSetCommand>(),
                ),
                instance<EventPointsCalculatorCommand>().subcommands(
                    instance<EventPointsCalculatorAddCommand>(),
                    instance<EventPointsCalculatorDeleteCommand>(),
                    instance<EventPointsCalculatorGetCommand>(),
                    instance<EventPointsCalculatorListCommand>(),
                    instance<EventPointsCalculatorSetCommand>()
                ),
                instance<MotorsportRegCommand>().subcommands(
                    instance<MotorsportRegMemberCommand>().subcommands(
                        instance<MotorsportRegMemberListCommand>(),
                        instance<MotorsportRegMemberImportCommand>(),
                        instance<MotorsportRegMemberImportSingleCommand>()
                    )
                ),
                instance<PersonCommand>().subcommands(
                    instance<PersonAddCommand>(),
                    instance<PersonDeleteCommand>(),
                    instance<PersonGetCommand>(),
                    instance<PersonListCommand>(),
                    instance<PersonSearchCommand>(),
                    instance<PersonSetCommand>()
                ),
                instance<RankingSortCommand>().subcommands(
                    instance<RankingSortAddCommand>(),
                    instance<RankingSortDeleteCommand>(),
                    instance<RankingSortGetCommand>(),
                    instance<RankingSortListCommand>(),
                    instance<RankingSortSetCommand>(),
                    instance<RankingSortStepsAppendCommand>()
                ),
                instance<SeasonCommand>().subcommands(
                    instance<SeasonAddCommand>(),
                    instance<SeasonDeleteCommand>(),
                    instance<SeasonGetCommand>(),
                    instance<SeasonListCommand>(),
                    instance<SeasonSetCommand>()
                ),
                instance<SeasonPointsCalculatorCommand>().subcommands(
                    instance<SeasonPointsCalculatorAddCommand>(),
                    instance<SeasonPointsCalculatorDeleteCommand>(),
                    instance<SeasonPointsCalculatorGetCommand>(),
                    instance<SeasonPointsCalculatorListCommand>(),
                    instance<SeasonPointsCalculatorSetCommand>()
                )
            )
        }
        return rootCommand
    }
}