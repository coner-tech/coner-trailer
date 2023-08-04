package tech.coner.trailer.cli.di.command

import com.github.ajalt.clikt.core.subcommands
import org.kodein.di.*
import tech.coner.trailer.cli.Feature
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.command.RootCommand
import tech.coner.trailer.cli.command.club.ClubCommand
import tech.coner.trailer.cli.command.club.ClubGetCommand
import tech.coner.trailer.cli.command.club.ClubSetCommand
import tech.coner.trailer.cli.command.config.*
import tech.coner.trailer.cli.command.event.*
import tech.coner.trailer.cli.command.event.crispyfish.EventCrispyFishCommand
import tech.coner.trailer.cli.command.event.crispyfish.EventCrispyFishPersonMapAddCommand
import tech.coner.trailer.cli.command.event.crispyfish.EventCrispyFishPersonMapAssembleCommand
import tech.coner.trailer.cli.command.event.crispyfish.EventCrispyFishPersonMapRemoveCommand
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
import tech.coner.trailer.cli.di.Invocation
import tech.coner.trailer.cli.di.InvocationScope
import tech.coner.trailer.cli.service.FeatureService

val commandModule = DI.Module("tech.coner.trailer.cli.command") {
    bindProvider { Invocation() }
    bind {
        scoped(InvocationScope).singleton {
            GlobalModel(invocation = context)
        }
    }
    
    // Club
    bind {
        scoped(InvocationScope).singleton {
            new(::ClubCommand)
                .subcommands(
                    instance<ClubGetCommand>(),
                    instance<ClubSetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::ClubGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ClubSetCommand) } }

    // Config
    bind {
        scoped(InvocationScope).singleton {
            new(::ConfigCommand)
                .subcommands(
                    instance<ConfigDatabaseCommand>(),
                    instance<ConfigWebappCommand>()
                )
        }
    }

    // Config Database
    bind {
        scoped(InvocationScope).singleton {
            new(::ConfigDatabaseCommand)
                .subcommands(
                    instance<ConfigDatabaseAddCommand>(),
                    instance<ConfigDatabaseGetCommand>(),
                    instance<ConfigDatabaseListCommand>(),
                    instance<ConfigDatabaseRemoveCommand>(),
                    instance<ConfigDatabaseSetDefaultCommand>(),
                    instance<ConfigDatabaseSnoozleCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::ConfigDatabaseAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ConfigDatabaseGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ConfigDatabaseListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ConfigDatabaseRemoveCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ConfigDatabaseSetDefaultCommand) } }

    // Config Database Snoozle
    bind {
        scoped(InvocationScope).singleton {
            new(::ConfigDatabaseSnoozleCommand)
                .subcommands(
                    instance<ConfigDatabaseSnoozleInitializeCommand>(),
                    instance<ConfigDatabaseSnoozleMigrateCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::ConfigDatabaseSnoozleInitializeCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ConfigDatabaseSnoozleMigrateCommand) } }


    // Config Webapp
    bind {
        scoped(InvocationScope).singleton {
            new(::ConfigWebappCommand)
                .subcommands(
                    instance<ConfigWebappGetCommand>(),
                    instance<ConfigWebappSetCommand>(),
                    instance<ConfigWebappUnsetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::ConfigWebappGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ConfigWebappSetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::ConfigWebappUnsetCommand) } }

    // Event
    bind {
        scoped(InvocationScope).singleton {
            new(::EventCommand)
                .subcommands(
                    instance<EventAddCommand>(),
                    instance<EventCheckCommand>(),
                    instance<EventCrispyFishCommand>(),
                    instance<EventDeleteCommand>(),
                    instance<EventGetCommand>(),
                    instance<EventListCommand>(),
                    instance<EventParticipantCommand>(),
                    instance<EventResultsCommand>(),
                    instance<EventRunCommand>(),
                    instance<EventSetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::EventAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventCheckCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventDeleteCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventResultsCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventSetCommand) } }

    // Event Crispy Fish
    bind {
        scoped(InvocationScope).singleton {
            new(::EventCrispyFishCommand)
                .subcommands(
                    instance<EventCrispyFishPersonMapAddCommand>(),
                    instance<EventCrispyFishPersonMapAssembleCommand>(),
                    instance<EventCrispyFishPersonMapRemoveCommand>(),
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::EventCrispyFishPersonMapAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventCrispyFishPersonMapAssembleCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventCrispyFishPersonMapRemoveCommand) } }

    // Event Participant
    bind {
        scoped(InvocationScope).singleton {
            new(::EventParticipantCommand)
                .subcommands(
                    instance<EventParticipantListCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::EventParticipantListCommand) } }

    // Event Points Calculator
    bind {
        scoped(InvocationScope).singleton {
            new(::EventPointsCalculatorCommand)
                .subcommands(
                    instance<EventPointsCalculatorAddCommand>(),
                    instance<EventPointsCalculatorDeleteCommand>(),
                    instance<EventPointsCalculatorGetCommand>(),
                    instance<EventPointsCalculatorListCommand>(),
                    instance<EventPointsCalculatorSetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::EventPointsCalculatorAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventPointsCalculatorDeleteCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventPointsCalculatorGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventPointsCalculatorListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::EventPointsCalculatorSetCommand) } }

    // Event Run
    bind {
        scoped(InvocationScope).singleton {
            new(::EventRunCommand)
                .subcommands(
                    instance<EventRunListCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::EventRunListCommand) } }

    // Policy
    bind {
        scoped(InvocationScope).singleton {
            new(::PolicyCommand)
                .subcommands(
                    instance<PolicyAddCommand>(),
                    instance<PolicyDeleteCommand>(),
                    instance<PolicyGetCommand>(),
                    instance<PolicyListCommand>(),
                    instance<PolicySetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::PolicyAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PolicyDeleteCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PolicyGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PolicyListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PolicySetCommand) } }

    // MotorsportReg
    bind {
        scoped(InvocationScope).singleton {
            new(::MotorsportRegCommand)
                .subcommands(
                    instance<MotorsportRegMemberCommand>()
                )
        }
    }

    // MotorsportReg Member
    bind {
        scoped(InvocationScope).singleton {
            new(::MotorsportRegMemberCommand)
                .subcommands(
                    instance<MotorsportRegMemberListCommand>(),
                    instance<MotorsportRegMemberImportCommand>(),
                    instance<MotorsportRegMemberImportSingleCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::MotorsportRegMemberListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::MotorsportRegMemberImportCommand) } }
    bind { scoped(InvocationScope).singleton { new(::MotorsportRegMemberImportSingleCommand) } }

    // Person
    bind {
        scoped(InvocationScope).singleton {
            new(::PersonCommand)
                .subcommands(
                    instance<PersonAddCommand>(),
                    instance<PersonDeleteCommand>(),
                    instance<PersonGetCommand>(),
                    instance<PersonListCommand>(),
                    instance<PersonSearchCommand>(),
                    instance<PersonSetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::PersonAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PersonDeleteCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PersonGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PersonListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PersonSearchCommand) } }
    bind { scoped(InvocationScope).singleton { new(::PersonSetCommand) } }

    // Ranking Sort
    bind {
        scoped(InvocationScope).singleton {
            new(::RankingSortCommand)
                .subcommands(
                    instance<RankingSortAddCommand>(),
                    instance<RankingSortDeleteCommand>(),
                    instance<RankingSortGetCommand>(),
                    instance<RankingSortListCommand>(),
                    instance<RankingSortSetCommand>(),
                    instance<RankingSortStepsAppendCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::RankingSortAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::RankingSortDeleteCommand) } }
    bind { scoped(InvocationScope).singleton { new(::RankingSortGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::RankingSortListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::RankingSortSetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::RankingSortStepsAppendCommand) } }

    // Root
    bind {
        scoped(InvocationScope).singleton {
            new(::RootCommand)
                .subcommands(
                    buildList {
                        val features = instance<FeatureService>().get()
                        add(instance<ConfigCommand>())
                        add(instance<ClubCommand>())
                        add(instance<EventCommand>())
                        add(instance<EventPointsCalculatorCommand>())
                        add(instance<MotorsportRegCommand>())
                        add(instance<PersonCommand>())
                        add(instance<PolicyCommand>())
                        add(instance<RankingSortCommand>())
                        add(instance<SeasonCommand>())
                        add(instance<SeasonPointsCalculatorCommand>())
                        if (features.contains(Feature.WEBAPP)) {
                            add(instance<WebappCommand>())
                        }
                    }
                )
        }
    }

    // Season
    bind {
        scoped(InvocationScope).singleton {
            new(::SeasonCommand)
                .subcommands(
                    instance<SeasonAddCommand>(),
                    instance<SeasonDeleteCommand>(),
                    instance<SeasonGetCommand>(),
                    instance<SeasonListCommand>(),
                    instance<SeasonSetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::SeasonAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonDeleteCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonSetCommand) } }
    
    // Season Points Calculator
    bind { 
        scoped(InvocationScope).singleton { 
            new(::SeasonPointsCalculatorCommand)
                .subcommands(
                    instance<SeasonPointsCalculatorAddCommand>(),
                    instance<SeasonPointsCalculatorDeleteCommand>(),
                    instance<SeasonPointsCalculatorGetCommand>(),
                    instance<SeasonPointsCalculatorListCommand>(),
                    instance<SeasonPointsCalculatorSetCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::SeasonPointsCalculatorAddCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonPointsCalculatorDeleteCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonPointsCalculatorGetCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonPointsCalculatorListCommand) } }
    bind { scoped(InvocationScope).singleton { new(::SeasonPointsCalculatorSetCommand) } }

    // Webapp
    bind {
        scoped(InvocationScope).singleton {
            new(::WebappCommand)
                .subcommands(
                    instance<WebappCompetitionCommand>()
                )
        }
    }
    bind { scoped(InvocationScope).singleton { new(::WebappCompetitionCommand) }}
}