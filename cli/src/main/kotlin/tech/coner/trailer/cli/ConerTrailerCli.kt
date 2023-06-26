package tech.coner.trailer.cli

import com.github.ajalt.clikt.core.subcommands
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.command.RootCommand
import tech.coner.trailer.cli.command.club.ClubCommand
import tech.coner.trailer.cli.command.club.ClubGetCommand
import tech.coner.trailer.cli.command.club.ClubSetCommand
import tech.coner.trailer.cli.command.config.*
import tech.coner.trailer.cli.command.event.*
import tech.coner.trailer.cli.command.event.participant.EventParticipantCommand
import tech.coner.trailer.cli.command.event.participant.EventParticipantListCommand
import tech.coner.trailer.cli.command.event.run.EventRunCommand
import tech.coner.trailer.cli.command.event.run.EventRunListCommand
import tech.coner.trailer.cli.command.eventpointscalculator.*
import tech.coner.trailer.cli.command.motorsportreg.*
import tech.coner.trailer.cli.command.person.*
import tech.coner.trailer.cli.command.policy.*
import tech.coner.trailer.cli.command.rankingsort.*
import tech.coner.trailer.cli.command.season.*
import tech.coner.trailer.cli.command.seasonpointscalculator.*
import tech.coner.trailer.cli.command.webapp.WebappCommand
import tech.coner.trailer.cli.command.webapp.WebappCompetitionCommand
import tech.coner.trailer.cli.di.cliServiceModule
import tech.coner.trailer.cli.di.cliktModule
import tech.coner.trailer.cli.di.utilityModule
import tech.coner.trailer.cli.di.viewModule
import tech.coner.trailer.cli.service.FeatureService
import tech.coner.trailer.di.*

object ConerTrailerCli {

    @JvmStatic
    fun main(args: Array<out String>) {
        createCommands().main(args)
    }

    fun createCommands(): RootCommand {
        val di = DI.from(listOf(
            eventResultsModule,
            viewModule,
            utilityModule,
            ioModule,
            constraintModule,
            mapperModule,
            serviceModule,
            snoozleModule,
            verifierModule,
            motorsportRegApiModule,
            cliServiceModule,
            cliktModule,
            allRendererModule
        ))
        val global = GlobalModel()
        val features = di.direct.instance<FeatureService>().get()
        return RootCommand(di, global).subcommands(
            ConfigCommand(di, global).subcommands(
                ConfigDatabaseCommand(di, global).subcommands(
                    ConfigDatabaseAddCommand(di, global),
                    ConfigDatabaseGetCommand(di, global),
                    ConfigDatabaseListCommand(di, global),
                    ConfigDatabaseSnoozleMigrateCommand(di, global),
                    ConfigDatabaseRemoveCommand(di, global),
                    ConfigDatabaseSetDefaultCommand(di, global),
                    ConfigDatabaseSnoozleCommand(di, global).subcommands(
                        ConfigDatabaseSnoozleInitializeCommand(di, global),
                        ConfigDatabaseSnoozleMigrateCommand(di, global)
                    )
                ),
                ConfigWebappCommand(di, global).subcommands(
                    ConfigWebappGetCommand(di, global),
                    ConfigWebappSetCommand(di, global),
                    ConfigWebappUnsetCommand(di, global)
                )
            ),
            ClubCommand(di, global).subcommands(
                ClubGetCommand(di, global),
                ClubSetCommand(di, global)
            ),
            PolicyCommand(di, global).subcommands(
                PolicyAddCommand(di, global),
                PolicyGetCommand(di, global),
                PolicyDeleteCommand(di, global),
                PolicyListCommand(di, global),
                PolicySetCommand(di, global)
            ),
            EventCommand(di, global).subcommands(
                EventAddCommand(di, global),
                EventCheckCommand(di, global),
                EventCrispyFishPersonMapAddCommand(di, global),
                EventCrispyFishPersonMapAssembleCommand(di, global),
                EventCrispyFishPersonMapRemoveCommand(di, global),
                EventDeleteCommand(di, global),
                EventGetCommand(di, global),
                EventListCommand(di, global),
                EventParticipantCommand(di, global).subcommands(
                    EventParticipantListCommand(di, global)
                ),
                EventResultsCommand(di, global),
                EventRunCommand(di, global).subcommands(
                    EventRunListCommand(di, global)
                ),
                EventSetCommand(di, global),
            ),
            EventPointsCalculatorCommand(di, global).subcommands(
                EventPointsCalculatorAddCommand(di, global),
                EventPointsCalculatorDeleteCommand(di, global),
                EventPointsCalculatorGetCommand(di, global),
                EventPointsCalculatorListCommand(di, global),
                EventPointsCalculatorSetCommand(di, global)
            ),
            MotorsportRegCommand(di, global).subcommands(
                MotorsportRegMemberCommand().subcommands(
                    MotorsportRegMemberListCommand(di, global),
                    MotorsportRegMemberImportCommand(di, global),
                    MotorsportRegMemberImportSingleCommand(di, global)
                )
            ),
            PersonCommand(di, global).subcommands(
                PersonAddCommand(di, global),
                PersonDeleteCommand(di, global),
                PersonGetCommand(di, global),
                PersonListCommand(di, global),
                PersonSearchCommand(di, global),
                PersonSetCommand(di, global)
            ),
            RankingSortCommand(di, global).subcommands(
                RankingSortAddCommand(di, global),
                RankingSortDeleteCommand(di, global),
                RankingSortGetCommand(di, global),
                RankingSortListCommand(di, global),
                RankingSortSetCommand(di, global),
                RankingSortStepsAppendCommand(di, global)
            ),
            SeasonCommand(di, global).subcommands(
                SeasonAddCommand(di, global),
                SeasonDeleteCommand(di, global),
                SeasonGetCommand(di, global),
                SeasonListCommand(di, global),
                SeasonSetCommand(di, global)
            ),
            SeasonPointsCalculatorCommand(di, global).subcommands(
                SeasonPointsCalculatorAddCommand(di, global),
                SeasonPointsCalculatorDeleteCommand(di, global),
                SeasonPointsCalculatorGetCommand(di, global),
                SeasonPointsCalculatorListCommand(di, global),
                SeasonPointsCalculatorSetCommand(di, global)
            ),
        )
            .apply {
                if (features.contains(Feature.WEBAPP)) {
                    subcommands(
                        WebappCommand(di, global).subcommands(
                            WebappCompetitionCommand(di, global)
                        )
                    )
                }
            }
    }
}