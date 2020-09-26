package org.coner.trailer.io.service

import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.client.motorsportreg.model.GetMembersResponse

class MotorsportRegService(
        private val authenticatedApi: AuthenticatedMotorsportRegApi,
        private val personService: PersonService
) {

    fun fetchMembers(): List<GetMembersResponse.Member> {
        val membersResponse = authenticatedApi.getMembers().execute()
        if (!membersResponse.isSuccessful) throw IllegalStateException(
                "Failed to fetch members, response code: ${membersResponse.code()}",

                )
        return checkNotNull(membersResponse.body()) { "Failed to fetch members" }
                .response
                .members
    }

    fun importMembersAsPeople() {
        val members = fetchMembers()
        val currentPeople = personService.list()
        val msrIdToPerson = currentPeople.mapNotNull {
            val msrId = it.motorsportRegMetadata?.id ?: return@mapNotNull null
            msrId to it
        }.toMap(mutableMapOf())

    }
}