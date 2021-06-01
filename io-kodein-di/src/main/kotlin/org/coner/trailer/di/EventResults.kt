package org.coner.trailer.di

import org.coner.trailer.Policy
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.*
import org.coner.trailer.eventresults.*
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
    bind<RunScoreFactory>(StandardResultsTypes.grouped) with multiton { policy: Policy -> GroupedRunScoreFactory(
        rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
        paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
    ) }
    bind<FinalScoreFactory>(FinalScoreStyle.AUTOCROSS) with singleton { AutocrossFinalScoreFactory() }
    bind<FinalScoreFactory>(FinalScoreStyle.RALLYCROSS) with singleton { RallycrossFinalScoreFactory() }
    bind<ParticipantResultMapper>(StandardResultsTypes.raw) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = factory<Policy, RawTimeRunScoreFactory>().invoke(policy)
        ),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResultMapper>(StandardResultsTypes.pax) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = instance(),
            runEligibilityQualifier = instance(),
            runScoreFactory = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
        ),
        crispyFishParticipantMapper = instance(),
        crispyFishRunMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResultMapper>(StandardResultsTypes.grouped) with multiton { policy: Policy -> ParticipantResultMapper(
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
    ) }
    bind<ParticipantResult.ScoredRunsComparator>() with factory { runCount: Int -> ParticipantResult.ScoredRunsComparator(
        runCount = runCount
    ) }
    bind<OverallRawTimeResultsReportCreator>() with multiton { policy: Policy -> OverallRawTimeResultsReportCreator(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardResultsTypes.raw).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) }
    bind<OverallPaxTimeResultsReportCreator>() with multiton { policy: Policy -> OverallPaxTimeResultsReportCreator(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardResultsTypes.pax).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) }
    bind<GroupedResultsReportCreator>() with multiton { policy: Policy -> GroupedResultsReportCreator(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardResultsTypes.grouped).invoke(policy),
        scoredRunsComparatorProvider = factory()
    ) }

    bind<EventResultsReportFileNameGenerator>() with singleton { EventResultsReportFileNameGenerator() }
}