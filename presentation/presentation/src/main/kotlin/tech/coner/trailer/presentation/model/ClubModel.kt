package tech.coner.trailer.presentation.model

import tech.coner.trailer.Club
import tech.coner.trailer.io.constraint.ClubPersistConstraints
import tech.coner.trailer.presentation.adapter.ClubModelAdapter
import tech.coner.trailer.presentation.library.model.BaseItemModel

class ClubModel(
    override val original: Club,
    override val constraints: ClubPersistConstraints,
    private val adapter: ClubModelAdapter
) : BaseItemModel<Club, ClubPersistConstraints>() {

    val nameValidated = validatedPropertyFlow(Club::name) { adapter.name(it) }
    var name
        get() = adapter.name(itemValue)
        set(value) = update { it.copy(name = value) }
}