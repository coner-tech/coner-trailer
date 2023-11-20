package tech.coner.trailer.presentation.model

data class EventDetailCollectionModel(
    override val items: Collection<EventDetailModel>
) : CollectionModel<EventDetailModel>
