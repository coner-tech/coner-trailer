package org.coner.trailer.cli.view

import org.coner.trailer.Club

class ClubView : View<Club> {
    override fun render(model: Club) = """
        Club
            Name: ${model.name}
    """.trimIndent()
}