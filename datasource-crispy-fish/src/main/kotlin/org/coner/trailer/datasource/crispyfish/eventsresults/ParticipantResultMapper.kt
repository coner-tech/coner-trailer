package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult

class ParticipantResultMapper(
    private val resultRunMapper: ResultRunMapper,
    private val scoreMapper: ScoreMapper,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper
) {

    fun toCore(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext,
        cfRegistration: Registration,
        cfResult: RegistrationResult
    ): ParticipantResult? {
        val cfResultPosition = cfResult.position
        val scoredRuns = resultRunMapper.map(
            crispyFishRegistrationRuns = cfRegistration.runs,
            crispyFishRegistrationBestRun = cfRegistration.bestRun
        )
        val peopleMapKey = Event.CrispyFishMetadata.PeopleMapKey(
            signage = crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = cfRegistration
            ),
            firstName = cfRegistration.firstName ?: return null,
            lastName = cfRegistration.lastName ?: return null
        )
        return ParticipantResult(
            position = cfResultPosition,
            score = scoreMapper.map(
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
            marginOfLoss = null,
            marginOfVictory = null
        )
    }

}