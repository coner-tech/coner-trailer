package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.eventresults.RunScoreFactory
import org.coner.trailer.eventresults.Score
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.model.RegistrationRun
import tech.coner.crispyfish.model.Run

class ScoreMapper(
    private val runScoreFactory: RunScoreFactory
) {

    fun toScore(
        cfRegistrationRun: RegistrationRun,
        participant: Participant,
    ) : Score? {
        return runScoreFactory.score(
            participantGrouping = participant.signage?.grouping ?: return null,
            scratchTime = Time(cfRegistrationRun.time ?: return null),
            cones = (cfRegistrationRun.penalty as? RegistrationRun.Penalty.Cone?)?.count,
            didNotFinish = cfRegistrationRun.penalty == RegistrationRun.Penalty.DidNotFinish,
            disqualified = cfRegistrationRun.penalty == RegistrationRun.Penalty.Disqualified
        )
    }

    fun toScore(
        cfRun: Run,
        participant: Participant
    ): Score? {
        return runScoreFactory.score(
            participantGrouping = participant.signage?.grouping ?: return null,
            scratchTime = Time(cfRun.timeScratchAsString ?: return null),
            cones = if (cfRun.penaltyType == PenaltyType.CONE) cfRun.cones else null,
            didNotFinish = cfRun.penaltyType == PenaltyType.DID_NOT_FINISH,
            disqualified = cfRun.penaltyType == PenaltyType.DISQUALIFIED
        )
    }
}