package tech.coner.trailer.render.text.view

import tech.coner.trailer.Policy
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.text.property.TextPolicyIdPropertyRenderer
import tech.coner.trailer.render.view.BaseCollectionViewRenderer
import tech.coner.trailer.render.view.PolicyCollectionViewRenderer
import tech.coner.trailer.render.view.PolicyViewRenderer

class TextPolicyViewRenderer(
    lineSeparator: String,
    private val policyIdPropertyRenderer: PolicyIdPropertyRenderer,
    private val policyNamePropertyRenderer: PolicyNamePropertyRenderer,
    private val policyConePenaltySecondsPropertyRenderer: PolicyConePenaltySecondsPropertyRenderer,
    private val policyPaxTimeStylePropertyRenderer: PolicyPaxTimeStylePropertyRenderer,
    private val policyFinalScoreStylePropertyRenderer: PolicyFinalScoreStylePropertyRenderer
) : BaseCollectionViewRenderer<Policy>(lineSeparator),
    PolicyViewRenderer,
    PolicyCollectionViewRenderer {
    override fun render(model: Policy) = """
        ID: ${policyIdPropertyRenderer(model)}
        Name: ${policyNamePropertyRenderer(model)}
        Cone penalty seconds: ${policyConePenaltySecondsPropertyRenderer(model)}
        Pax time style: ${policyPaxTimeStylePropertyRenderer(model)}
        Final score style: ${policyFinalScoreStylePropertyRenderer(model)}
    """.trimIndent()
}