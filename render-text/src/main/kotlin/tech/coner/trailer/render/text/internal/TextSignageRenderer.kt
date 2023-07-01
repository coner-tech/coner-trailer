package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Policy
import tech.coner.trailer.Signage
import tech.coner.trailer.SignageStyle
import tech.coner.trailer.render.internal.SignageRenderer

class TextSignageRenderer(
    private val policy: Policy,
    private val nullGroupAbbreviation: String = "",
    private val nullHandicapAbbreviation: String = "",
    private val nullNumber: String = ""
) : SignageRenderer {
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