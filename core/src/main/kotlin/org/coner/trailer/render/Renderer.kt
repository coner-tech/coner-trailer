package org.coner.trailer.render

import kotlinx.html.*
import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.Score

interface Renderer {

    fun render(signage: Participant.Signage?) = "${signage?.grouping?.abbreviation} ${signage?.number}".trim()
    fun renderName(participant: Participant) = "${participant.firstName} ${participant.lastName}"
    fun renderScoreColumnValue(participantResult: ParticipantResult) = when(participantResult.score.penalty) {
        Score.Penalty.DidNotFinish -> "DNF"
        Score.Penalty.Disqualified -> "DSQ"
        else -> participantResult.score.value.toString()
    }
    fun render(time: Time?) = time?.value?.toString() ?: ""

    fun HEAD.bootstrapMetaViewport() {
        meta(name = "viewport", content = "width=device-width, initial-scale=1")
    }

    fun HEAD.bootstrapLinkCss() {
        link(
            href = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css",
            rel = LinkRel.stylesheet,
        ) {
            integrity = "sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
            attributes["crossorigin"] = "anonymous"
        }
    }
}