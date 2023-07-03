package tech.coner.trailer.render.text.property

import tech.coner.trailer.Run
import tech.coner.trailer.render.property.RunRerunPropertyRenderer
import tech.coner.trailer.render.property.RunSequencePropertyRenderer
import tech.coner.trailer.render.property.Strings

class TextRunRerunPropertyRenderer : RunRerunPropertyRenderer {

    override fun render(model: Run): String {
        return if (model.rerun) Strings.Abbreviations.rerun else ""
    }
}

class TextRunSequencePropertyRenderer : RunSequencePropertyRenderer {
    override fun render(model: Run): String {
        return model.sequence.toString()
    }
}