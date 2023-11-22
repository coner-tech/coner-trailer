package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.model.eventresults.ComprehensiveEventResultsModel

class ComprehensiveEventResultsModelAdapter(
    override val participantResultAdapter: ParticipantResultModelAdapter,
    override val eventNameAdapter: EventNameStringFieldAdapter,
    override val eventDateAdapter: EventDateStringFieldAdapter,
    override val eventResultsTypeKeyAdapter: EventResultsTypeKeyStringFieldAdapter,
    override val eventResultsTypeTitleAdapter: EventResultsTypeTitleStringFieldAdapter,
    override val eventResultsTypeScoreColumnHeadingAdapter: EventResultsTypeScoreColumnHeadingStringFieldAdapter,
    private val overallEventResultsAdapter: OverallEventResultsModelAdapter,
    private val classEventResultsAdapter: ClassEventResultsModelAdapter,
    private val topTimesEventResultsAdapter: TopTimesEventResultsModelAdapter
) : EventResultsModelAdapter<ComprehensiveEventResults, ComprehensiveEventResultsModel>() {
    override fun invoke(model: ComprehensiveEventResults): ComprehensiveEventResultsModel {
        return ComprehensiveEventResultsModel(
            eventResults = model,
            adapter = this,
            overallEventResults = model.overallEventResults.map { overallEventResultsAdapter(it) },
            classEventResults = classEventResultsAdapter(model.classEventResults),
            topTimesEventResults = topTimesEventResultsAdapter(model.topTimesEventResults)
        )
    }
}