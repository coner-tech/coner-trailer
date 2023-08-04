package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Person
import tech.coner.trailer.io.constraint.PersonPersistConstraints
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel

class PersonIdStringFieldAdapter : StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.id.toString()
    }
}

class PersonFirstNameStringFieldAdapter : StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.firstName
    }
}

class PersonLastNameStringFieldAdapter : StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.lastName
    }
}

class PersonFullNameStringFieldAdapter : StringFieldAdapter<Person> {
    override fun invoke(model: Person): String {
        return "${model.firstName} ${model.lastName}".trim()
    }
}

class PersonClubMemberIdStringFieldAdapter : StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.clubMemberId ?: ""
    }
}

class PersonMotorsportRegMemberIdStringFieldAdapter : StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.motorsportReg?.memberId ?: ""
    }
}

class PersonDetailModelAdapter(
    val id: PersonIdStringFieldAdapter,
    val firstName: PersonFirstNameStringFieldAdapter,
    val lastName: PersonLastNameStringFieldAdapter,
    val clubMemberId: PersonClubMemberIdStringFieldAdapter,
    val motorsportRegId: PersonMotorsportRegMemberIdStringFieldAdapter,
    val personPersistConstraints: PersonPersistConstraints
) : Adapter<Person, PersonDetailModel> {
    override fun invoke(model: Person): PersonDetailModel {
        return PersonDetailModel(
            original = model,
            adapter = this,
            constraints = personPersistConstraints
        )
    }
}

class PersonCollectionModelAdapter(
    private val personAdapter: PersonDetailModelAdapter
) : Adapter<Collection<Person>, PersonCollectionModel> {
    override fun invoke(model: Collection<Person>): PersonCollectionModel {
        return PersonCollectionModel(
            items = model.map(personAdapter::invoke)
        )
    }
}