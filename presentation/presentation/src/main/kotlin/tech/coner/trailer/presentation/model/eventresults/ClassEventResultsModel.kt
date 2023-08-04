package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.presentation.adapter.eventresults.ClassEventResultsModelAdapter

class ClassEventResultsModel(
    override val eventResults: ClassEventResults,
    override val adapter: ClassEventResultsModelAdapter,
    val classEventResults: ClassParticipantResultsCollectionModel
) : EventResultsModel<ClassEventResults>() {
}
