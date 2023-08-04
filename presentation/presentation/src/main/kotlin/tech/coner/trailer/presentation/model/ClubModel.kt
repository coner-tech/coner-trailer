package tech.coner.trailer.presentation.model

import tech.coner.trailer.Club
import tech.coner.trailer.io.constraint.ClubPersistConstraints
import tech.coner.trailer.presentation.adapter.ClubModelAdapter
import tech.coner.trailer.presentation.model.util.ItemModel

class ClubModel(
    override val original: Club,
    override val adapter: ClubModelAdapter,
    override val constraints: ClubPersistConstraints
) : ItemModel<Club, ClubPersistConstraints, ClubModelAdapter>() {

    val nameValidated = validatedPropertyFlow(Club::name) { adapter.name(it) }
    var name
        get() = adapter.name(itemValue)
        set(value) = updateItem { it.copy(name = value) }
}