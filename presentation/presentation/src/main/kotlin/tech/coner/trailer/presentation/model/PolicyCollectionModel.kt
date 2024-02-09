package tech.coner.trailer.presentation.model

import tech.coner.trailer.presentation.library.model.CollectionModel

class PolicyCollectionModel(
    override val items: Collection<PolicyModel>
) : CollectionModel<PolicyModel>
