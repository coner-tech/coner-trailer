package org.coner.trailer.datasource.crispyfish

import org.coner.trailer.Car
import org.coner.trailer.Class
import org.coner.trailer.Participant
import org.coner.trailer.Person
import tech.coner.crispyfish.model.Registration

class CrispyFishParticipantMapper(
    private val crispyFishClassingMapper: CrispyFishClassingMapper
) {

    fun toCore(
        allClassesByAbbreviation: Map<String, Class>,
        fromRegistration: Registration,
        withPerson: Person?
    ): Participant {
        return Participant(
            person = withPerson,
            firstName = fromRegistration.firstName,
            lastName = fromRegistration.lastName,
            car = Car(
                model = fromRegistration.carModel,
                color = fromRegistration.carColor
            ),
            number = fromRegistration.number,
            classing = crispyFishClassingMapper.toCore(
                allClassesByAbbreviation = allClassesByAbbreviation,
                cfRegistration = fromRegistration
            ),
            seasonPointsEligible = withPerson != null,
            sponsor = fromRegistration.sponsor
        )
    }
}