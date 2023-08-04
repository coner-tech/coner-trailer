package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.presentation.model.ClassParentModel
import tech.coner.trailer.presentation.model.CollectionModel
import tech.coner.trailer.presentation.model.Model

class TopTimesEventResultsCollectionModel(
    override val items: Collection<Item>,
) : CollectionModel<TopTimesEventResultsCollectionModel.Item> {

    class Item(
        private val classParent: ClassParentModel,
        private val participantResult: ParticipantResultModel,
    ) : Model {
        val classParentName
            get() = classParent.name
        val participantName
            get() = participantResult.name
        val score
            get() = participantResult.score
    }
}