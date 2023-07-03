package tech.coner.trailer.render.property.eventresults

import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.Renderer
import tech.coner.trailer.render.property.PropertyRenderer

/**
 * Render a Diff from a ParticipantResult
 *
 * It defines its own Model which takes the ParticipantResult and a function to select the desired diff Time
 */
fun interface ParticipantResultDiffPropertyRenderer : PropertyRenderer<ParticipantResultDiffPropertyRenderer.Model> {


    /**
     * Convenience calls render with a Model from supplied parameters
     */
    fun render(participantResult: ParticipantResult, selectDiffFn: (ParticipantResult) -> Time?): String {
        return render(Model(participantResult, selectDiffFn))
    }

    /**
     * Convenience calls render with a Model from supplied parameters
     */
    operator fun invoke(participantResult: ParticipantResult, selectDiffFn: (ParticipantResult) -> Time?): String {
        return render(participantResult, selectDiffFn)
    }

    /**
     * The model used by the ParticipantResultDiffRenderer to select the desired Diff from the ParticipantResult
     *
     * @param participantResult the ParticipantResult to use for selecting the Diff
     * @param selectDiffFn ParticipantResultDiffRenderer
     */
    data class Model(
        val participantResult: ParticipantResult,
        val selectDiffFn: (ParticipantResult) -> Time?
    )
}

/**
 * Render the score of a participant result
 */
fun interface ParticipantResultScoreRenderer : Renderer<ParticipantResult>