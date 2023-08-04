package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.presentation.model.CollectionModel

class OverallParticipantResultsCollectionModel(
    override val items: Collection<ParticipantResultModel>
) : CollectionModel<ParticipantResultModel> {
}