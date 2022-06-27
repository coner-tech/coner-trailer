package tech.coner.trailer

import assertk.Assert
import assertk.assertions.*

fun Assert<Participant>.person() = prop("person") { it.person }

fun Assert<Participant>.firstName() = prop("firstName") { it.firstName }
fun Assert<Participant>.hasFirstName(expected: String) = firstName().isEqualTo(expected)

fun Assert<Participant>.lastName() = prop("lastName") { it.lastName }
fun Assert<Participant>.hasLastName(expected: String) = lastName().isEqualTo(expected)

fun Assert<Participant>.signage() = prop(Participant::signage)

fun Assert<Participant>.car() = prop("car") { it.car }

fun Assert<Participant>.seasonPointsEligible() = prop("seasonPointsEligible") { it.seasonPointsEligible }
