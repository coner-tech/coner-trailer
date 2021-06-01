package org.coner.trailer.render

import org.coner.trailer.Participant
import org.coner.trailer.Run
import org.coner.trailer.Time
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.Score

interface Renderer {

    fun render(signage: Participant.Signage?) = "${signage?.grouping?.abbreviation} ${signage?.number}".trim()
    fun renderName(participant: Participant) = "${participant.firstName} ${participant.lastName}"
    fun renderScoreColumnValue(participantResult: ParticipantResult) = when (participantResult.score.penalty) {
        Score.Penalty.DidNotFinish -> Text.didNotFinish
        Score.Penalty.Disqualified -> Text.disqualified
        is Score.Penalty.Cone, null -> participantResult.score.value.toString()
    }
    fun render(time: Time?) = time?.value?.toString() ?: ""

    fun render(run: Run) = when {
        run.disqualified -> Text.disqualified
        run.didNotFinish -> "${run.time?.value}${Text.didNotFinish}"
        run.rerun -> "${run.time?.value}${Text.rerun}"
        run.cones > 0 -> "${run.time?.value}${Text.cone(run.cones)}"
        else -> run.time?.value
    }
}

private object Text {
    const val disqualified = "DSQ"
    const val didNotFinish = "DNF"
    const val rerun = "RRN"
    fun cone(count: Int) = "+$count"
}