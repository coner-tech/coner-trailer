package tech.coner.trailer.presentation.text.view

import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel

class TextPolicyWidgets(
    private val lineSeparator: String,
) {
    val single = TextView<PolicyModel> { model ->
        """
        ID: ${model.id}
        Name: ${model.name}
        Cone penalty seconds: ${model.conePenaltySeconds}
        Pax time style: ${model.paxTimeStyle}
        Final score style: ${model.finalScoreStyle}
    """.trimIndent()
    }

    val collection: TextCollectionView<PolicyModel, PolicyCollectionModel> = SimpleTextCollectionView(single, lineSeparator)
}