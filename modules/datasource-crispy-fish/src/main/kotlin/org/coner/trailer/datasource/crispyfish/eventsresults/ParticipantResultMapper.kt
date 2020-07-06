package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.ParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult

object ParticipantResultMapper {

    fun map(
            crispyFishRegistration: Registration,
            crispyFishResult: RegistrationResult,
            peopleByMemberId: Map<String, Person>
    ): ParticipantResult? {
        val classResultPosition = crispyFishResult.position
                ?: return null
        return ParticipantResult(
                position = classResultPosition,
                participant = ParticipantMapper.map(
                        fromRegistration = crispyFishRegistration,
                        withPerson = peopleByMemberId[crispyFishRegistration.memberNumber]
                ),
                scoredRuns = ResultRunMapper.map(
                        crispyFishRegistrationRuns = crispyFishRegistration.runs,
                        crispyFishRegistrationBestRun = crispyFishRegistration.bestRun
                ),
                marginOfLoss = null,
                marginOfVictory = null
        )
    }

}