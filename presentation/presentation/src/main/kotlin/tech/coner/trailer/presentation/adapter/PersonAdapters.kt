package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Person
import tech.coner.trailer.io.constraint.PersonPersistConstraints
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel

class PersonIdStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.id.toString()
    }
}

class PersonFirstNameStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.firstName
    }
}

class PersonLastNameStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.lastName
    }
}

class PersonFullNameStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Person> {
    override fun invoke(model: Person): String {
        return "${model.firstName} ${model.lastName}".trim()
    }
}

class PersonClubMemberIdStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.clubMemberId ?: ""
    }
}

class PersonMotorsportRegMemberIdStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Person> {
    override operator fun invoke(model: Person): String {
        return model.motorsportReg?.memberId ?: ""
    }
}

class PersonDetailModelAdapter(
    val id: PersonIdStringFieldAdapter,
    val firstName: PersonFirstNameStringFieldAdapter,
    val lastName: PersonLastNameStringFieldAdapter,
    val clubMemberId: PersonClubMemberIdStringFieldAdapter,
    val motorsportRegMemberId: PersonMotorsportRegMemberIdStringFieldAdapter,
    val personPersistConstraints: PersonPersistConstraints
) : tech.coner.trailer.presentation.library.adapter.Adapter<Person, PersonDetailModel> {
    override fun invoke(model: Person): PersonDetailModel {
        return PersonDetailModel(
            original = model,
            constraints = personPersistConstraints,
            adapter = this
        )
    }
}

class PersonCollectionModelAdapter(
    private val personAdapter: PersonDetailModelAdapter
) : tech.coner.trailer.presentation.library.adapter.Adapter<Collection<Person>, PersonCollectionModel> {
    override fun invoke(model: Collection<Person>): PersonCollectionModel {
        return PersonCollectionModel(
            items = model.map(personAdapter::invoke)
        )
    }
}