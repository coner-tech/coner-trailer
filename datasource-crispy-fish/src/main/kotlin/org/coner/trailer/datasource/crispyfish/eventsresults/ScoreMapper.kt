package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.eventresults.RunScoreFactory
import org.coner.trailer.eventresults.Score

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
}