package tech.coner.trailer.presentation.model

data class ParticipantCollectionModel(
    override val items: Collection<ParticipantModel>,
) : CollectionModel<ParticipantModel>
