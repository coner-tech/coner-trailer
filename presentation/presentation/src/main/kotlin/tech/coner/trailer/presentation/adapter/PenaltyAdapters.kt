package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Run
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter

class PenaltyStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Run.Penalty> {
    override operator fun invoke(model: Run.Penalty): String {
        return when (model) {
            Run.Penalty.Disqualified -> Strings.abbreviationDisqualified
            Run.Penalty.DidNotFinish -> Strings.abbreviationDidNotFinish
            is Run.Penalty.Cone -> "+${model.count}"
        }
    }
}

class PenaltyCollectionStringFieldAdapter(
    private val penaltyPropertyRenderer: PenaltyStringFieldAdapter
) : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<List<Run.Penalty>?> {
    override operator fun invoke(model: List<Run.Penalty>?): String {
        return model?.joinToString(transform = penaltyPropertyRenderer::invoke) ?: ""
    }
}
