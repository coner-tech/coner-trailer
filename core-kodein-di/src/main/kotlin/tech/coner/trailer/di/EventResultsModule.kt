package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.Policy
import tech.coner.trailer.eventresults.StandardPenaltyFactory
import tech.coner.trailer.eventresults.*

private val privateDi = DI.direct {
    bindFactory { eventContext: EventContext ->
        ParticipantResult.ScoredRunsComparator(
            runCount = eventContext.extendedParameters.runsPerParticipant
        )
    }
    bindSingleton {
        RunEligibilityQualifier()
    }
    bindMultiton { policy: Policy ->
        StandardPenaltyFactory(policy)
    }
    bindMultiton { policy: Policy ->
        RawTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
    }
    bindMultiton { policy: Policy ->
        PaxTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
    }
    bindMultiton { policy: Policy ->
        ClazzRunScoreFactory(
            rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
            paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
        )
    }
    bindMultiton { policy: Policy ->
        when (policy.finalScoreStyle) {
            FinalScoreStyle.AUTOCROSS -> AutocrossFinalScoreFactory()
            FinalScoreStyle.RALLYCROSS -> RallycrossFinalScoreFactory()
        }
    }
    TODO("Introduce an appropriate scope for event results. Multitons by policy might grow excessively and would be retained indefinitely")
}

val eventResultsModule = DI.Module("tech.coner.trailer.eventresults") {
    bindFactory { eventContext: EventContext ->
        RawEventResultsCalculator(
            scoredRunsComparatorFactory = privateDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateDi.instance(),
            runScoreFactory = privateDi.factory<Policy, RawTimeRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    }
    bindFactory { eventContext: EventContext ->
        PaxEventResultsCalculator(
            scoredRunsComparator = privateDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateDi.instance(),
            runScoreFactory = privateDi.factory<Policy, PaxTimeRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    }
    bindFactory { eventContext: EventContext ->
        ClazzEventResultsCalculator(
            scoredRunsComparator = privateDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateDi.instance(),
            runScoreFactory = privateDi.factory<EventContext, ClazzRunScoreFactory>().invoke(eventContext),
            finalScoreFactory = privateDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    }
    bindFactory { eventContext: EventContext ->
        ComprehensiveEventResultsCalculator(
            overallEventResultsCalculators = listOf(
                factory<EventContext, RawEventResultsCalculator>().invoke(eventContext),
                factory<EventContext, PaxEventResultsCalculator>().invoke(eventContext)
            ),
            groupEventResultsCalculator = factory<EventContext, ClazzEventResultsCalculator>().invoke(eventContext)
        )
    }
    bindFactory { eventContext: EventContext ->
        IndividualEventResultsCalculator(
            comprehensiveEventResultsCalculator = factory<EventContext, ComprehensiveEventResultsCalculator>().invoke(eventContext)
        )
    }
}