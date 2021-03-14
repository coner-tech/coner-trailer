package org.coner.trailer.io.service

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Person
import org.coner.trailer.client.motorsportreg.model.Assignment
import org.coner.trailer.datasource.snoozle.PersonResource
import org.coner.trailer.datasource.snoozle.entity.PersonEntity
import org.coner.trailer.io.constraint.PersonDeleteConstraints
import org.coner.trailer.io.constraint.PersonPersistConstraints
import org.coner.trailer.io.mapper.PersonMapper
import java.util.*
import java.util.function.Predicate
import kotlin.streams.toList

class PersonService(
        private val persistConstraints: PersonPersistConstraints,
        private val deleteConstraints: PersonDeleteConstraints,
        private val resource: PersonResource,
        private val mapper: PersonMapper
) {

    fun create(create: Person) {
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
    }

    fun findById(id: UUID): Person {
        val key = PersonEntity.Key(id = id)
        return mapper.fromSnoozle(resource.read(key))
    }

    fun list(): List<Person> {
        return resource.stream()
                .map(mapper::fromSnoozle)
                .toList()
    }

    fun search(filter: Predicate<Person>): List<Person> {
        return resource.stream()
                .parallel()
                .map(mapper::fromSnoozle)
                .filter(filter)
                .toList()
    }

    fun searchByNameFrom(registration: Registration): List<Person> {
        val firstName = registration.firstName ?: return emptyList()
        val lastName = registration.lastName ?: return emptyList()
        val filter = FilterFirstNameEquals(firstName, ignoreCase = true)
            .and(FilterLastNameEquals(lastName, ignoreCase = true))
        return search(filter)
            .sortedWith(compareBy(Person::lastName).thenBy(Person::firstName))
    }

    fun searchByClubMemberIdFrom(registration: Registration): List<Person> {
        val filter = FilterMemberIdEquals(registration.memberNumber, ignoreCase = true)
        return search(filter)
            .sortedWith(compareBy(Person::lastName).thenBy(Person::firstName))
    }

    fun searchByMotorsportRegMemberIdFrom(assignment: Assignment): Person? {
        val filter = FilterMotorsportRegMemberIdEquals(assignment.motorsportRegMemberId)
        return search(filter)
            .sortedWith(compareBy(Person::lastName).thenBy(Person::firstName))
            .firstOrNull()
    }

    fun update(person: Person) {
        persistConstraints.assess(person)
        resource.update(mapper.toSnoozle(person))
    }

    fun delete(person: Person) {
        deleteConstraints.assess(person)
        resource.delete(mapper.toSnoozle(person))
    }

    class FilterFirstNameEquals(
            private val firstNameEquals: String,
            private val ignoreCase: Boolean = true
    ) : Predicate<Person> {
        override fun test(t: Person): Boolean {
            return t.firstName.equals(firstNameEquals, ignoreCase = ignoreCase)
        }
    }

    class FilterFirstNameContains(
            private val firstNameContains: String,
            private val ignoreCase: Boolean = true
    ) : Predicate<Person> {
        override fun test(t: Person): Boolean {
            return t.firstName.contains(firstNameContains, ignoreCase = ignoreCase)
        }
    }

    class FilterLastNameEquals(
            private val lastNameEquals: String,
            private val ignoreCase: Boolean = true
    ) : Predicate<Person> {
        override fun test(t: Person): Boolean {
            return t.lastName.equals(lastNameEquals, ignoreCase = ignoreCase)
        }
    }

    class FilterLastNameContains(
            private val lastNameContains: String,
            private val ignoreCase: Boolean = true
    ) : Predicate<Person> {
        override fun test(t: Person): Boolean {
            return t.lastName.contains(lastNameContains, ignoreCase = ignoreCase)
        }
    }

    class FilterMemberIdEquals(
            private val memberIdEquals: String?,
            private val ignoreCase: Boolean = true
    ) : Predicate<Person> {
        override fun test(t: Person): Boolean {
            return when (memberIdEquals) {
                null -> t.clubMemberId == null
                else -> t.clubMemberId?.equals(memberIdEquals, ignoreCase = ignoreCase) == true
            }
        }
    }

    class FilterMemberIdContains(
            private val memberIdContains: String,
            private val ignoreCase: Boolean = true
    ) : Predicate<Person> {
        override fun test(t: Person): Boolean {
            return t.clubMemberId?.contains(memberIdContains, ignoreCase = ignoreCase) == true
        }
    }

    class FilterMotorsportRegMemberIdEquals(
        private val motorsportRegMemberId: String
    ) : Predicate<Person> {
        override fun test(t: Person): Boolean {
            return motorsportRegMemberId.equals(t.motorsportReg?.memberId, ignoreCase = true)
        }
    }

}