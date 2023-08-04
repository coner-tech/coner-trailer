package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.StringFieldAdapter
import tech.coner.trailer.presentation.model.eventresults.EventResultsModel

class EventResultsTypeTitleStringFieldAdapter : StringFieldAdapter<EventResults> {
    override fun invoke(model: EventResults): String {
        return model.type.title
    }
}

class EventResultsTypeScoreColumnHeadingStringFieldAdapter : StringFieldAdapter<EventResults> {
    override fun invoke(model: EventResults): String {
        return model.type.scoreColumnHeading
    }
}

abstract class EventResultsModelAdapter<ER : EventResults, ERM : EventResultsModel<ER>> : Adapter<ER, ERM> {
    abstract val participantResultAdapter: ParticipantResultModelAdapter
    abstract val eventNameAdapter: EventNameStringFieldAdapter
    abstract val eventDateAdapter: EventDateStringFieldAdapter
    abstract val eventResultsTypeTitleAdapter: EventResultsTypeTitleStringFieldAdapter
    abstract val eventResultsTypeScoreColumnHeadingAdapter: EventResultsTypeScoreColumnHeadingStringFieldAdapter
}
