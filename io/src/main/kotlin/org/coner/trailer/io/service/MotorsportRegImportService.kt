package org.coner.trailer.io.service

import org.coner.trailer.Person
import org.coner.trailer.client.motorsportreg.model.Member
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import java.util.function.Predicate

class MotorsportRegImportService(
        private val personService: PersonService,
        private val motorsportRegMemberService: MotorsportRegMemberService,
        private val motorsportRegPersonMapper: MotorsportRegPersonMapper
) {

    fun importMembersAsPeople(dry: Boolean): ImportMembersAsPeopleResult {
        val members = motorsportRegMemberService.list()
        val motorsportRegMemberIdToMember: Map<String, Member> = members.map { it.id to it }.toMap()
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

    fun importSingleMemberAsPerson(motorsportRegMemberId: String, dry: Boolean): ImportMembersAsPeopleResult {
        val member = motorsportRegMemberService.findById(motorsportRegMemberId)
        val people = personService.search { it.motorsportReg?.memberId == motorsportRegMemberId }
        check(people.size in 0..1) { "Expected 0 or 1 matching people. Found ${people.size} matches." }
        val person = people.singleOrNull()
        val update = person?.let { motorsportRegPersonMapper.updateCore(core = it, motorsportRegMember = member) }
        val create = if (person == null) {
            motorsportRegPersonMapper.fromMotorsportReg(motorsportRegMember = member)
        } else {
            null
        }
        if (!dry) {
            if (update != null) personService.update(update)
            if (create != null) personService.create(create)
        }
        return ImportMembersAsPeopleResult(
                updated = update?.let { listOf(it) } ?: emptyList(),
                created = create?.let { listOf(it) } ?: emptyList()
        )
    }

    data class ImportMembersAsPeopleResult(
            val updated: List<Person>,
            val created: List<Person>
    )
}