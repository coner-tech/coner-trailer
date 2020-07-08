package org.coner.trailer.datasource.crispyfish.eventsresults

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
        val bestRun = scoredRuns.single { it.personalBest }
        return ParticipantResult(
                position = classResultPosition,
                score = when {
                    cfResult.hasValidTime() -> Score(BigDecimal(cfResult.time))
                    cfResult.hasDidNotFinish() -> Score.forDidNotFinishWithBestTime(bestRun.time)
                    cfResult.hasDisqualified() -> Score.forDisqualifiedWithBestTime(bestRun.time)
                    else -> return null
                },
                participant = ParticipantMapper.map(
                        fromRegistration = cfRegistration,
                        withPerson = peopleByMemberId[cfRegistration.memberNumber]
                ),
                scoredRuns = scoredRuns,
                marginOfLoss = null,
                marginOfVictory = null
        )
    }

    private fun RegistrationResult.hasValidTime() = Time.pattern.matcher(time).matches()
    private fun RegistrationResult.hasDidNotFinish() = time == "DNF"
    private fun RegistrationResult.hasDisqualified() = time == "DSQ"

}