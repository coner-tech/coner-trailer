package tech.coner.trailer.presentation.model

import tech.coner.trailer.presentation.library.model.CollectionModel

class PersonCollectionModel(
    override val items: Collection<PersonDetailModel>
) : CollectionModel<PersonDetailModel>
