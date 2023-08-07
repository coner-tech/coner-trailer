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
import tech.coner.trailer.cli.command.config.ConfigCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseAddCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseGetCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseListCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseRemoveCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseSetDefaultCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseSnoozleCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseSnoozleInitializeCommand
import tech.coner.trailer.cli.command.config.ConfigDatabaseSnoozleMigrateCommand
import tech.coner.trailer.cli.command.config.ConfigWebappCommand
import tech.coner.trailer.cli.command.config.ConfigWebappGetCommand
import tech.coner.trailer.cli.command.config.ConfigWebappSetCommand
import tech.coner.trailer.cli.command.config.ConfigWebappUnsetCommand
import tech.coner.trailer.cli.command.event.EventAddCommand
import tech.coner.trailer.cli.command.event.EventCheckCommand
import tech.coner.trailer.cli.command.event.EventCommand
import tech.coner.trailer.cli.command.event.EventCrispyFishPersonMapAddCommand
import tech.coner.trailer.cli.command.event.EventCrispyFishPersonMapAssembleCommand
import tech.coner.trailer.cli.command.event.EventCrispyFishPersonMapRemoveCommand
import tech.coner.trailer.cli.command.event.EventDeleteCommand
import tech.coner.trailer.cli.command.event.EventGetCommand
import tech.coner.trailer.cli.command.event.EventListCommand
import tech.coner.trailer.cli.command.event.EventResultsCommand
import tech.coner.trailer.cli.command.event.EventSetCommand
import tech.coner.trailer.cli.command.event.participant.EventParticipantCommand
import tech.coner.trailer.cli.command.event.participant.EventParticipantListCommand
import tech.coner.trailer.cli.command.event.run.EventRunCommand
import tech.coner.trailer.cli.command.event.run.EventRunLatestCommand
import tech.coner.trailer.cli.command.event.run.EventRunListCommand
import tech.coner.trailer.cli.command.eventpointscalculator.EventPointsCalculatorAddCommand
import tech.coner.trailer.cli.command.eventpointscalculator.EventPointsCalculatorCommand
import tech.coner.trailer.cli.command.eventpointscalculator.EventPointsCalculatorDeleteCommand
import tech.coner.trailer.cli.command.eventpointscalculator.EventPointsCalculatorGetCommand
import tech.coner.trailer.cli.command.eventpointscalculator.EventPointsCalculatorListCommand
import tech.coner.trailer.cli.command.eventpointscalculator.EventPointsCalculatorSetCommand
import tech.coner.trailer.cli.command.motorsportreg.MotorsportRegCommand
import tech.coner.trailer.cli.command.motorsportreg.MotorsportRegMemberCommand
import tech.coner.trailer.cli.command.motorsportreg.MotorsportRegMemberImportCommand
import tech.coner.trailer.cli.command.motorsportreg.MotorsportRegMemberImportSingleCommand
import tech.coner.trailer.cli.command.motorsportreg.MotorsportRegMemberListCommand
import tech.coner.trailer.cli.command.person.PersonAddCommand
import tech.coner.trailer.cli.command.person.PersonCommand
import tech.coner.trailer.cli.command.person.PersonDeleteCommand
import tech.coner.trailer.cli.command.person.PersonGetCommand
import tech.coner.trailer.cli.command.person.PersonListCommand
import tech.coner.trailer.cli.command.person.PersonSearchCommand
import tech.coner.trailer.cli.command.person.PersonSetCommand
import tech.coner.trailer.cli.command.policy.PolicyAddCommand
import tech.coner.trailer.cli.command.policy.PolicyCommand
import tech.coner.trailer.cli.command.policy.PolicyDeleteCommand
import tech.coner.trailer.cli.command.policy.PolicyGetCommand
import tech.coner.trailer.cli.command.policy.PolicyListCommand
import tech.coner.trailer.cli.command.policy.PolicySetCommand
import tech.coner.trailer.cli.command.rankingsort.RankingSortAddCommand
import tech.coner.trailer.cli.command.rankingsort.RankingSortCommand
import tech.coner.trailer.cli.command.rankingsort.RankingSortDeleteCommand
import tech.coner.trailer.cli.command.rankingsort.RankingSortGetCommand
import tech.coner.trailer.cli.command.rankingsort.RankingSortListCommand
import tech.coner.trailer.cli.command.rankingsort.RankingSortSetCommand
import tech.coner.trailer.cli.command.rankingsort.RankingSortStepsAppendCommand
import tech.coner.trailer.cli.command.season.SeasonAddCommand
import tech.coner.trailer.cli.command.season.SeasonCommand
import tech.coner.trailer.cli.command.season.SeasonDeleteCommand
import tech.coner.trailer.cli.command.season.SeasonGetCommand
import tech.coner.trailer.cli.command.season.SeasonListCommand
import tech.coner.trailer.cli.command.season.SeasonSetCommand
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorAddCommand
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorCommand
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorDeleteCommand
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorGetCommand
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorListCommand
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorSetCommand
import tech.coner.trailer.cli.command.webapp.WebappCommand
import tech.coner.trailer.cli.command.webapp.WebappCompetitionCommand
import tech.coner.trailer.cli.di.cliServiceModule
import tech.coner.trailer.cli.di.cliktModule
import tech.coner.trailer.cli.di.mordantModule
import tech.coner.trailer.cli.di.utilityModule
import tech.coner.trailer.cli.di.viewModule
import tech.coner.trailer.cli.service.FeatureService
import tech.coner.trailer.di.allRendererModule
import tech.coner.trailer.di.constraintModule
import tech.coner.trailer.di.eventResultsModule
import tech.coner.trailer.di.ioModule
import tech.coner.trailer.di.mapperModule
import tech.coner.trailer.di.motorsportRegApiModule
import tech.coner.trailer.di.serviceModule
import tech.coner.trailer.di.snoozleModule
import tech.coner.trailer.di.verifierModule

object ConerTrailerCli {

    @JvmStatic
    fun main(args: Array<out String>) {
        createCommands().main(args)
    }

    fun createCommands(): RootCommand {
        val di = DI.from(listOf(
            eventResultsModule,
            viewModule,
            mordantModule,
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
                    EventRunListCommand(di, global),
                    EventRunLatestCommand(di, global)
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