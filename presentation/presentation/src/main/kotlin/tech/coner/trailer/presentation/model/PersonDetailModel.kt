package tech.coner.trailer.presentation.model

import tech.coner.trailer.Person
import tech.coner.trailer.io.constraint.PersonPersistConstraints
import tech.coner.trailer.presentation.adapter.PersonDetailModelAdapter
import tech.coner.trailer.presentation.library.model.BaseItemModel
import java.util.*

class PersonDetailModel(
    override val item: Person,
    override val constraints: PersonPersistConstraints,
    private val adapter: PersonDetailModelAdapter
) : BaseItemModel<Person, PersonPersistConstraints>() {

    val id
        get() = adapter.id(pendingItem)
    fun setId(id: UUID) {
        update { it.copy(id = id) }
    }

    val firstName
        get() = adapter.firstName(pendingItem)
    fun setFirstName(firstName: String) {
        update { it.copy(firstName = firstName) }
    }

    val lastName
        get() = adapter.lastName(pendingItem)
    fun setLastName(lastName: String) {
        update { it.copy(lastName = lastName) }
    }

    val clubMemberId
        get() = adapter.clubMemberId(pendingItem)
    fun setClubMemberId(clubMemberId: String) {
        update { it.copy(clubMemberId = clubMemberId) }
    }

    val motorsportRegId
        get() = adapter.motorsportRegMemberId(pendingItem)
    fun setMotorsportRegId(motorsportRegId: String) {
        update { it.copy(motorsportReg = Person.MotorsportRegMetadata(motorsportRegId)) }
    }
}