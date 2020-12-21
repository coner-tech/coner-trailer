package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult

class ParticipantResultMapper(
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val memberIdToPeople: Map<String, Person>
) {

    fun toCore(
        context: CrispyFishEventMappingContext,
        cfRegistration: Registration,
        cfResult: RegistrationResult
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
            participant = crispyFishParticipantMapper.toCore(
                context = context,
                fromRegistration = cfRegistration,
                withPerson = memberIdToPeople[cfRegistration.memberNumber]
            ),
            scoredRuns = scoredRuns,
            marginOfLoss = null,
            marginOfVictory = null
        )
    }

}