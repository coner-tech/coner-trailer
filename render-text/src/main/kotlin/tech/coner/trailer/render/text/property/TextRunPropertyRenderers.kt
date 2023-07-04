package tech.coner.trailer.render.text.property

import tech.coner.trailer.Run
import tech.coner.trailer.render.Strings
import tech.coner.trailer.render.property.RunRerunPropertyRenderer
import tech.coner.trailer.render.property.RunSequencePropertyRenderer

class TextRunSequencePropertyRenderer : RunSequencePropertyRenderer {
    override fun render(model: Run): String {
        return model.sequence.toString()
    }
}

class TextRunRerunPropertyRenderer : RunRerunPropertyRenderer {
    override fun render(model: Run): String {
        return if (model.rerun) Strings.abbreviationRerun else ""
    }
}
