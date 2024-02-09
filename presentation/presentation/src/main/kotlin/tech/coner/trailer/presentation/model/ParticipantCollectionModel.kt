package tech.coner.trailer.presentation.model

import tech.coner.trailer.presentation.library.model.CollectionModel

data class ParticipantCollectionModel(
    override val items: Collection<ParticipantModel>,
) : CollectionModel<ParticipantModel>
