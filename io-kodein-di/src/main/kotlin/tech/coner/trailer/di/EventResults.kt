package tech.coner.trailer.di

import tech.coner.trailer.Policy
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import tech.coner.trailer.datasource.crispyfish.eventresults.*
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.io.service.CrispyFishEventResultsServiceImpl
import tech.coner.trailer.io.service.EventResultsServiceImpl
import org.kodein.di.*
import tech.coner.trailer.StandardPenaltyFactory

val eventResultsModule = DI.Module("coner.trailer.io.eventResults") {

    // Event Results
    bind { scoped(DataSessionScope).multiton { policy: Policy -> StandardPenaltyFactory(policy) } }
    bind { scoped(DataSessionScope).multiton { policy: Policy -> RawTimeRunScoreFactory(
        penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
    ) } }
    bind { scoped(DataSessionScope).multiton { policy: Policy -> when (policy.paxTimeStyle) {
        PaxTimeStyle.FAIR -> PaxTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
        PaxTimeStyle.LEGACY_BUGGED -> LegacyBuggedPaxTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
    } } }
    bind { scoped(DataSessionScope).singleton { CrispyFishRunMapper() } }
    bind { scoped(DataSessionScope).singleton { RunEligibilityQualifier() } }
    bind<RunScoreFactory>(StandardEventResultsTypes.clazz) with multiton { policy: Policy -> GroupedRunScoreFactory(
        rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
        paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
    ) }
    bind<FinalScoreFactory>(FinalScoreStyle.AUTOCROSS) with singleton { AutocrossFinalScoreFactory() }
    bind<FinalScoreFactory>(FinalScoreStyle.RALLYCROSS) with singleton { RallycrossFinalScoreFactory() }
    bind(StandardEventResultsTypes.raw) { scoped(DataSessionScope).multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = factory<Policy, RawTimeRunScoreFactory>().invoke(policy)
        ),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) } }
    bind(StandardEventResultsTypes.pax) { scoped(DataSessionScope).multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
        ),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) } }
    bind(StandardEventResultsTypes.clazz) { scoped(DataSessionScope).multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = GroupedRunScoreFactory(
                rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
                paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
            )
        ),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) } }
    bind { scoped(DataSessionScope).factory { runCount: Int -> ParticipantResult.ScoredRunsComparator(
        runCount = runCount
    ) } }
    bind<CrispyFishOverallEventResultsFactory>(StandardEventResultsTypes.raw) { scoped(DataSessionScope).multiton { policy: Policy -> OverallRawEventResultsFactory(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.raw).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) } }
    bind<CrispyFishOverallEventResultsFactory>(StandardEventResultsTypes.pax) { scoped(DataSessionScope).multiton { policy: Policy -> OverallPaxEventResultsFactory(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.pax).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) } }
    bind<EventResultsService> { scoped(DataSessionScope).singleton { EventResultsServiceImpl(
        crispyFishEventResultsService = CrispyFishEventResultsServiceImpl(
            crispyFishClassService = instance(),
            crispyFishEventMappingContextService = instance(),
            overallRawEventResultsFactory = factory(StandardEventResultsTypes.raw),
            overallPaxEventResultsFactory = factory(StandardEventResultsTypes.pax),
            groupEventResultsFactory = factory()
        )
    ) } }
    bind { scoped(DataSessionScope).singleton { ComprehensiveEventResultsService(
        eventResultsService = instance(),
    ) } }
    bind { scoped(DataSessionScope).singleton { IndividualEventResultsService(
        comprehensiveEventResultsService = instance(),
        individualEventResultsFactory = instance()
    ) } }
    bind { scoped(DataSessionScope).multiton { policy: Policy -> GroupedEventResultsFactory(
        groupParticipantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.clazz).invoke(policy),
        rawTimeParticipantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.raw).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) } }
    bind { scoped(DataSessionScope).singleton { IndividualEventResultsFactory() } }
}