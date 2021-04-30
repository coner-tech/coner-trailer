package org.coner.trailer.di

import org.coner.trailer.Policy
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
    bind<ScoreMapper>(StandardResultsTypes.raw) with multiton { policy: Policy -> ScoreMapper(
        runScoreFactory = factory<Policy, RawTimeRunScoreFactory>().invoke(policy)
    ) }
    bind<ScoreMapper>(StandardResultsTypes.pax) with multiton { policy: Policy -> ScoreMapper(
        runScoreFactory = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
    ) }
    bind<ScoreMapper>(StandardResultsTypes.grouped) with multiton { policy: Policy -> ScoreMapper(
        runScoreFactory = GroupedRunScoreFactory(
            rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
            paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
        )
    ) }
    bind<FinalScoreFactory>(FinalScoreStyle.AUTOCROSS) with singleton { AutocrossFinalScoreFactory() }
    bind<FinalScoreFactory>(FinalScoreStyle.RALLYCROSS) with singleton { RallycrossFinalScoreFactory() }
    bind<ParticipantResultMapper>(StandardResultsTypes.raw) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            scoreMapper = factory<Policy, ScoreMapper>(StandardResultsTypes.raw).invoke(policy)
        ),
        crispyFishParticipantMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResultMapper>(StandardResultsTypes.pax) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            scoreMapper = factory<Policy, ScoreMapper>(StandardResultsTypes.pax).invoke(policy)
        ),
        crispyFishParticipantMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResultMapper>(StandardResultsTypes.grouped) with multiton { policy: Policy -> ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            scoreMapper = ScoreMapper(
                runScoreFactory = GroupedRunScoreFactory(
                    rawTimes = factory<Policy, RawTimeRunScoreFactory>().invoke(policy),
                    paxTimes = factory<Policy, PaxTimeRunScoreFactory>().invoke(policy)
                )
            )
        ),
        crispyFishParticipantMapper = instance(),
        finalScoreFactory = instance(FinalScoreStyle.AUTOCROSS)
    ) }
    bind<ParticipantResult.ScoredRunsComparator>() with factory {  ParticipantResult.ScoredRunsComparator(
        runCount =
    ) }
    bind<OverallRawTimeResultsReportCreator>() with multiton { policy: Policy -> OverallRawTimeResultsReportCreator(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardResultsTypes.raw).invoke(policy),
        scoredRunsComparatorProvider = provider()
    ) }
    bind<OverallPaxTimeResultsReportCreator>() with multiton { policy: Policy -> OverallPaxTimeResultsReportCreator(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardResultsTypes.pax).invoke(policy)
    ) }
    bind<CompetitionGroupedResultsReportCreator>() with multiton { policy: Policy -> CompetitionGroupedResultsReportCreator(
        participantResultMapper = factory<Policy, ParticipantResultMapper>(StandardResultsTypes.grouped).invoke(policy)
    ) }

    bind<EventResultsReportFileNameGenerator>() with singleton { EventResultsReportFileNameGenerator() }
}