package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Car
import tech.coner.trailer.render.Renderer
import tech.coner.trailer.render.internal.CarColorRenderer

class TextCarColorRenderer(
    private val nullCarColor: String = ""
) : CarColorRenderer {
    override fun render(model: Car?): String {
        return model?.color ?: nullCarColor
    }
}