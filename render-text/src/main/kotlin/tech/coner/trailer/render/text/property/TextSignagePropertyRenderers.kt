package tech.coner.trailer.render.text.property

import tech.coner.trailer.SignageStyle
import tech.coner.trailer.render.property.SignagePropertyRenderer

class TextSignagePropertyRenderer(
    private val nullGroupAbbreviation: String = "",
    private val nullHandicapAbbreviation: String = "",
    private val nullNumber: String = ""
) : SignagePropertyRenderer {
    override fun render(model: SignagePropertyRenderer.Model): String {
        val groupAbbreviation = model.signage?.classing?.group?.abbreviation ?: nullGroupAbbreviation
        val handicapAbbreviation = model.signage?.classing?.handicap?.abbreviation ?: nullHandicapAbbreviation
        val classingAbbreviation: String = "$groupAbbreviation $handicapAbbreviation".trim()
        val number = model.signage?.number ?: nullNumber
        return when (model.policy.signageStyle) {
            SignageStyle.CLASSING_NUMBER -> "$classingAbbreviation $number"
            SignageStyle.NUMBER_CLASSING -> "$number $classingAbbreviation"
        }
            .trim()
    }
}