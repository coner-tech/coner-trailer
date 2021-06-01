package org.coner.trailer.datasource.crispyfish.eventsresults

import tech.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.eventresults.FinalScoreFactory
import org.coner.trailer.eventresults.ParticipantResult

class ParticipantResultMapper(
    private val resultRunMapper: ResultRunMapper,
    private val finalScoreFactory: FinalScoreFactory,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val crispyFishRunMapper: CrispyFishRunMapper
) {

    fun toCore(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext,
        cfRegistration: Registration
    ): ParticipantResult? {
        val coreSignage = crispyFishParticipantMapper.toCoreSignage(
            context = context,
            crispyFish = cfRegistration
        )
        val peopleMapKey = Event.CrispyFishMetadata.PeopleMapKey(
            grouping = coreSignage.grouping ?: return null,
            number = coreSignage.number ?: return null,
            firstName = cfRegistration.firstName ?: return null,
            lastName = cfRegistration.lastName ?: return null
        )
        val participant = crispyFishParticipantMapper.toCore(
            context = context,
            fromRegistration = cfRegistration,
            withPerson = eventCrispyFishMetadata.peopleMap[peopleMapKey]
        )
        val participantCfRuns = context.runsByRegistration[cfRegistration] ?: return null
        val allRuns = participantCfRuns
            .mapNotNull { cfRun -> crispyFishRunMapper.toCore(
                cfRun = cfRun,
                cfRunIndex = cfRun.number ?: return@mapNotNull null,
                participant = participant
            ) }
        val scoredRuns = resultRunMapper.toCores(
            context = context,
            participantCfRuns = participantCfRuns,
            participant = participant
        )
        return ParticipantResult(
            score = finalScoreFactory.score(scoredRuns) ?: return null,
            participant = participant,
            allRuns = allRuns,
            scoredRuns = scoredRuns,
            personalBestScoredRunIndex = finalScoreFactory.bestRun(scoredRuns)?.let { scoredRuns.indexOf(it) },
            // positions and diffs are calculated in toCoreRanked after sorting by score
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