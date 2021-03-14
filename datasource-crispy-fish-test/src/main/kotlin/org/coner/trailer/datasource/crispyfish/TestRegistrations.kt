package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.TestParticipants
import java.math.BigDecimal

object TestRegistrations {

    object Lscc2019Points1 {
        val REBECCA_JACKSON: Registration by lazy {
            factory(
                    participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
                    rawResult = RegistrationResult(time = "51.408", position = 63),
                    paxResult = RegistrationResult(time = "40.098", position = 44),
                    classResult = RegistrationResult(time = "51.408", position = 3),
                    runs = listOf(
                        RegistrationRun(time = "52.749", penalty = null),
                        RegistrationRun(time = "53.175", penalty = null),
                        RegistrationRun(time = "52.130", penalty = null),
                        RegistrationRun(time = "52.117", penalty = null),
                        RegistrationRun(time = "51.408", penalty = null)
                    ),
                    bestRun = 5
            )
        }
        val BRANDY_HUFF: Registration by lazy {
            factory(
                    participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                    rawResult = RegistrationResult(time = "48.515", position = 35),
                    paxResult = RegistrationResult(time = "39.297", position = 30),
                    classResult = RegistrationResult(time = "39.297", position = 4),
                    runs = listOf(
                        RegistrationRun(time = "49.419", penalty = RegistrationRun.Penalty.Cone(4)),
                        RegistrationRun(time = "49.848", penalty = RegistrationRun.Penalty.Cone(3)),
                        RegistrationRun(time = "48.515", penalty = null),
                        RegistrationRun(time = "49.076", penalty = RegistrationRun.Penalty.Cone(1)),
                        RegistrationRun(time = "49.436", penalty = null)
                    ),
                    bestRun = 3
            )
        }
    }

    private fun factory(
        participant: Participant,
        rawResult: RegistrationResult?,
        paxResult: RegistrationResult?,
        classResult: RegistrationResult?,
        runs: List<RegistrationRun>?,
        bestRun: Int?
    ): Registration {
        return Registration(
            memberNumber = participant.person?.clubMemberId,
            membershipExpires = null,
            category = when (val grouping = participant.signage?.grouping) {
                is Grouping.Singular -> null
                is Grouping.Paired -> grouping.pair.first?.let { category ->
                    ClassDefinition(
                        abbreviation = category.abbreviation,
                        name = category.name,
                        groupName = "not supported",
                        paxed = true,
                        paxFactor = BigDecimal.ONE, // not supported
                        exclude = false // not supported
                    )
                }
                else -> null
            },
            handicap = when (val grouping = participant.signage?.grouping) {
                is Grouping.Singular -> grouping.let {
                    ClassDefinition(
                        abbreviation = it.abbreviation,
                        name = it.name,
                        groupName = "not supported",
                        paxed = false,
                        paxFactor = BigDecimal.ONE, // not supported
                        exclude = false // not supported
                    )
                }
                is Grouping.Paired -> grouping.pair.second?.let { handicap ->
                    ClassDefinition(
                        abbreviation = handicap.abbreviation,
                        name = handicap.name,
                        groupName = "not supported",
                        paxed = false,
                        paxFactor = BigDecimal.ONE, // not supported
                        exclude = false // not supported
                    )
                }
                else -> null
            },
            number = participant.signage?.number,
            firstName = participant.firstName,
            lastName = participant.lastName,
            carColor = participant.car.color,
            carModel = participant.car.model,
            rawResult = rawResult,
            paxResult = paxResult,
            classResult = classResult,
            runs = runs ?: emptyList(),
            bestRun = bestRun,
            sponsor = participant.sponsor,

            // unsupported properties
            tireBrand = null,
            tireSize = null,
            region = null,
            gridNumber = null,
            dateOfBirth = null,
            age = null,
            registered = true,
            registeredCheckedIn = true,
            checkIn = true,
            onlineRegistration = true,
            paid = true,
            feeType = null,
            paymentMethod = null,
            paymentAmount = null,
            annualTech = false,
            annualWaiver = false,
            rookie = false,
            runHeat = null,
            workHeat = null,
            workAssignment = null,
            classResultDiff = null,
            classResultFromFirst = null,
            custom = emptyMap()
        )
    }
}