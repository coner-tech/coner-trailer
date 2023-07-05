package tech.coner.trailer.render.text.property

import tech.coner.trailer.Policy
import tech.coner.trailer.render.property.*

class TextPolicyIdPropertyRenderer : PolicyIdPropertyRenderer {
    override fun render(model: Policy): String {
        return model.id.toString()
    }
}
class TextPolicyNamePropertyRenderer : PolicyNamePropertyRenderer {
    override fun render(model: Policy): String {
        return model.name
    }
}
class TextPolicyConePenaltySecondsPropertyRenderer : PolicyConePenaltySecondsPropertyRenderer {
    override fun render(model: Policy): String {
        return model.conePenaltySeconds.toString()
    }
}
class TextPolicyPaxTimeStylePropertyRenderer : PolicyPaxTimeStylePropertyRenderer {
    override fun render(model: Policy): String {
        return model.paxTimeStyle.toString()
    }
}
class TextPolicyFinalScoreStylePropertyRenderer : PolicyFinalScoreStylePropertyRenderer {
    override fun render(model: Policy): String {
        return model.finalScoreStyle.toString()
    }
}
