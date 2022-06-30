package tech.coner.trailer.di

import io.mockk.every
import io.mockk.mockk
import tech.coner.trailer.datasource.crispyfish.*
import tech.coner.trailer.datasource.snoozle.*
import tech.coner.trailer.io.constraint.*
import tech.coner.trailer.io.mapper.*
import tech.coner.trailer.io.service.*
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.snoozle.db.session.data.DataSession

fun mockkDatabaseModule() = DI.Module("mockk for coner.trailer.io.database") {
    bind<ConerTrailerDatabase> {
        scoped(EnvironmentScope).singleton { mockk() }
    }
    bind<DataSession> {
        scoped(DataSessionScope).singleton { mockk {
            every { close() } returns Result.success(Unit)
        } }
    }

    // Club
    bind<ClubResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<ClubMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<ClubService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // Event Points Calculators
    bind<EventPointsCalculatorResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPointsCalculatorMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPointsCalculatorPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPointsCalculatorService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // Ranking Sorts
    bind<RankingSortResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RankingSortMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RankingSortPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<RankingSortService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // SeasonPointsCalculatorConfigurations
    bind<SeasonPointsCalculatorConfigurationResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPointsCalculatorConfigurationMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPointsCalculatorConfigurationConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPointsCalculatorConfigurationService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // People
    bind<PersonResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PersonService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // Seasons
    bind<SeasonResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<SeasonService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // Events
    bind<EventResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk(relaxed = true) }
    }
    bind<EventDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishLoadConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishPersonMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishParticipantMapper> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<EventCrispyFishPersonMapVerifier> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishEventMappingContextService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // Policies
    bind<PolicyResource> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyMapper> {
        scoped(DataSessionScope).singleton { mockk() } }
    bind<PolicyService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyDeleteConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<PolicyPersistConstraints> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // Classing
    bind<CrispyFishClassService> {
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

    // Participants
    bind<ParticipantService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishParticipantService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

    // Runs
    bind<RunService> {
        scoped(DataSessionScope).singleton { mockk() }
    }
    bind<CrispyFishRunService> {
        scoped(DataSessionScope).singleton { mockk() }
    }

}