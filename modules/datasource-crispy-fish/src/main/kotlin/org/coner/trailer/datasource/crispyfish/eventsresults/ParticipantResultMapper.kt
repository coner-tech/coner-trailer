package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.ParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult

object ParticipantResultMapper {

    fun map(
            cfRegistration: Registration,
            cfResult: RegistrationResult,
            memberIdToPeople: Map<String, Person>
    ): ParticipantResult? {
        val cfResultPosition = cfResult.position ?: return null
        val scoredRuns = ResultRunMapper.map(
                crispyFishRegistrationRuns = cfRegistration.runs,
                crispyFishRegistrationBestRun = cfRegistration.bestRun
        )
        return ParticipantResult(
                position = cfResultPosition,
                score = ScoreMapper.map(
                        cfRegistration = cfRegistration,
                        cfResult = cfResult,
                        scoredRuns = scoredRuns
                ),
                participant = ParticipantMapper.map(
                        fromRegistration = cfRegistration,
                        withPerson = memberIdToPeople[cfRegistration.memberNumber]
                ),
                scoredRuns = scoredRuns,
                marginOfLoss = null,
                marginOfVictory = null
        )
    }

}