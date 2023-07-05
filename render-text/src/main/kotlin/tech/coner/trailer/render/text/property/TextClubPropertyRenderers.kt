package tech.coner.trailer.render.text.property

import tech.coner.trailer.Club
import tech.coner.trailer.render.property.ClubNamePropertyRenderer

class TextClubNamePropertyRenderer : ClubNamePropertyRenderer {
    override fun render(model: Club): String {
        return model.name
    }
}