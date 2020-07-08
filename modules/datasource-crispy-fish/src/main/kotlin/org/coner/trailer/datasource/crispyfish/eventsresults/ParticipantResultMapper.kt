package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Person
import org.coner.trailer.eventresults.Score
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.ParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult
import java.math.BigDecimal

object ParticipantResultMapper {

    fun map(
            cfRegistration: Registration,
            cfResult: RegistrationResult,
            peopleByMemberId: Map<String, Person>
    ): ParticipantResult? {
        val classResultPosition = cfResult.position ?: return null
        val scoredRuns = ResultRunMapper.map(
                crispyFishRegistrationRuns = cfRegistration.runs,
                crispyFishRegistrationBestRun = cfRegistration.bestRun
        )
        return ParticipantResult(
                position = classResultPosition,
                score = ScoreMapper.map(
                        cfRegistration = cfRegistration,
                        cfResult = cfResult,
                        scoredRuns = scoredRuns
                ),
                participant = ParticipantMapper.map(
                        fromRegistration = cfRegistration,
                        withPerson = peopleByMemberId[cfRegistration.memberNumber]
                ),
                scoredRuns = scoredRuns,
                marginOfLoss = null,
                marginOfVictory = null
        )
    }

}