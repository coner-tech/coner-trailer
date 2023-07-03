package tech.coner.trailer.render.text.property

import tech.coner.trailer.Policy
import tech.coner.trailer.Signage
import tech.coner.trailer.SignageStyle
import tech.coner.trailer.render.property.SignagePropertyRenderer

class TextSignagePropertyRenderer(
    private val policy: Policy,
    private val nullGroupAbbreviation: String = "",
    private val nullHandicapAbbreviation: String = "",
    private val nullNumber: String = ""
) : SignagePropertyRenderer {
    override fun render(model: Signage?): String {
        val groupAbbreviation = model?.classing?.group?.abbreviation ?: nullGroupAbbreviation
        val handicapAbbreviation = model?.classing?.handicap?.abbreviation ?: nullHandicapAbbreviation
        val classingAbbreviation: String = "$groupAbbreviation $handicapAbbreviation".trim()
        val number = model?.number ?: nullNumber
        return when (policy.signageStyle) {
            SignageStyle.CLASSING_NUMBER -> "$classingAbbreviation $number"
            SignageStyle.NUMBER_CLASSING -> "$number $classingAbbreviation"
        }
            .trim()
    }
}