package org.coner.trailer.io.service

import org.coner.trailer.Person
import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.client.motorsportreg.model.GetMembersResponse
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import java.util.*

class MotorsportRegService(
        private val authenticatedApi: AuthenticatedMotorsportRegApi,
        private val personService: PersonService,
        private val motorsportRegPersonMapper: MotorsportRegPersonMapper
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

    fun importMembersAsPeople(dry: Boolean): ImportMembersAsPeopleResult {
        val members = fetchMembers()
        val motorsportRegMemberIdToMember: Map<String, GetMembersResponse.Member> = members.map { it.id to it }.toMap()
        val currentPeople = personService.list()
        val updatePeople = currentPeople.mapNotNull { person ->
            val motorsportRegMemberId = person.motorsportReg?.memberId ?: return@mapNotNull null
            val motorsportRegMember = motorsportRegMemberIdToMember[motorsportRegMemberId] ?: return@mapNotNull null
            val updated = motorsportRegPersonMapper.updateCore(person, motorsportRegMember)
            when {
                updated != person -> updated
                else -> null
            }
        }
        val currentMotorsportRegMemberIds: Set<String> = currentPeople.mapNotNull { it.motorsportReg?.memberId }.toSet()
        val createPeople = members.filterNot { currentMotorsportRegMemberIds.contains(it.id) }
                .map(motorsportRegPersonMapper::fromMotorsportReg)
        if (!dry) {
            updatePeople.forEach { personService.update(it) }
            createPeople.forEach { personService.create(it) }
        }
        return ImportMembersAsPeopleResult(
                updated = updatePeople,
                created = createPeople
        )
    }

    data class ImportMembersAsPeopleResult(
            val updated: List<Person>,
            val created: List<Person>
    )
}