package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.model.eventresults.EventResultsTypeModel

class EventResultsTypeModelAdapter :
    tech.coner.trailer.presentation.library.adapter.Adapter<EventResultsType, EventResultsTypeModel> {
    override fun invoke(model: EventResultsType): EventResultsTypeModel {
        return EventResultsTypeModel(model)
    }
}