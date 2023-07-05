package tech.coner.trailer.render.text.view

import tech.coner.trailer.Club
import tech.coner.trailer.render.property.ClubNamePropertyRenderer
import tech.coner.trailer.render.view.ClubViewRenderer

class TextClubViewRenderer(
    private val clubNamePropertyRenderer: ClubNamePropertyRenderer
) : ClubViewRenderer {
    override fun render(model: Club) = """
        Club
            Name: ${clubNamePropertyRenderer(model)}
    """.trimIndent()
}