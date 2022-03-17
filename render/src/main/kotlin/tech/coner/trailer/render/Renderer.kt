package tech.coner.trailer.render

import tech.coner.trailer.Participant
import tech.coner.trailer.Run
import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.Score

interface Renderer {

    fun renderName(participant: Participant) = "${participant.firstName} ${participant.lastName}"
    fun renderScoreColumnValue(participantResult: ParticipantResult) = when (participantResult.score.penalty) {
        Score.Penalty.DidNotFinish -> Text.didNotFinish
        Score.Penalty.Disqualified -> Text.disqualified
        is Score.Penalty.Cone, null -> participantResult.score.value.toString()
    }
    fun render(time: Time?) = time?.value?.toString() ?: ""

    fun render(run: Run): String {
        return when {
            run.disqualified -> Text.disqualified
            run.didNotFinish -> "${run.time?.value}+${Text.didNotFinish}"
            run.rerun -> {
                if (run.cones > 0)
                    "${run.time?.value}+${Text.rerun}, +${Text.cone(run.cones)}"
                else
                    "${run.time?.value}+${Text.rerun}"
            }
            run.cones > 0 -> "${run.time?.value}+${Text.cone(run.cones)}"
            else -> "${run.time?.value ?: ""}"
        }
    }
}

private object Text {
    const val disqualified = "DSQ"
    const val didNotFinish = "DNF"
    const val rerun = "RRN"
    fun cone(count: Int) = "$count"
}