package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Run
import tech.coner.trailer.render.Text
import tech.coner.trailer.render.internal.RunRerunRenderer

class TextRunRerunRenderer : RunRerunRenderer {

    override fun render(model: Run): String {
        return if (model.rerun) Text.rerun else ""
    }
}