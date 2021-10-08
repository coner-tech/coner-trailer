package org.coner.trailer.di

import org.coner.trailer.Policy
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.datasource.crispyfish.eventresults.*
import org.coner.trailer.eventresults.*
import org.coner.trailer.io.service.CrispyFishEventResultsServiceImpl
import org.coner.trailer.io.service.EventResultsServiceImpl
import org.kodein.di.*

val eventResultsModule = DI.Module("coner.trailer.io.eventResults") {

    // Event Results
    bind<StandardPenaltyFactory>() with multiton { policy: Policy -> StandardPenaltyFactory(policy) }
    bind<RawTimeRunScoreFactory>() with multiton { policy: Policy -> RawTimeRunScoreFactory(
        penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
    ) }
    bind<PaxTimeRunScoreFactory>() with multiton { policy: Policy -> when (policy.paxTimeStyle) {
        PaxTimeStyle.FAIR -> PaxTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
        PaxTimeStyle.LEGACY_BUGGED -> LegacyBuggedPaxTimeRunScoreFactory(
            penaltyFactory = factory<Policy, StandardPenaltyFactory>().invoke(policy)
        )
    } }
    bind<CrispyFishRunMapper>() with singleton { CrispyFishRunMapper() }
    bind<RunEligibilityQualifier>() with singleton { RunEligibilityQualifier() }
    bind<RunScoreFactory>(StandardEventResultsTypes.clazz) with multiton { policy: Policy -> GroupedRunScoreFactory(
        rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
        paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
    ) }
    bind<FinalScoreFactory>(FinalScoreStyle.AUTOCROSS) with singleton { AutocrossFinalScoreFactory() }
    bind<FinalScoreFactory>(FinalScoreStyle.RALLYCROSS) with singleton { RallycrossFinalScoreFactory() }
    bind<ParticipantResultMapper>(StandardEventResultsTypes.raw) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = factory<Policy, RawTimeRunScoreFactory>().invoke(policy)
        ),
        crispyFishClassingMapper = instance(),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResultMapper>(StandardEventResultsTypes.pax) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
        ),
        crispyFishClassingMapper = instance(),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResultMapper>(StandardEventResultsTypes.clazz) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = GroupedRunScoreFactory(
                rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
                paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
            )
        ),
        crispyFishClassingMapper = instance(),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResult.ScoredRunsComparator>() with factory { runCount: Int -> ParticipantResult.ScoredRunsComparator(
        runCount = runCount
    ) }
    bind<CrispyFishOverallEventResultsFactory>(StandardEventResultsTypes.raw) with multiton { policy: Policy -> OverallRawEventResultsFactory(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.raw).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) }
    bind<CrispyFishOverallEventResultsFactory>(StandardEventResultsTypes.pax) with multiton { policy: Policy -> OverallPaxEventResultsFactory(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.pax).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) }
    bind<EventResultsServiceImpl>() with singleton { EventResultsServiceImpl(
        crispyFishEventResultsService = CrispyFishEventResultsServiceImpl(
            crispyFishClassService = instance(),
            crispyFishEventMappingContextService = instance(),
            overallRawEventResultsFactory = factory(StandardEventResultsTypes.raw),
            overallPaxEventResultsFactory = factory(StandardEventResultsTypes.pax),
            groupEventResultsFactory = factory(),
            individualEventResultsFactory = instance()
        )
    ) }
    bind<GroupedEventResultsFactory>() with multiton { policy: Policy -> GroupedEventResultsFactory(
        groupParticipantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.clazz).invoke(policy),
        rawTimeParticipantResultMapper = factory<Policy, ParticipantResultMapper>(StandardEventResultsTypes.raw).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) }
    bind<IndividualEventResultsFactory>() with singleton { IndividualEventResultsFactory() }

    bind<EventResultsFileNameGenerator>() with singleton { EventResultsFileNameGenerator() }
}