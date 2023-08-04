package tech.coner.trailer.presentation.model

class PersonCollectionModel(
    override val items: Collection<PersonDetailModel>
) : CollectionModel<PersonDetailModel>
