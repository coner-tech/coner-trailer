package tech.coner.trailer.render.internal

import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.Renderer

fun interface ParticipantResultDiffRenderer : Renderer<ParticipantResultDiffRenderer.Model> {

    fun render(participantResult: ParticipantResult, selectDiffFn: (ParticipantResult) -> Time?): String {
        return render(Model(participantResult, selectDiffFn))
    }

    operator fun invoke(participantResult: ParticipantResult, selectDiffFn: (ParticipantResult) -> Time?): String {
        return render(participantResult, selectDiffFn)
    }

    data class Model(
        val participantResult: ParticipantResult,
        val selectDiffFn: (ParticipantResult) -> Time?
    )
}