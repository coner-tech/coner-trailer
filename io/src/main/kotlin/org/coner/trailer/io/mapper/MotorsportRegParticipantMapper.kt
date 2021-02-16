package org.coner.trailer.io.mapper

import org.coner.trailer.Car
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.client.motorsportreg.model.Assignment
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import org.coner.trailer.io.service.PersonService

class MotorsportRegParticipantMapper(
    private val personService: PersonService,
    private val crispyFishGroupingMapper: CrispyFishGroupingMapper
) {

    fun toCore(
        groupingsByAbbreviation: Map<String, Grouping.Singular>,
        motorsportRegAssignment: Assignment
    ): Participant {
        val person = personService.searchByMotorsportRegMemberIdFrom(assignment = motorsportRegAssignment)
        return Participant(
            person = person,
            firstName = motorsportRegAssignment.firstName,
            lastName = motorsportRegAssignment.lastName,
            car = Car(
                model = motorsportRegAssignment.vehicleModel,
                color = motorsportRegAssignment.vehicleColor
            ),
            signage = toCoreSignage(
                groupingsByAbbreviation = groupingsByAbbreviation,
                motorsportRegAssignment = motorsportRegAssignment
            ),
            seasonPointsEligible = true
        )
    }

    fun toCoreSignage(
        groupingsByAbbreviation: Map<String, Grouping.Singular>,
        motorsportRegAssignment: Assignment
    ): Participant.Signage {
        val category = groupingsByAbbreviation[motorsportRegAssignment.classModifierShort]
        val handicap = groupingsByAbbreviation[motorsportRegAssignment.classShort] ?: Grouping.UNKNOWN
        return Participant.Signage(
            grouping = category?.let { Grouping.Paired(it to handicap) } ?: handicap,
            number = motorsportRegAssignment.vehicleNumber
        )
    }
}