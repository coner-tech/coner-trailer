package tech.coner.trailer.io.constraint

import tech.coner.trailer.Person

class PersonDeleteConstraints : Constraint<Person>() {

    override fun assess(candidate: Person) {
        // TODO: prohibit deletes on people referenced by registrations for events/seasons
        // https://github.com/caeos/coner-trailer/issues/22
    }
}
