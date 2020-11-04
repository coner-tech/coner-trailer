package org.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop

fun Assert<Person>.id() = prop("id") { it.id }
fun Assert<Person>.hasSameIdAs(expected: Person) = id().isEqualTo(expected.id)

fun Assert<Person>.firstName() = prop("firstName") { it.firstName }
fun Assert<Person>.lastName() = prop("lastName") { it.lastName }
fun Assert<Person>.clubMemberId() = prop("clubMemberId") { it.clubMemberId }
fun Assert<Person>.motorsportReg() = prop("motorsportReg") { it.motorsportReg }

fun Assert<Person.MotorsportRegMetadata>.memberId() = prop("memberId") { it.memberId }