package tech.coner.trailer.cli.view

import tech.coner.trailer.Club

class ClubView : View<Club> {
    override fun render(model: Club) = """
        Club
            Name: ${model.name}
    """.trimIndent()
}