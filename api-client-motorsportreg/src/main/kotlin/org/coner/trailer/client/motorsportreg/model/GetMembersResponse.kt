package org.coner.trailer.client.motorsportreg.model

data class GetMembersResponse(
        val response: Response
) {

    data class Response(
            val members: List<Member>
    )

}