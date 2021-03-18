package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Participant
import org.coner.trailer.Policy
import org.coner.trailer.Time
import org.coner.trailer.eventresults.Score

class ScoreMapper {

    fun toScore(
        cfRegistrationRun: RegistrationRun,
        corePolicy: Policy,
        participant: Participant,
        scoreFn: (participant: Participant, time: Time?, Score.Penalty?) -> Score
    ) : Score? {
        val time = Time(cfRegistrationRun.time ?: return null)
        val penalty = when (val cfPenalty = cfRegistrationRun.penalty) {
            is RegistrationRun.Penalty.Cone -> Score.Penalty.Cone(corePolicy, cfPenalty.count)
            RegistrationRun.Penalty.DidNotFinish -> Score.Penalty.DidNotFinish
            RegistrationRun.Penalty.Disqualified -> Score.Penalty.Disqualified
            RegistrationRun.Penalty.Unknown -> Score.Penalty.Unknown
            null -> null
        }
        return scoreFn(participant, time, penalty)
    }
}