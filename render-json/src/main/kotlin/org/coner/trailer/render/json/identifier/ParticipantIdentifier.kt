package org.coner.trailer.render.json.identifier

import org.coner.trailer.Car
import org.coner.trailer.Participant
import java.util.*

class ParticipantIdentifier(
    val personId: UUID?,
    val firstName: String?,
    val lastName: String?,
    val signage: String?,
    val car: Car,
    val sponsor: String?
) {

    constructor(participant: Participant) : this(
        personId = participant.person?.id,
        firstName = participant.firstName,
        lastName = participant.lastName,
        signage = participant.signage?.toAbbreviatedString(),
        car = participant.car,
        sponsor = participant.sponsor
    )
}
