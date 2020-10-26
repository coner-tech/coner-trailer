package org.coner.trailer.client.motorsportreg.model

import assertk.Assert
import assertk.assertions.prop

fun Assert<GetMembersResponse>.response() = prop("response") { it.response }

fun Assert<GetMembersResponse.Response>.members() = prop("members") { it.members}

fun Assert<Member>.id() = prop("id") { it.id }
fun Assert<Member>.memberId() = prop("memberId") { it.memberId }
fun Assert<Member>.firstName() = prop("firstName") { it.firstName }
fun Assert<Member>.lastName() = prop("lastName") { it.lastName }