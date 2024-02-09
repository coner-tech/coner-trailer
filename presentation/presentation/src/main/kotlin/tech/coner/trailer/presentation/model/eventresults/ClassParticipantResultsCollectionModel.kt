package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.presentation.adapter.eventresults.ClassParticipantResultsCollectionModelAdapter
import tech.coner.trailer.presentation.model.ClassModel
import tech.coner.trailer.presentation.library.model.CollectionModel
import tech.coner.trailer.presentation.library.model.Model

class ClassParticipantResultsCollectionModel(
    override val items: Collection<Item>,
    private val eventResults: ClassEventResults,
    private val adapter: ClassParticipantResultsCollectionModelAdapter
) : CollectionModel<ClassParticipantResultsCollectionModel.Item> {

    class Item(
        val clazz: ClassModel,
        val participantResults: List<ParticipantResultModel>
    ) : Model
}