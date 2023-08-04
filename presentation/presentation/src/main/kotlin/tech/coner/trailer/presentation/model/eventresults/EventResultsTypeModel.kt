package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.presentation.model.Model

class EventResultsTypeModel(
    private val eventResultsType: EventResultsType
) : Model {
    val title
        get() = eventResultsType.title
    val titleShort
        get() = eventResultsType.titleShort
    val positionColumnHeading
        get() = eventResultsType.positionColumnHeading
    val scoreColumnHeading
        get() = eventResultsType.scoreColumnHeading
}