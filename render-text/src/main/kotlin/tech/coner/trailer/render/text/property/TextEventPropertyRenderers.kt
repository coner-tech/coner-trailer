package tech.coner.trailer.render.text.property

import tech.coner.trailer.Event
import tech.coner.trailer.render.property.*

class TextEventIdPropertyRenderer : EventIdPropertyRenderer {
    override fun render(model: Event): String {
        return model.id.toString()
    }
}
class TextEventDatePropertyRenderer : EventDatePropertyRenderer {
    override fun render(model: Event): String {
        return model.date.toString()
    }
}
class TextEventLifecyclePropertyRenderer : EventLifecyclePropertyRenderer {
    override fun render(model: Event): String {
        return model.lifecycle.name.lowercase()
    }
}
class TextEventCrispyFishEventControlFilePropertyRenderer : EventCrispyFishEventControlFilePropertyRenderer {
    override fun render(model: Event): String {
        return model.crispyFish?.eventControlFile?.toString() ?: ""
    }
}
class TextEventCrispyFishClassDefinitionFilePropertyRenderer : EventCrispyFishClassDefinitionFilePropertyRenderer {
    override fun render(model: Event): String {
        return model.crispyFish?.classDefinitionFile?.toString() ?: ""
    }
}
class TextEventMotorsportRegIdPropertyRenderer : EventMotorsportRegIdPropertyRenderer {
    override fun render(model: Event): String {
        return model.motorsportReg?.id ?: ""
    }
}