package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Time
import tech.coner.trailer.render.internal.TimeRenderer

class TextTimeRenderer : TimeRenderer {
    override fun render(model: Time): String {
        return model.value.toString()
    }
}