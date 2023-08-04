package tech.coner.trailer.presentation.model

@JvmInline
value class EventDetailCollectionModel(
    override val items: Collection<EventDetailModel>
) : CollectionModel<EventDetailModel>
