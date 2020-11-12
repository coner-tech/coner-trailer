package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Car
import org.coner.trailer.Participant
import org.coner.trailer.Person

class ParticipantMapper(
        private val groupingMapper: GroupingMapper
) {

    fun map(fromRegistration: Registration, withPerson: Person?): Participant {
        return Participant(
                person = withPerson,
                firstName = fromRegistration.firstName,
                lastName = fromRegistration.lastName,
                car = Car(
                        model = fromRegistration.carModel,
                        color = fromRegistration.carColor
                ),
                signage = toCoreSignage(fromRegistration),
                seasonPointsEligible = withPerson != null
        )
    }

    fun toCoreSignage(crispyFish: Registration): Participant.Signage {
        return Participant.Signage(
                grouping = groupingMapper.map(crispyFish),
                number = crispyFish.number
        )
    }
}