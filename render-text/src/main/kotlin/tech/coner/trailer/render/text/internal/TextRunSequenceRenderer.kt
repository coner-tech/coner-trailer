package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Run
import tech.coner.trailer.render.internal.RunSequenceRenderer

class TextRunSequenceRenderer : RunSequenceRenderer {
    override fun render(model: Run): String {
        return model.sequence.toString()
    }
}