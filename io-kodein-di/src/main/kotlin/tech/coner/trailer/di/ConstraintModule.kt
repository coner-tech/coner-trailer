package tech.coner.trailer.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.io.constraint.CrispyFishLoadConstraints
import tech.coner.trailer.io.constraint.EventDeleteConstraints
import tech.coner.trailer.io.constraint.EventPersistConstraints
import tech.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import tech.coner.trailer.io.constraint.PersonDeleteConstraints
import tech.coner.trailer.io.constraint.PersonPersistConstraints
import tech.coner.trailer.io.constraint.PolicyDeleteConstraints
import tech.coner.trailer.io.constraint.PolicyPersistConstraints
import tech.coner.trailer.io.constraint.PortConstraints
import tech.coner.trailer.io.constraint.RankingSortPersistConstraints
import tech.coner.trailer.io.constraint.SeasonDeleteConstraints
import tech.coner.trailer.io.constraint.SeasonPersistConstraints
import tech.coner.trailer.io.constraint.SeasonPointsCalculatorConfigurationConstraints

val constraintModule = DI.Module("tech.coner.trailer.io.constraint") {
    bind {
        scoped(DataSessionScope).singleton {
            EventPointsCalculatorPersistConstraints(
                resource = instance(),
            )
        }
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
            SeasonPointsCalculatorConfigurationConstraints(
                resource = instance(),
                mapper = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { PersonPersistConstraints() } }
    bind { scoped(DataSessionScope).singleton { PersonDeleteConstraints() } }
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
            EventPersistConstraints(
                dbConfig = context.environment.requireDatabaseConfiguration(),
                resource = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { EventDeleteConstraints() } }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishLoadConstraints(
                crispyFishDatabase = context.environment.requireDatabaseConfiguration().crispyFishDatabase
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
    bind {
        scoped(DataSessionScope).singleton {
            PolicyDeleteConstraints(
                eventResource = instance()
            )
        }
    }
    bindSingleton { PortConstraints() }
}