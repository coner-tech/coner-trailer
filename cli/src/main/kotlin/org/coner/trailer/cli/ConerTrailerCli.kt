package org.coner.trailer.cli

import com.github.ajalt.clikt.core.subcommands
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.command.RootCommand
import org.coner.trailer.cli.command.club.ClubCommand
import org.coner.trailer.cli.command.club.ClubGetCommand
import org.coner.trailer.cli.command.club.ClubSetCommand
import org.coner.trailer.cli.command.config.*
import org.coner.trailer.cli.command.event.*
import org.coner.trailer.cli.command.eventpointscalculator.*
import org.coner.trailer.cli.command.motorsportreg.*
import org.coner.trailer.cli.command.person.*
import org.coner.trailer.cli.command.policy.*
import org.coner.trailer.cli.command.rankingsort.*
import org.coner.trailer.cli.command.season.*
import org.coner.trailer.cli.command.seasonpointscalculator.*
import org.coner.trailer.cli.di.cliktModule
import org.coner.trailer.cli.di.viewModule
import org.coner.trailer.di.*
import org.kodein.di.DI

object ConerTrailerCli {

    @JvmStatic
    fun main(args: Array<out String>) {
        createCommands().main(args)
    }

    fun createCommands(): RootCommand {
        val di = DI.from(listOf(
            viewModule,
            ioModule,
            databaseModule,
            motorsportRegApiModule,
            eventResultsModule,
            cliktModule,
            allRendererModule
        ))
        val global = GlobalModel()
        return RootCommand(di, global).subcommands(
            ConfigCommand().subcommands(
                ConfigDatabaseCommand().subcommands(
                    ConfigDatabaseAddCommand(di, global),
                    ConfigDatabaseGetCommand(di, global),
                    ConfigDatabaseListCommand(di, global),
                    ConfigDatabaseSnoozleMigrateCommand(di, global),
                    ConfigDatabaseRemoveCommand(di, global),
                    ConfigDatabaseSetDefaultCommand(di, global),
                    ConfigDatabaseSnoozleCommand(global).subcommands(
                        ConfigDatabaseSnoozleInitializeCommand(di, global),
                        ConfigDatabaseSnoozleMigrateCommand(di, global)
                    )
                )
            ),
            ClubCommand(global).subcommands(
                ClubGetCommand(di, global),
                ClubSetCommand(di, global)
            ),
            PolicyCommand().subcommands(
                PolicyAddCommand(di, global),
                PolicyGetCommand(di, global),
                PolicyDeleteCommand(di, global),
                PolicyListCommand(di, global),
                PolicySetCommand(di, global)
            ),
            EventCommand().subcommands(
                EventAddCommand(di, global),
                EventCheckCommand(di, global),
                EventCrispyFishPersonMapAddCommand(di, global),
                EventCrispyFishPersonMapAssembleCommand(di, global),
                EventCrispyFishPersonMapRemoveCommand(di, global),
                EventDeleteCommand(di, global),
                EventGetCommand(di, global),
                EventListCommand(di, global),
                EventResultsCommand(di, global),
                EventSetCommand(di, global),
            ),
            EventPointsCalculatorCommand().subcommands(
                EventPointsCalculatorAddCommand(di, global),
                EventPointsCalculatorDeleteCommand(di, global),
                EventPointsCalculatorGetCommand(di, global),
                EventPointsCalculatorListCommand(di, global),
                EventPointsCalculatorSetCommand(di, global)
            ),
            MotorsportRegCommand().subcommands(
                MotorsportRegMemberCommand().subcommands(
                    MotorsportRegMemberListCommand(di, global),
                    MotorsportRegMemberImportCommand(di, global),
                    MotorsportRegMemberImportSingleCommand(di, global)
                )
            ),
            PersonCommand().subcommands(
                PersonAddCommand(di, global),
                PersonDeleteCommand(di, global),
                PersonGetCommand(di, global),
                PersonListCommand(di, global),
                PersonSearchCommand(di, global),
                PersonSetCommand(di, global)
            ),
            RankingSortCommand().subcommands(
                RankingSortAddCommand(di, global),
                RankingSortDeleteCommand(di, global),
                RankingSortGetCommand(di, global),
                RankingSortListCommand(di, global),
                RankingSortSetCommand(di, global),
                RankingSortStepsAppendCommand(di, global)
            ),
            SeasonCommand().subcommands(
                SeasonAddCommand(di, global),
                SeasonDeleteCommand(di, global),
                SeasonGetCommand(di, global),
                SeasonListCommand(di, global),
                SeasonSetCommand(di, global)
            ),
            SeasonPointsCalculatorCommand().subcommands(
                SeasonPointsCalculatorAddCommand(di, global),
                SeasonPointsCalculatorDeleteCommand(di, global),
                SeasonPointsCalculatorGetCommand(di, global),
                SeasonPointsCalculatorListCommand(di, global),
                SeasonPointsCalculatorSetCommand(di, global)
            )
        )
    }
}