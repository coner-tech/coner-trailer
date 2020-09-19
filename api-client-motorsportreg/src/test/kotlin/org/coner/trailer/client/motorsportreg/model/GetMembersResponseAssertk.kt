package org.coner.trailer.client.motorsportreg.model

import assertk.Assert
import assertk.assertions.prop

fun Assert<GetMembersResponse>.response() = prop("response") { it.response }

fun Assert<GetMembersResponse.Response>.members() = prop("members") { it.members}

fun Assert<GetMembersResponse.Member>.id() = prop("id") { it.id }
fun Assert<GetMembersResponse.Member>.memberId() = prop("memberId") { it.memberId }
fun Assert<GetMembersResponse.Member>.firstName() = prop("firstName") { it.firstName }
fun Assert<GetMembersResponse.Member>.lastName() = prop("lastName") { it.lastName }