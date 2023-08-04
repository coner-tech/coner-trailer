package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.model.eventresults.OverallEventResultsModel
import tech.coner.trailer.presentation.model.eventresults.OverallParticipantResultsCollectionModel

class OverallEventResultsModelAdapter(
    override val participantResultAdapter: ParticipantResultModelAdapter,
    override val eventNameAdapter: EventNameStringFieldAdapter,
    override val eventDateAdapter: EventDateStringFieldAdapter,
    override val eventResultsTypeTitleAdapter: EventResultsTypeTitleStringFieldAdapter,
    override val eventResultsTypeScoreColumnHeadingAdapter: EventResultsTypeScoreColumnHeadingStringFieldAdapter,
    private val overallParticipantResultsCollectionAdapter: OverallParticipantResultsCollectionModelAdapter
) : EventResultsModelAdapter<OverallEventResults, OverallEventResultsModel>() {
    override fun invoke(model: OverallEventResults): OverallEventResultsModel {
        return OverallEventResultsModel(
            eventResults = model,
            adapter = this,
            participantResults = overallParticipantResultsCollectionAdapter(model)
        )
    }
}

class OverallParticipantResultsCollectionModelAdapter(
    private val participantResultAdapter: ParticipantResultModelAdapter
) : Adapter<OverallEventResults, OverallParticipantResultsCollectionModel> {
    override fun invoke(model: OverallEventResults): OverallParticipantResultsCollectionModel {
        return OverallParticipantResultsCollectionModel(
            items = model.participantResults.map { participantResultAdapter(it, model) }
        )
    }
}