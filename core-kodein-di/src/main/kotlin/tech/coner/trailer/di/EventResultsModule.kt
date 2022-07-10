package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.Policy
import tech.coner.trailer.eventresults.*

private val privateDi = DI.direct {
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        ParticipantResult.ScoredRunsComparator(
            runCount = eventContext.extendedParameters.runsPerParticipant
        )
    } }
    bind { scoped(EventResultsSessionScope).singleton { RunEligibilityQualifier() } }
    bind { scoped(EventResultsSessionScope).multiton { policy: Policy ->
        StandardPenaltyFactory(policy)
    } }
    bind { scoped(EventResultsSessionScope).multiton { policy: Policy ->
        RawTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).multiton { policy: Policy ->
        PaxTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).multiton { policy: Policy ->
        ClazzRunScoreFactory(
            rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
            paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).multiton { policy: Policy ->
        when (policy.finalScoreStyle) {
            FinalScoreStyle.AUTOCROSS -> AutocrossFinalScoreFactory()
            FinalScoreStyle.RALLYCROSS -> RallycrossFinalScoreFactory()
        }
    } }
}

val eventResultsModule = DI.Module("tech.coner.trailer.eventresults") {
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        val privateContextDi = privateDi.on(context)
        RawEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparatorFactory = privateContextDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateContextDi.instance(),
            runScoreFactory = privateContextDi.factory<Policy, RawTimeRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateContextDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        val privateContextDi = privateDi.on(context)
        PaxEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = privateContextDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateContextDi.instance(),
            runScoreFactory = privateContextDi.factory<Policy, PaxTimeRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateContextDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        val privateContextDi = privateDi.on(context)
        ClazzEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = privateContextDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateContextDi.instance(),
            runScoreFactory = privateContextDi.factory<Policy, ClazzRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateContextDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        ComprehensiveEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculators = listOf(
                factory<EventContext, RawEventResultsCalculator>().invoke(eventContext),
                factory<EventContext, PaxEventResultsCalculator>().invoke(eventContext)
            ),
            groupEventResultsCalculator = factory<EventContext, ClazzEventResultsCalculator>().invoke(eventContext)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        IndividualEventResultsCalculator(
            eventContext = eventContext,
            comprehensiveEventResultsCalculator = factory<EventContext, ComprehensiveEventResultsCalculator>().invoke(eventContext)
        )
    } }
}