package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Car
import org.coner.trailer.Participant
import org.coner.trailer.Person

class CrispyFishParticipantMapper(
    private val crispyFishGroupingMapper: CrispyFishGroupingMapper
) {

    fun toCore(
        context: CrispyFishEventMappingContext,
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
            signage = toCoreSignage(context, fromRegistration),
            seasonPointsEligible = withPerson != null,
            sponsor = fromRegistration.sponsor
        )
    }

    fun toCoreSignage(context: CrispyFishEventMappingContext, crispyFish: Registration): Participant.Signage {
        return Participant.Signage(
            grouping = crispyFishGroupingMapper.toCore(
                context = context,
                fromRegistration = crispyFish
            ),
            number = crispyFish.number
        )
    }
}