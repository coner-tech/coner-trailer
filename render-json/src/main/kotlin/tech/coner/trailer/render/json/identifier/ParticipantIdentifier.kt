package tech.coner.trailer.render.json.identifier

import tech.coner.trailer.Car
import tech.coner.trailer.Participant
import java.util.*

class ParticipantIdentifier(
    val personId: UUID?,
    val firstName: String?,
    val lastName: String?,
    val signage: String?,
    val car: Car?,
    val sponsor: String?
) {

    constructor(participant: Participant) : this(
        personId = participant.person?.id,
        firstName = participant.firstName,
        lastName = participant.lastName,
        signage = participant.signage?.classingNumber,
        car = participant.car,
        sponsor = participant.sponsor
    )
}
