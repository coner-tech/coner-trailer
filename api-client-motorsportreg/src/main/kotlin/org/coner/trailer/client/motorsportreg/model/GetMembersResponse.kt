package org.coner.trailer.client.motorsportreg.model

data class GetMembersResponse(
        val response: Response
) {

    data class Response(
            val members: List<Member>
    )

    data class Member(
            val id: String,
            val memberId: String?,
            val firstName: String,
            val lastName: String,
    )
}