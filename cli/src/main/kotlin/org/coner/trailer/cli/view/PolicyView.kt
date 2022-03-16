package org.coner.trailer.cli.view

import org.coner.trailer.Policy

class PolicyView : View<Policy> {
    override fun render(model: Policy) = """
        ${model.name}
            ID: ${model.id}
            Cone penalty seconds: ${model.conePenaltySeconds}
            Pax time style: ${model.paxTimeStyle}
            Final score style: ${model.finalScoreStyle}
    """.trimIndent()
}