package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Policy
import tech.coner.trailer.Signage
import tech.coner.trailer.SignageStyle

class SignageStringFieldAdapter : StringFieldAdapter<SignageStringFieldAdapter.Input> {
    override operator fun invoke(model: Input): String {
        val groupAbbreviation = model.signage?.classing?.group?.abbreviation ?: ""
        val handicapAbbreviation = model.signage?.classing?.handicap?.abbreviation ?: ""
        val classingAbbreviation: String = "$groupAbbreviation $handicapAbbreviation".trim()
        val number = model.signage?.number ?: ""
        return when (model.policy.signageStyle) {
            SignageStyle.CLASSING_NUMBER -> "$classingAbbreviation $number"
            SignageStyle.NUMBER_CLASSING -> "$number $classingAbbreviation"
        }
            .trim()
    }

    data class Input(val signage: Signage?, val policy: Policy)

    operator fun invoke(signage: Signage?, policy: Policy) = invoke(Input(signage, policy))
}
