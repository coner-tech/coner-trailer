package org.coner.trailer.io.service

import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.client.motorsportreg.model.Member

class MotorsportRegMemberService(
        private val authenticatedApi: AuthenticatedMotorsportRegApi
) {

    fun fetchMembers(): List<Member> {
        val membersResponse = authenticatedApi.getMembers().execute()
        if (!membersResponse.isSuccessful) throw IllegalStateException(
                "Failed to fetch members, response code: ${membersResponse.code()}",

                )
        return checkNotNull(membersResponse.body()) { "Failed to fetch members" }
                .response
                .members
    }
}