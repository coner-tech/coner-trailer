package org.coner.trailer

import assertk.Assert
import assertk.assertions.*

fun Assert<Participant>.person() = prop("person") { it.person }
fun Assert<Participant>.hasPerson(expected: Person) = person().isEqualTo(expected)
fun Assert<Participant>.doesNotHavePerson() = person().isNull()

fun Assert<Participant>.firstName() = prop("firstName") { it.firstName }
fun Assert<Participant>.hasFirstName(expected: String) = firstName().isEqualTo(expected)

fun Assert<Participant>.lastName() = prop("lastName") { it.lastName }
fun Assert<Participant>.hasLastName(expected: String) = lastName().isEqualTo(expected)

fun Assert<Participant>.grouping() = prop("") { it.grouping }
fun Assert<Participant>.hasGrouping(expected: Grouping) = grouping().isEqualTo(expected)

fun Assert<Participant>.number() = prop("number") { it.number }
fun Assert<Participant>.hasNumber(expected: String) = number().isEqualTo(expected)

fun Assert<Participant>.car() = prop("car") { it.car }
fun Assert<Participant>.hasCar(expected: Car) = car().isEqualTo(expected)

fun Assert<Participant>.seasonPointsEligible() = prop("seasonPointsEligible") { it.seasonPointsEligible }
fun Assert<Participant>.isSeasonPointsEligible() = seasonPointsEligible().isTrue()
fun Assert<Participant>.isNotSeasonPointsEligible() = seasonPointsEligible().isFalse()
