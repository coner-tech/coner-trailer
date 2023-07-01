package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Time
import tech.coner.trailer.render.internal.NullableTimeRenderer
import tech.coner.trailer.render.internal.TimeRenderer

class TextNullableTimeRenderer(
    private val timeRenderer: TimeRenderer,
    private val nullTime: String = ""
) : NullableTimeRenderer {
    override fun render(model: Time?): String {
        return when {
            model != null -> timeRenderer.render(model)
            else -> nullTime
        }
    }
}