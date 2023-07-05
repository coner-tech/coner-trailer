package tech.coner.trailer.render.text.property

import tech.coner.trailer.Policy
import tech.coner.trailer.render.property.PolicyIdPropertyRenderer
import tech.coner.trailer.render.property.PolicyNamePropertyRenderer

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