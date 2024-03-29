package tech.coner.trailer.io.constraint

import tech.coner.trailer.Club

class ClubPersistConstraints : CompositeConstraint<Club>() {

    val nameIsNotBlank = propertyConstraint(
        property = Club::name,
        assessFn = { it.name.isNotBlank() },
        violationMessageFn = { "Name must not be blank" }
    )

    val nameIsNotTooLong = propertyConstraint(
        property = Club::name,
        assessFn = { it.name.length < Club.NAME_MAX_LENGTH },
        violationMessageFn = { "Name must contain less than ${Club.NAME_MAX_LENGTH} characters" }
    )
}