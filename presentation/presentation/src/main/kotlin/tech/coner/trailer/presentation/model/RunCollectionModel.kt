package tech.coner.trailer.presentation.model

import tech.coner.trailer.presentation.library.model.CollectionModel

class RunCollectionModel(
    override val items: Collection<RunModel>
) : CollectionModel<RunModel>
