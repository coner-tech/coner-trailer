package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.presentation.adapter.eventresults.EventResultsModelAdapter
import tech.coner.trailer.presentation.library.model.Model

abstract class EventResultsModel<ER : EventResults> : Model {
    protected abstract val eventResults: ER
    protected abstract val adapter: EventResultsModelAdapter<ER, *>

    val eventName
        get() = adapter.eventNameAdapter(eventResults.eventContext.event)
    val eventDate
        get() = adapter.eventDateAdapter(eventResults.eventContext.event)
    val eventResultsTypeKey
        get() = adapter.eventResultsTypeKeyAdapter(eventResults)
    val eventResultsTypeTitle
        get() = adapter.eventResultsTypeTitleAdapter(eventResults)
    val eventResultsTypeScoreColumnHeading
        get() = adapter.eventResultsTypeScoreColumnHeadingAdapter(eventResults)
}