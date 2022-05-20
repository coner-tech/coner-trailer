package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.*

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
            signage = Signage(
                classing = crispyFishClassingMapper.toCore(
                    allClassesByAbbreviation = allClassesByAbbreviation,
                    cfRegistration = fromRegistration
                ),
                number = fromRegistration.number,
            ),
            seasonPointsEligible = withPerson != null,
            sponsor = fromRegistration.sponsor
        )
    }
}