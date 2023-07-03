package tech.coner.trailer.render.text.property

import tech.coner.trailer.Time
import tech.coner.trailer.render.property.NullableTimePropertyRenderer
import tech.coner.trailer.render.property.TimePropertyRenderer

class TextNullableTimePropertyRenderer(
    private val timePropertyRenderer: TimePropertyRenderer,
    private val nullTime: String = ""
) : NullableTimePropertyRenderer {
    override fun render(model: Time?): String {
        return when {
            model != null -> timePropertyRenderer.render(model)
            else -> nullTime
        }
    }
}

class TextTimePropertyRenderer : TimePropertyRenderer {
    override fun render(model: Time): String {
        return model.value.toString()
    }
}