package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter
import tech.coner.trailer.presentation.model.eventresults.EventResultsModel

class EventResultsTypeKeyStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<EventResults> {
    override fun invoke(model: EventResults): String {
        return model.type.key
    }
}

class EventResultsTypeTitleStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<EventResults> {
    override fun invoke(model: EventResults): String {
        return model.type.title
    }
}

class EventResultsTypeScoreColumnHeadingStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<EventResults> {
    override fun invoke(model: EventResults): String {
        return model.type.scoreColumnHeading
    }
}

abstract class EventResultsModelAdapter<ER : EventResults, ERM : EventResultsModel<ER>> :
    tech.coner.trailer.presentation.library.adapter.Adapter<ER, ERM> {
    abstract val participantResultAdapter: ParticipantResultModelAdapter
    abstract val eventNameAdapter: EventNameStringFieldAdapter
    abstract val eventDateAdapter: EventDateStringFieldAdapter
    abstract val eventResultsTypeKeyAdapter: EventResultsTypeKeyStringFieldAdapter
    abstract val eventResultsTypeTitleAdapter: EventResultsTypeTitleStringFieldAdapter
    abstract val eventResultsTypeScoreColumnHeadingAdapter: EventResultsTypeScoreColumnHeadingStringFieldAdapter
}
