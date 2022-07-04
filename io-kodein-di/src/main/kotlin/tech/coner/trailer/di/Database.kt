package tech.coner.trailer.di

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import org.kodein.di.*
import tech.coner.snoozle.db.session.data.DataSession
import tech.coner.trailer.datasource.crispyfish.*
import tech.coner.trailer.datasource.snoozle.*
import tech.coner.trailer.io.constraint.*
import tech.coner.trailer.io.mapper.*
import tech.coner.trailer.io.service.*
import tech.coner.trailer.io.util.LifetimeCache
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier
import tech.coner.trailer.io.verifier.RunWithInvalidSignageVerifier
import java.time.Duration

val databaseModule = DI.Module("coner.trailer.io.database") {
    bind {
        scoped(EnvironmentScope).singleton {
            ConerTrailerDatabase(
                root = context.requireDatabaseConfiguration().snoozleDatabase
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            on(context.environment).instance<ConerTrailerDatabase>()
                .openDataSession()
                .getOrThrow()
        }
    }

    // Club
    bind<ClubResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind {
        scoped(DataSessionScope).singleton { ClubMapper() }
    }
    bind {
        scoped(DataSessionScope).singleton {
            ClubService(
                resource = instance(),
                mapper = instance()
            )
        }
    }

    // Event Points Calculators
    bind<EventPointsCalculatorResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind {
        scoped(DataSessionScope).singleton { EventPointsCalculatorMapper() }
    }
    bind {
        scoped(DataSessionScope).singleton {
            EventPointsCalculatorPersistConstraints(
                resource = instance(),
                mapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            EventPointsCalculatorService(
                resource = instance(),
                mapper = instance(),
                persistConstraints = instance()
            )
        }
    }

    // Ranking Sorts
    bind<RankingSortResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind {
        scoped(DataSessionScope).singleton { RankingSortMapper() }
    }
    bind {
        scoped(DataSessionScope).singleton {
            RankingSortPersistConstraints(
                resource = instance(),
                mapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            RankingSortService(
                resource = instance(),
                mapper = instance(),
                persistConstraints = instance()
            )
        }
    }

    // SeasonPointsCalculatorConfigurations
    bind<SeasonPointsCalculatorConfigurationResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonPointsCalculatorConfigurationMapper(
                eventPointsCalculatorService = instance(),
                rankingSortService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonPointsCalculatorConfigurationConstraints(
                resource = instance(),
                mapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonPointsCalculatorConfigurationService(
                resource = instance(),
                mapper = instance(),
                constraints = instance()
            )
        }
    }

    // People
    bind<PersonResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind { scoped(DataSessionScope).singleton { PersonMapper() } }
    bind { scoped(DataSessionScope).singleton { PersonPersistConstraints() } }
    bind { scoped(DataSessionScope).singleton { PersonDeleteConstraints() } }
    bind {
        scoped(DataSessionScope).singleton {
            PersonService(
                persistConstraints = instance(),
                deleteConstraints = instance(),
                resource = instance(),
                mapper = instance()
            )
        }
    }

    // Seasons
    bind<SeasonResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonMapper(
                seasonPointsCalculatorConfigurationService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonPersistConstraints(
                resource = instance(),
                mapper = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { SeasonDeleteConstraints() } }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonService(
                resource = instance(),
                mapper = instance(),
                persistConstraints = instance(),
                deleteConstraints = instance()
            )
        }
    }

    // Events
    bind<EventResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind {
        scoped(DataSessionScope).singleton {
            EventMapper(
                dbConfig = context.environment.requireDatabaseConfiguration(),
                personService = instance(),
                crispyFishClassService = instance(),
                policyResource = instance(),
                policyMapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            EventPersistConstraints(
                dbConfig = context.environment.requireDatabaseConfiguration(),
                resource = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { EventDeleteConstraints() } }
    bind {
        scoped(DataSessionScope).singleton {
            EventService(
                dbConfig = context.environment.requireDatabaseConfiguration(),
                resource = instance(),
                mapper = instance(),
                persistConstraints = instance(),
                deleteConstraints = instance(),
                eventCrispyFishPersonMapVerifier = instance(),
                runWithInvalidSignageVerifier = instance(),
                crispyFishEventMappingContextService = instance(),
                runService = instance(),
                participantService = instance(),
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishLoadConstraints(
                crispyFishDatabase = context.environment.requireDatabaseConfiguration().crispyFishDatabase
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { CrispyFishPersonMapper() } }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishParticipantMapper(
                crispyFishClassingMapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            EventCrispyFishPersonMapVerifier(
                personService = instance(),
                crispyFishClassService = instance(),
                crispyFishClassingMapper = instance(),
                motorsportRegPeopleMapService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishEventMappingContextService(
                coroutineContext = context.coroutineContext + Job(),
                cache = LifetimeCache(Duration.ofSeconds(5)),
                crispyFishDatabase = context.environment.requireDatabaseConfiguration().crispyFishDatabase,
                loadConstraints = instance()
            )
        }
    }

    // Policies
    bind<PolicyResource> { scoped(DataSessionScope).singleton { instance<DataSession>().entity() } }
    bind {
        scoped(DataSessionScope).singleton {
            PolicyMapper(
                clubService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            PolicyService(
                persistConstraints = instance(),
                deleteConstraints = instance(),
                resource = instance(),
                mapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            PolicyDeleteConstraints(
                eventResource = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            PolicyPersistConstraints(
                resource = instance(),
                eventResource = instance(),
                eventMapper = instance()
            )
        }
    }

    // Classing
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishClassService(
                crispyFishRoot = context.environment.requireDatabaseConfiguration().crispyFishDatabase.toFile(),
                classMapper = instance(),
                classParentMapper = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { CrispyFishClassParentMapper() } }
    bind { scoped(DataSessionScope).singleton { CrispyFishClassMapper() } }
    bind { scoped(DataSessionScope).singleton { CrispyFishClassingMapper() } }

    // Participants
    bind {
        scoped(DataSessionScope).singleton {
            ParticipantService(
                crispyFishParticipantService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishParticipantService(
                crispyFishEventMappingContextService = instance(),
                crispyFishClassService = instance(),
                crispyFishParticipantMapper = instance()
            )
        }
    }

    // Runs
    bind {
        scoped(DataSessionScope).singleton {
            RunService(
                crispyFishRunService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishRunService(
                crispyFishEventMappingContextService = instance(),
                crispyFishClassService = instance(),
                crispyFishParticipantMapper = instance(),
                crispyFishRunMapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            RunWithInvalidSignageVerifier()
        }
    }
}