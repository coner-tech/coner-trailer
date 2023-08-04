package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.eventresults.EventResultsTypeModel

class EventResultsTypeModelAdapter : Adapter<EventResultsType, EventResultsTypeModel> {
    override fun invoke(model: EventResultsType): EventResultsTypeModel {
        return EventResultsTypeModel(model)
    }
}