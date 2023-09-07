package tech.coner.trailer.presentation.view.json.internal.identifier

import tech.coner.trailer.Car
import tech.coner.trailer.Participant
import tech.coner.trailer.Signage
import java.util.*

class ParticipantIdentifier(
    val personId: UUID?,
    val firstName: String?,
    val lastName: String?,
    val signage: Signage?,
    val car: Car?,
    val sponsor: String?
) {

    constructor(participant: Participant) : this(
        personId = participant.person?.id,
        firstName = participant.firstName,
        lastName = participant.lastName,
        signage = participant.signage,
        car = participant.car,
        sponsor = participant.sponsor
    )
}
