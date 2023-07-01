package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Car
import tech.coner.trailer.render.internal.CarModelRenderer

class TextCarModelRenderer(
    private val nullCarModel: String = ""
) : CarModelRenderer {
    override fun render(model: Car?): String {
        return model?.model ?: nullCarModel
    }
}