package tech.coner.trailer.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.io.service.ClassService
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.CrispyFishParticipantService
import tech.coner.trailer.io.service.CrispyFishRunService
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventExtendedParametersService
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.io.service.RunService
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.io.service.SeasonService
import tech.coner.trailer.io.util.SimpleCache

val serviceModule = DI.Module("tech.coner.trailer.io.service") {
    bind {
        scoped(DataSessionScope).singleton {
            ClubService(
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
    bind {
        scoped(DataSessionScope).singleton {
            RankingSortService(
                resource = instance(),
                mapper = instance(),
                persistConstraints = instance()
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
                cache = SimpleCache(),
                crispyFishDatabase = context.environment.requireDatabaseConfiguration().crispyFishDatabase,
                loadConstraints = instance()
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
            CrispyFishClassService(
                crispyFishRoot = context.environment.requireDatabaseConfiguration().crispyFishDatabase.toFile(),
                classMapper = instance(),
                classParentMapper = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton {
        ClassService(
            crispyFishClassService = instance()
        )
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