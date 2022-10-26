package tech.coner.trailer.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.on
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

val snoozleModule = DI.Module("tech.coner.trailer.datasource.snoozle") {
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
    bind<ClubResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind<EventPointsCalculatorResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind<RankingSortResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind<SeasonPointsCalculatorConfigurationResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind<PersonResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind<SeasonResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind<EventResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().entity() }
    }
    bind<PolicyResource> { scoped(DataSessionScope).singleton { instance<DataSession>().entity() } }
}