package org.coner.trailer.io.service

import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.client.motorsportreg.model.Member

class MotorsportRegMemberService(
        private val authenticatedApi: AuthenticatedMotorsportRegApi
) {

    fun list(): List<Member> {
        val membersResponse = authenticatedApi.getMembers().execute().also {
            check(it.isSuccessful) { "Failed to fetch members. ${it.code()} ${it.message()}" }
        }
        return checkNotNull(membersResponse.body()) { "Got members response with null body" }
                .response
                .members
    }

    fun findById(id: String): Member {
        val memberByIdResponse = authenticatedApi.getMemberById(id).execute().also {
            check(it.isSuccessful) { "Failed to fetch member by ID. ${it.code()} ${it.message()}"}
        }
        return checkNotNull(memberByIdResponse.body()) { "Got member by id response with null body" }
                .response
                .member
    }


}