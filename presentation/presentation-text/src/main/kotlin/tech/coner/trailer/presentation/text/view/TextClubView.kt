package tech.coner.trailer.presentation.text.view

import tech.coner.trailer.presentation.model.ClubModel

class TextClubView : TextView<ClubModel> {
    override operator fun invoke(model: ClubModel) = """
        Club
            Name: ${model.name}
    """.trimIndent()
}