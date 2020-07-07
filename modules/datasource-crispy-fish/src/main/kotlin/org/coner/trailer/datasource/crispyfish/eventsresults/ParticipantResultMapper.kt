package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.trailer.Person
import org.coner.trailer.Score
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.ParticipantMapper
import org.coner.trailer.eventresults.ParticipantResult

object ParticipantResultMapper {

    fun map(
            crispyFishRegistration: Registration,
            crispyFishResult: RegistrationResult,
            peopleByMemberId: Map<String, Person>
    ): ParticipantResult? {
        val classResultPosition = crispyFishResult.position ?: return null
        val scoredRuns = ResultRunMapper.map(
                crispyFishRegistrationRuns = crispyFishRegistration.runs,
                crispyFishRegistrationBestRun = crispyFishRegistration.bestRun
        )
        val bestRun = scoredRuns.single { it.personalBest }
        return ParticipantResult(
                position = classResultPosition,
                score = when {
                    crispyFishResult.time.isValidTime() -> Score(crispyFishResult.time)
                    crispyFishResult.time.isDidNotFinish() -> Score.forDidNotFinishWithBestTime(bestRun.time)
                    else -> Score(Int.MAX_VALUE)
                },
                participant = ParticipantMapper.map(
                        fromRegistration = crispyFishRegistration,
                        withPerson = peopleByMemberId[crispyFishRegistration.memberNumber]
                ),
                scoredRuns = scoredRuns,
                marginOfLoss = null,
                marginOfVictory = null
        )
    }

    private fun String.isValidTime() = Time.pattern.matcher(this).matches()
    private fun String.isDidNotFinish() = this == "DNF"

}