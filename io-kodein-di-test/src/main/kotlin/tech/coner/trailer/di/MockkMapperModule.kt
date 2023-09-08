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
import tech.coner.trailer.io.mapper.ClubMapper
import tech.coner.trailer.io.mapper.EventMapper
import tech.coner.trailer.io.mapper.EventPointsCalculatorMapper
import tech.coner.trailer.io.mapper.PersonMapper
import tech.coner.trailer.io.mapper.PolicyMapper
import tech.coner.trailer.io.mapper.RankingSortMapper
import tech.coner.trailer.io.mapper.SeasonMapper
import tech.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper

val mockkMapperModule = DI.Module("tech.coner.trailer.io.mapper mockk") {
    bind<ClubMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPointsCalculatorMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RankingSortMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPointsCalculatorConfigurationMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishPersonMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishParticipantMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishClassParentMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishClassMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishClassingMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
}