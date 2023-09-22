package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.io.service.*
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier

val mockkServiceModule = DI.Module("mockk for coner.trailer.io.service") {
    bindMultiton<ConfigurationServiceArgument, ConfigurationService> { _ -> mockk() }

    bind<ClubService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPointsCalculatorService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RankingSortService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPointsCalculatorConfigurationService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventCrispyFishPersonMapVerifier> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishEventMappingContextService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishClassService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<ParticipantService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishParticipantService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RunService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishRunService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventContextService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
}