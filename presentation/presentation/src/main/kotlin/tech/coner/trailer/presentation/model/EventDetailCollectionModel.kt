package tech.coner.trailer.presentation.model

import tech.coner.trailer.presentation.library.model.CollectionModel

data class EventDetailCollectionModel(
    override val items: Collection<EventDetailModel>
) : CollectionModel<EventDetailModel>
