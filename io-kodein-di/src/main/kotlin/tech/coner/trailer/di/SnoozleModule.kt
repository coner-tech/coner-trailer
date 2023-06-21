package tech.coner.trailer.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.on
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.snoozle.db.session.data.DataSession
import tech.coner.trailer.datasource.snoozle.*

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
        scoped(DataSessionScope).singleton { instance<DataSession>().clubs() }
    }
    bind<EventPointsCalculatorResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().eventPointsCalculators() }
    }
    bind<RankingSortResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().rankingSorts() }
    }
    bind<SeasonPointsCalculatorConfigurationResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().seasonPointsCalculators() }
    }
    bind<PersonResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().persons() }
    }
    bind<SeasonResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().seasons() }
    }
    bind<EventResource> {
        scoped(DataSessionScope).singleton { instance<DataSession>().events() }
    }
    bind<PolicyResource> { scoped(DataSessionScope).singleton { instance<DataSession>().policies() } }
}