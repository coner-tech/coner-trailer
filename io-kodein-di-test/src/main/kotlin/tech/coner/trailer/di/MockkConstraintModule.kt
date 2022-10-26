package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindSingleton
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

val mockkConstraintModule = DI.Module("mockk for coner.trailer.io.constraint") {
    bind<EventPointsCalculatorPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RankingSortPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPointsCalculatorConfigurationConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk(relaxed = true) }
    }
    bind<EventDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishLoadConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk(relaxed = true) }
    }
    bindSingleton { PortConstraints() }
}