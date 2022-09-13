package tech.coner.trailer.render.json.model

import tech.coner.trailer.eventresults.EventResultsType

class EventResultsTypeModel(
    val key: String,
    val title: String,
    val titleShort: String,
    val positionColumnHeading: String,
    val scoreColumnHeading: String
) {
    constructor(eventResultsType: EventResultsType) : this(
        key = eventResultsType.key,
        title = eventResultsType.title,
        titleShort = eventResultsType.titleShort,
        positionColumnHeading = eventResultsType.positionColumnHeading,
        scoreColumnHeading = eventResultsType.scoreColumnHeading
    )
}