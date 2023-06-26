package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassParentMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import tech.coner.trailer.datasource.snoozle.ClubResource
import tech.coner.trailer.datasource.snoozle.EventPointsCalculatorResource
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.datasource.snoozle.PersonResource
import tech.coner.trailer.datasource.snoozle.PolicyResource
import tech.coner.trailer.datasource.snoozle.RankingSortResource
import tech.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import tech.coner.trailer.datasource.snoozle.SeasonResource
import tech.coner.trailer.io.mapper.ClubMapper
import tech.coner.trailer.io.mapper.EventMapper
import tech.coner.trailer.io.mapper.EventPointsCalculatorMapper
import tech.coner.trailer.io.mapper.PersonMapper
import tech.coner.trailer.io.mapper.PolicyMapper
import tech.coner.trailer.io.mapper.RankingSortMapper
import tech.coner.trailer.io.mapper.SeasonMapper
import tech.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.CrispyFishParticipantService
import tech.coner.trailer.io.service.CrispyFishRunService
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.io.service.RunService
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.io.service.SeasonService
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier

val mockkServiceModule = DI.Module("mockk for coner.trailer.io.service") {

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