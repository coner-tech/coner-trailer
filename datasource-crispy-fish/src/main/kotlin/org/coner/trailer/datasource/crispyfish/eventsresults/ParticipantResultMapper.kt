package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.ResultsType

class ParticipantResultMapper(
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper
) {

    fun toCore(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext,
        cfRegistration: Registration,
        cfResult: RegistrationResult,
        resultsType: ResultsType
    ): ParticipantResult? {
        val cfResultPosition = cfResult.position ?: return null
        val scoredRuns = ResultRunMapper.map(
            crispyFishRegistrationRuns = cfRegistration.runs,
            crispyFishRegistrationBestRun = cfRegistration.bestRun
        )
        val peopleMapKey = Event.CrispyFishMetadata.PeopleMapKey(
            signage = crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = cfRegistration
            ),
            firstName = cfRegistration.firstName,
            lastName = cfRegistration.lastName
        )
        return ParticipantResult(
            position = cfResultPosition,
            score = ScoreMapper.map(
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
            marginOfVictory = null,
            resultsType = resultsType
        )
    }

}