package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.Policy
import tech.coner.trailer.eventresults.*

internal val internalEventResultsModule = DI.direct {
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
        when (policy.paxTimeStyle) {
            PaxTimeStyle.FAIR -> PaxTimeRunScoreFactory(
                penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
            )
            PaxTimeStyle.LEGACY_BUGGED -> LegacyBuggedPaxTimeRunScoreFactory(
                penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
            )
        }
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
        val privateContextDi = internalEventResultsModule.on(context)
        RawEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = privateContextDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateContextDi.instance(),
            runScoreFactory = privateContextDi.factory<Policy, RawTimeRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateContextDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        val privateContextDi = internalEventResultsModule.on(context)
        PaxEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = privateContextDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateContextDi.instance(),
            runScoreFactory = privateContextDi.factory<Policy, PaxTimeRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateContextDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        val privateContextDi = internalEventResultsModule.on(context)
        ClazzEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparator = privateContextDi.factory<EventContext, ParticipantResult.ScoredRunsComparator>().invoke(eventContext),
            runEligibilityQualifier = privateContextDi.instance(),
            runScoreFactory = privateContextDi.factory<Policy, ClazzRunScoreFactory>().invoke(eventContext.event.policy),
            finalScoreFactory = privateContextDi.factory<Policy, FinalScoreFactory>().invoke(eventContext.event.policy)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        TopTimesEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculator = when (eventContext.event.policy.topTimesEventResultsMethod) {
                StandardEventResultsTypes.raw -> factory<EventContext, RawEventResultsCalculator>().invoke(eventContext)
                StandardEventResultsTypes.pax -> factory<EventContext, PaxEventResultsCalculator>().invoke(eventContext)
                else -> throw IllegalArgumentException("Unrecognized topTimeStyle for event policy")
            }
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        ComprehensiveEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculators = listOf(
                factory<EventContext, RawEventResultsCalculator>().invoke(eventContext),
                factory<EventContext, PaxEventResultsCalculator>().invoke(eventContext)
            ),
            clazzEventResultsCalculator = factory<EventContext, ClazzEventResultsCalculator>().invoke(eventContext),
            topTimesEventResultsCalculator = factory<EventContext, TopTimesEventResultsCalculator>().invoke(eventContext),
            individualEventResultsCalculator = factory<EventContext, IndividualEventResultsCalculator>().invoke(eventContext)
        )
    } }
    bind { scoped(EventResultsSessionScope).factory { eventContext: EventContext ->
        IndividualEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculators = listOf(
                factory<EventContext, RawEventResultsCalculator>().invoke(eventContext),
                factory<EventContext, PaxEventResultsCalculator>().invoke(eventContext)
            ),
            clazzEventResultsCalculator = factory<EventContext, ClazzEventResultsCalculator>().invoke(eventContext)
        )
    } }
}