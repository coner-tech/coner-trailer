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
            category = when (val grouping = participant.signage.grouping) {
                is Grouping.Singular -> null
                is Grouping.Paired -> grouping.pair.first.let {
                    ClassDefinition(
                        abbreviation = it.abbreviation,
                        name = it.name,
                        groupName = "not supported",
                        paxed = true,
                        paxFactor = BigDecimal.ONE, // not supported
                        exclude = false // not supported
                    )
                }
            },
            handicap = when (val grouping = participant.signage.grouping) {
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
                is Grouping.Paired -> grouping.pair.second.let {
                    ClassDefinition(
                        abbreviation = it.abbreviation,
                        name = it.name,
                        groupName = "not supported",
                        paxed = false,
                        paxFactor = BigDecimal.ONE, // not supported
                        exclude = false // not supported
                    )
                }
            },
            number = participant.signage.number,
            firstName = participant.firstName,
            lastName = participant.lastName,
            carColor = participant.car.color,
            carModel = participant.car.model,
            rawResult = rawResult ?: RegistrationResult("", null),
            paxResult = paxResult ?: RegistrationResult("", null),
            classResult = classResult ?: RegistrationResult("", null),
            runs = runs ?: emptyList(),
            bestRun = bestRun
        )
    }
}