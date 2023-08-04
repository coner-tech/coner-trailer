package tech.coner.trailer.presentation.model

import tech.coner.trailer.Person
import tech.coner.trailer.io.constraint.PersonPersistConstraints
import tech.coner.trailer.presentation.adapter.PersonDetailModelAdapter
import tech.coner.trailer.presentation.model.util.ItemModel

class PersonDetailModel(
    override val original: Person,
    override val adapter: PersonDetailModelAdapter,
    override val constraints: PersonPersistConstraints
) : ItemModel<Person, PersonPersistConstraints, PersonDetailModelAdapter>() {

    val id
        get() = adapter.id(itemValue)
    val firstName
        get() = adapter.firstName(itemValue)
    val lastName
        get() = adapter.lastName(itemValue)
    val clubMemberId
        get() = adapter.clubMemberId(itemValue)
    val motorsportRegId
        get() = adapter.motorsportRegId(itemValue)
}