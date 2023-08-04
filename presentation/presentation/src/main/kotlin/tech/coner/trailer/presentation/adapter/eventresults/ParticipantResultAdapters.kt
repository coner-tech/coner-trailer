package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.Score
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.adapter.*
import tech.coner.trailer.presentation.model.eventresults.ParticipantResultModel

class ParticipantResultPositionStringFieldAdapter : StringFieldAdapter<ParticipantResult> {
    override fun invoke(model: ParticipantResult): String {
        return "${model.position}"
    }
}

/**
 * Render a Diff from a ParticipantResult
 *
 * It defines its own Model which takes the ParticipantResult and a function to select the desired diff Time
 */
class ParticipantResultDiffStringFieldAdapter(
    private val nullableTimeStringFieldAdapter: NullableTimeStringFieldAdapter
) : StringFieldAdapter<ParticipantResultDiffStringFieldAdapter.Model> {

    override fun invoke(model: Model): String {
        return when (model.participantResult.position) {
            1 -> ""
            else -> nullableTimeStringFieldAdapter(model.selectDiffFn(model.participantResult))
        }
    }

    /**
     * Convenience calls render with a Model from supplied parameters
     */
    operator fun invoke(participantResult: ParticipantResult, selectDiffFn: (ParticipantResult) -> Time?): String {
        return invoke(Model(participantResult, selectDiffFn))
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
class ParticipantResultScoreStringFieldAdapter : StringFieldAdapter<ParticipantResult> {
    override fun invoke(model: ParticipantResult): String {
        return when (model.score.penalty) {
            Score.Penalty.DidNotFinish -> Strings.abbreviationDidNotFinish
            Score.Penalty.Disqualified -> Strings.abbreviationDisqualified
            is Score.Penalty.Cone, null -> model.score.value.toString()
        }
    }
}

class ParticipantResultModelAdapter(
    val positionAdapter: ParticipantResultPositionStringFieldAdapter,
    val signageAdapter: SignageStringFieldAdapter,
    val nameAdapter: ParticipantFullNameStringFieldAdapter,
    val carModelAdapter: CarModelStringFieldAdapter,
    val scoreAdapter: ParticipantResultScoreStringFieldAdapter,
    val diffAdapter: ParticipantResultDiffStringFieldAdapter
) : Adapter<ParticipantResultModelAdapter.Input, ParticipantResultModel> {
    override fun invoke(model: Input): ParticipantResultModel {
        return ParticipantResultModel(
            eventResults = model.eventResults,
            participantResult = model.participantResult,
            adapter = this
        )
    }

    operator fun invoke(participantResult: ParticipantResult, eventResults: EventResults): ParticipantResultModel {
        return invoke(Input(participantResult, eventResults))
    }

    data class Input(
        val participantResult: ParticipantResult,
        val eventResults: EventResults
    )
}