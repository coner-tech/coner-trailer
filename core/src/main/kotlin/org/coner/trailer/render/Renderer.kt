package org.coner.trailer.render

import org.coner.trailer.Participant
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.Score

interface Renderer {

    fun render(signage: Participant.Signage) = "${signage.grouping.abbreviation} ${signage.number}"
    fun renderName(participant: Participant) = "${participant.firstName} ${participant.lastName}"
    fun renderScoreColumnValue(participantResult: ParticipantResult) = when(participantResult.score.penalty) {
        Score.Penalty.DidNotFinish -> "DNF"
        Score.Penalty.Disqualified -> "DSQ"
        else -> participantResult.score.value.toString()
    }
}