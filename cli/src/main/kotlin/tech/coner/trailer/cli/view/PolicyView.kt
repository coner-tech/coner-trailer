package tech.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import tech.coner.trailer.Policy

class PolicyView(
    override val console: CliktConsole
) : BaseCollectionView<Policy>() {
    override fun render(model: Policy) = """
        ${model.name}
            ID: ${model.id}
            Cone penalty seconds: ${model.conePenaltySeconds}
            Pax time style: ${model.paxTimeStyle}
            Final score style: ${model.finalScoreStyle}
    """.trimIndent()
}