package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.*
import tech.coner.trailer.Participant
import tech.coner.trailer.TestParticipants

object TestRegistrations {

    object Lscc2019Points1 {
        val REBECCA_JACKSON: Registration by lazy {
            val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
            factory(
                index = 1,
                participant = participant,
                category = null,
                handicap = TestClassDefinitions.Lscc2019.HS,
                membershipExpires = "2020-07-06",
                dateOfBirth = "0000-00-00",
                paymentAmount = "0.00",
                classResultDiff = "+0.085",
                classResultFromFirst = "+0.085",
                rawResult = RegistrationResult(time = "51.408", position = 5),
                paxResult = RegistrationResult(time = "40.098", position = 4),
                classResult = RegistrationResult(time = "51.408", position = 1),
                runs = listOf(
                    RegistrationRun(time = "52.749", penalty = null),
                    RegistrationRun(time = "53.175", penalty = null),
                    RegistrationRun(time = "52.130", penalty = null),
                    RegistrationRun(time = "52.117", penalty = null),
                    RegistrationRun(time = "51.408", penalty = null)
                ),
                bestRun = 5,
                custom = mapOf(
                    "Class 2" to null,
                    "Member" to "No",
                    "Co-Drivers" to null,
                    "" to null
                )
            )
        }
        val BRANDY_HUFF: Registration by lazy {
            factory(
                index = 4,
                participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                category = TestClassDefinitions.Lscc2019.NOV,
                handicap = TestClassDefinitions.Lscc2019.BS,
                membershipExpires = "2020-07-06",
                dateOfBirth = "0000-00-00",
                paymentAmount = "0.00",
                classResultDiff = "[-]1.889",
                classResultFromFirst = "-",
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
                bestRun = 3,
                custom = mapOf(
                    "Class 2" to null,
                    "Member" to "No",
                    "Co-Drivers" to null,
                    "" to null
                )
            )
        }
    }

    private fun factory(
        index: Int,
        participant: Participant,
        category: ClassDefinition?,
        handicap: ClassDefinition?,
        membershipExpires: String?,
        dateOfBirth: String?,
        paymentAmount: String?,
        classResultDiff: String?,
        classResultFromFirst: String?,
        rawResult: RegistrationResult?,
        paxResult: RegistrationResult?,
        classResult: RegistrationResult?,
        runs: List<RegistrationRun>?,
        bestRun: Int?,
        custom: Map<String, String?>
    ): Registration {
        return Registration(
            index = index,
            memberNumber = participant.person?.clubMemberId,
            membershipExpires = membershipExpires,
            dateOfBirth = dateOfBirth,
            signage = Signage(
                classing = Classing(
                    category = category,
                    handicap = handicap,
                ),
                number = participant.signage?.number,
            ),
            firstName = participant.firstName,
            lastName = participant.lastName,
            carColor = participant.car?.color,
            carModel = participant.car?.model,
            rawResult = rawResult,
            paxResult = paxResult,
            classResult = classResult,
            runs = runs ?: emptyList(),
            bestRun = bestRun,
            sponsor = participant.sponsor,
            paymentAmount = paymentAmount,
            classResultDiff = classResultDiff,
            classResultFromFirst = classResultFromFirst,
            custom = custom,

            // unsupported properties
            tireBrand = null,
            tireSize = null,
            region = null,
            gridNumber = null,
            age = null,
            registered = true,
            registeredCheckedIn = true,
            checkIn = false,
            onlineRegistration = false,
            paid = false,
            feeType = null,
            paymentMethod = null,
            annualTech = false,
            annualWaiver = false,
            rookie = false,
            runHeat = null,
            workHeat = null,
            workAssignment = null,
        )
    }
}