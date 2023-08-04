package tech.coner.trailer.presentation.model

@JvmInline
value class ParticipantCollectionModel(
    override val items: Collection<ParticipantModel>,
) : CollectionModel<ParticipantModel>
