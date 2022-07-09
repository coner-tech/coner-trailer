package tech.coner.trailer.datasource.crispyfish.eventresults

import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.StagingRun
import tech.coner.trailer.Class
import tech.coner.trailer.Event
import tech.coner.trailer.Time
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import tech.coner.trailer.eventresults.FinalScoreFactory
import tech.coner.trailer.eventresults.ParticipantResult

@Deprecated("Removing in favor of core-only event results calculator")
class ParticipantResultMapper(
    private val resultRunMapper: ResultRunMapper,
    private val finalScoreFactory: FinalScoreFactory,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val crispyFishRunMapper: CrispyFishRunMapper
) {

    fun toCore(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext,
        allClassesByAbbreviation: Map<String, Class>,
        registration: Registration
    ): ParticipantResult? {
        val participant = crispyFishParticipantMapper.toCoreFromRegistration(
            allClassesByAbbreviation = allClassesByAbbreviation,
            peopleMap = eventCrispyFishMetadata.peopleMap,
            registration = registration
        )
        val participantCfStagingRuns = context.runsByRegistration[registration] ?: return null
        val participantCfRuns = participantCfStagingRuns
            .map { (cfRunIndex, cfStagingRun) ->
                crispyFishRunMapper.toCore(
                    cfRun = cfStagingRun.run,
                    cfRunIndex = cfRunIndex,
                    participant = participant
                )
            }
        val scoredRuns = resultRunMapper.toCores(
            context = context,
            participantCfRuns = participantCfStagingRuns.mapNotNull { (index, stagingRun) -> stagingRun.run?.let { index to it } },
            participant = participant
        )
        return ParticipantResult(
            score = finalScoreFactory.score(scoredRuns) ?: return null,
            participant = participant,
            allRuns = participantCfRuns,
            scoredRuns = scoredRuns,
            personalBestScoredRunIndex = finalScoreFactory.bestRun(scoredRuns)?.let { scoredRuns.indexOf(it) },

            position = Int.MAX_VALUE,
            diffFirst = null,
            diffPrevious = null,
        )
    }

    fun toCoreRanked(sortedResults: List<ParticipantResult>, index: Int, result: ParticipantResult): ParticipantResult {
        return result.copy(
            position = index + 1,
            diffFirst = when {
                index == 0 -> null
                result.score.penalty?.diff == false -> null
                else -> Time(result.score.value - sortedResults[0].score.value)
            },
            diffPrevious = when {
                index == 0 -> null
                result.score.penalty?.diff == false -> null
                else -> Time(result.score.value - sortedResults[index - 1].score.value)
            }
        )
    }

}