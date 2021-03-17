package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Event
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.Policy

class ParticipantResultMapper(
    private val resultRunMapper: ResultRunMapper,
    private val scoreMapper: ScoreMapper,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper
) {

    fun toCore(
        corePolicy: Policy,
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext,
        cfRegistration: Registration,
        cfResult: RegistrationResult
    ): ParticipantResult? {
        val scoredRuns = resultRunMapper.toCore(
            corePolicy = corePolicy,
            crispyFishRegistrationRuns = cfRegistration.runs,
            crispyFishRegistrationBestRun = cfRegistration.bestRun
        )
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
        return ParticipantResult(
            score = scoreMapper.toScore(
                cfRegistration = cfRegistration,
                cfResult = cfResult,
                scoredRuns = scoredRuns
            ) ?: return null,
            participant = crispyFishParticipantMapper.toCore(
                context = context,
                fromRegistration = cfRegistration,
                withPerson = eventCrispyFishMetadata.peopleMap[peopleMapKey]
            ),
            scoredRuns = scoredRuns,
            // positions and diffs are calculated in toCoreRanked after sorting by score
            position = Int.MAX_VALUE,
            diffFirst = null,
            diffPrevious = null
        )
    }

    fun toCoreRanked(sortedResults: List<ParticipantResult>, index: Int, result: ParticipantResult): ParticipantResult {
        return result.copy(
            position = index + 1,
            diffFirst = when {
                index == 0 -> null
                result.score.penalty != null -> null
                else -> Time(result.score.value - sortedResults[0].score.value)
            },
            diffPrevious = when {
                index == 0 -> null
                result.score.penalty != null -> null
                else -> Time(result.score.value - sortedResults[index - 1].score.value)
            }
        )
    }

}