package tech.coner.trailer.di

import io.mockk.every
import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.snoozle.db.session.data.DataSession
import tech.coner.trailer.datasource.snoozle.ClubResource
import tech.coner.trailer.datasource.snoozle.ConerTrailerDatabase
import tech.coner.trailer.datasource.snoozle.EventPointsCalculatorResource
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.datasource.snoozle.PersonResource
import tech.coner.trailer.datasource.snoozle.PolicyResource
import tech.coner.trailer.datasource.snoozle.RankingSortResource
import tech.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import tech.coner.trailer.datasource.snoozle.SeasonResource

val mockkSnoozleModule = DI.Module("mockk for coner.trailer.datasource.snoozle") {
    bind<ConerTrailerDatabase> {
        scoped(EnvironmentScope).singleton { mockk() }
    }
    bind<DataSession> {
        scoped(DataSessionScope).singleton { mockk {
            every { close() } returns Result.success(Unit)
        } }
    }
    bind<ClubResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPointsCalculatorResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RankingSortResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPointsCalculatorConfigurationResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
}