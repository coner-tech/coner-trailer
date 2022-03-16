package org.coner.trailer.io.mapper

import org.coner.trailer.*
import org.coner.trailer.client.motorsportreg.model.Assignment

class MotorsportRegParticipantMapper {

    fun toCore(
        peopleByMotorsportRegMemberId: Map<String, Person>,
        allClassesByAbbreviation: Map<String, Class>,
        motorsportRegAssignment: Assignment
    ): Participant {
        val person = peopleByMotorsportRegMemberId[motorsportRegAssignment.motorsportRegMemberId]
        return Participant(
            person = person,
            firstName = motorsportRegAssignment.firstName,
            lastName = motorsportRegAssignment.lastName,
            car = Car(
                model = motorsportRegAssignment.vehicleModel,
                color = motorsportRegAssignment.vehicleColor
            ),
            classing = toCoreClassing(
                allClassesByAbbreviation = allClassesByAbbreviation,
                motorsportRegAssignment = motorsportRegAssignment
            ),
            number = motorsportRegAssignment.vehicleNumber,
            seasonPointsEligible = true,
            sponsor = motorsportRegAssignment.sponsor
        )
    }

    fun toCoreClassing(
        allClassesByAbbreviation: Map<String, Class>,
        motorsportRegAssignment: Assignment
    ): Classing? {
        return Classing(
            group = allClassesByAbbreviation[motorsportRegAssignment.classModifierShort],
            handicap = allClassesByAbbreviation[motorsportRegAssignment.classShort]
                ?: return null
        )
    }
}