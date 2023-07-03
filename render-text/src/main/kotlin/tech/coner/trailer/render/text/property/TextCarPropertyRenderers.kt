package tech.coner.trailer.render.text.property

import tech.coner.trailer.Car
import tech.coner.trailer.render.property.CarColorPropertyRenderer
import tech.coner.trailer.render.property.CarModelPropertyRenderer

class TextCarColorPropertyRenderer(
    private val nullCarColor: String = ""
) : CarColorPropertyRenderer {
    override fun render(model: Car?): String {
        return model?.color ?: nullCarColor
    }
}

class TextCarModelPropertyRenderer(
    private val nullCarModel: String = ""
) : CarModelPropertyRenderer {
    override fun render(model: Car?): String {
        return model?.model ?: nullCarModel
    }
}