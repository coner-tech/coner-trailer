package tech.coner.trailer.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.kodein.di.*
import tech.coner.crispyfish.CrispyFish
import tech.coner.trailer.io.service.*
import tech.coner.trailer.io.util.SimpleCache

val serviceModule = DI.Module("tech.coner.trailer.io.service") {
    bind {
        scoped(DataSessionScope).singleton {
            new(::ClubService)
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            new(::EventPointsCalculatorService)
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            new(::RankingSortService)
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            new(::SeasonPointsCalculatorConfigurationService)
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            new(::PersonService)
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            new(::SeasonService)
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            EventService(
                coroutineScope = CoroutineScope(context.coroutineContext + Job()),
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
            ParticipantService(
                coroutineContext = context.coroutineContext + Job(),
                crispyFishParticipantService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishEventMappingContextService(
                coroutineContext = context.coroutineContext + Job(),
                nonActiveCache = SimpleCache(),
                crispyFishDatabase = context.environment.requireDatabaseConfiguration().crispyFishDatabase,
                loadConstraints = instance(),
                crispyFishClassDefinitionsFactory = CrispyFish.classDefinitions,
                crispyFishEventFactory = CrispyFish.event
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            new(::PolicyService)
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishClassService(
                crispyFishRoot = context.environment.requireDatabaseConfiguration().crispyFishDatabase,
                crispyFishClassDefinitionsFactory = CrispyFish.classDefinitions,
                classMapper = instance(),
                classParentMapper = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton {
        new(::ClassService)
    } }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishParticipantService(
                coroutineContext = context.coroutineContext + Job(),
                crispyFishEventMappingContextService = instance(),
                crispyFishClassService = instance(),
                crispyFishParticipantMapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            RunService(
                coroutineContext = context.coroutineContext + Job(),
                crispyFishRunService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishRunService(
                coroutineContext = context.coroutineContext + Job(),
                crispyFishEventMappingContextService = instance(),
                crispyFishClassService = instance(),
                crispyFishParticipantMapper = instance(),
                crispyFishRunMapper = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton {
        EventContextService(
            coroutineContext = context.coroutineContext + Job(),
            classService = instance(),
            participantService = instance(),
            runService = instance(),
            eventExtendedParametersService = instance()
        )
    } }
    bind { scoped(DataSessionScope).singleton {
        EventExtendedParametersService(
            coroutineContext = context.coroutineContext + Job(),
            crispyFishEventMappingContextService = instance()
        )
    } }

}