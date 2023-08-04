package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.presentation.adapter.eventresults.OverallEventResultsModelAdapter

class OverallEventResultsModel(
    override val eventResults: OverallEventResults,
    override val adapter: OverallEventResultsModelAdapter,
    val participantResults: OverallParticipantResultsCollectionModel
) : EventResultsModel<OverallEventResults>() {
}
