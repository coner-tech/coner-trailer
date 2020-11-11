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
                signage = Participant.Signage(
                    grouping = groupingMapper.map(fromRegistration),
                    number = fromRegistration.number
                ),
                seasonPointsEligible = withPerson != null
        )
    }
}