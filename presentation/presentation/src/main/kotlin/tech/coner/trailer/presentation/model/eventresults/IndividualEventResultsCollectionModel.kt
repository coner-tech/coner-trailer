package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.presentation.model.CollectionModel
import tech.coner.trailer.presentation.model.Model
import tech.coner.trailer.presentation.model.ParticipantModel

class IndividualEventResultsCollectionModel(
    override val items: Collection<Item>
) : CollectionModel<IndividualEventResultsCollectionModel.Item> {

    class Item(
        val participant: ParticipantModel,
        val typedParticipantResults: List<TypedParticipantResultModel>
    ) : Model

    class TypedParticipantResultModel(
        private val eventResultsType: EventResultsTypeModel,
        private val participantResult: ParticipantResultModel?
    ) : Model {
        val position
            get() = participantResult?.position ?: ""
        val score
            get() = participantResult?.score ?: ""
    }
}