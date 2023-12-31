package tech.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameInstanceAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.TestPeople
import tech.coner.trailer.datasource.snoozle.PersonResource
import tech.coner.trailer.datasource.snoozle.entity.PersonEntity
import tech.coner.trailer.io.constraint.PersonDeleteConstraints
import tech.coner.trailer.io.constraint.PersonPersistConstraints
import tech.coner.trailer.io.mapper.PersonMapper

@ExtendWith(MockKExtension::class)
class PersonServiceTest {

    lateinit var service: PersonService

    @MockK lateinit var persistConstraints: PersonPersistConstraints
    @MockK lateinit var deleteConstraints: PersonDeleteConstraints
    @MockK lateinit var mapper: PersonMapper
    @MockK lateinit var resource: PersonResource

    @BeforeEach
    fun before() {
        service = PersonService(
                persistConstraints = persistConstraints,
                deleteConstraints = deleteConstraints,
                resource = resource,
                mapper = mapper
        )
    }

    @Test
    fun `It should create person`(
            @MockK personEntity: PersonEntity
    ) {
        val person = TestPeople.ANASTASIA_RIGLER
        every { persistConstraints.assess(person) } answers { Unit }
        every { mapper.toSnoozle(person) } returns personEntity
        every { resource.create(personEntity) } answers { Unit }

        service.create(person)

        verifySequence {
            persistConstraints.assess(person)
            mapper.toSnoozle(person)
            resource.create(personEntity)
        }
    }

    @Test
    fun `It should find person by ID`(
            @MockK personEntity: PersonEntity
    ) {
        val person = TestPeople.ANASTASIA_RIGLER
        every { resource.read(any()) } returns personEntity
        every { mapper.fromSnoozle(any()) } returns person

        val actual = service.findById(person.id)

        verifySequence {
            resource.read(match { it.id == person.id })
            mapper.fromSnoozle(personEntity)
        }
        assertThat(actual).isSameInstanceAs(person)
    }

    @Test
    fun `It should list people`() {
        val realMapper = PersonMapper()
        val peopleEntities = TestPeople.all.stream()
                .map(realMapper::toSnoozle)
        every { resource.stream() } returns peopleEntities
        val entitySlot = slot<PersonEntity>()
        every {
            mapper.fromSnoozle(capture(entitySlot))
        } answers {
            TestPeople.all.single { it.id == entitySlot.captured.id }
        }

        val actual = service.list()

        assertThat(actual).isEqualTo(TestPeople.all)
    }

    @Test
    fun `It should search people with equals filters`() {
        val person = TestPeople.ANASTASIA_RIGLER
        val filter = PersonService.FilterFirstNameEquals(person.firstName)
                .and(PersonService.FilterLastNameEquals(person.lastName))
                .and(PersonService.FilterMemberIdEquals(person.clubMemberId))
        val realMapper = PersonMapper()
        val peopleEntities = TestPeople.all.stream()
                .map(realMapper::toSnoozle)
        every { resource.stream() } returns peopleEntities
        every {
            mapper.fromSnoozle(any())
        } answers {
            val argument: PersonEntity = this.arg(0)
            TestPeople.all.single { person -> person.id == argument.id }
        }

        val actual = service.search(filter)

        assertThat(actual).isEqualTo(listOf(person))
    }

    @Test
    fun `It should search people with contains filters`() {
        val person = TestPeople.ANASTASIA_RIGLER
        val filter = PersonService.FilterFirstNameContains(person.firstName.substring(0..3))
                .and(PersonService.FilterLastNameContains(person.lastName.substring(0..3)))
                .and(PersonService.FilterMemberIdContains(person.clubMemberId!!.substringAfter("-")))
        val realMapper = PersonMapper()
        val peopleEntities = TestPeople.all.stream()
                .map(realMapper::toSnoozle)
        every { resource.stream() } returns peopleEntities
        every {
            mapper.fromSnoozle(any())
        } answers {
            val argument: PersonEntity = this.arg(0)
            TestPeople.all.single { person -> person.id == argument.id }
        }

        val actual = service.search(filter)

        assertThat(actual).isEqualTo(listOf(person))
    }
    
    @Test
    fun `It should update person`(
            @MockK personEntity: PersonEntity
    ) {
        val person = TestPeople.ANASTASIA_RIGLER
        every { persistConstraints.assess(any()) } answers { Unit }
        every { mapper.toSnoozle(any()) } returns personEntity
        every { resource.update(any()) } answers { Unit }

        service.update(person)

        verifySequence {
            persistConstraints.assess(person)
            mapper.toSnoozle(person)
            resource.update(personEntity)
        }
    }

    @Test
    fun `It should delete person`(
            @MockK personEntity: PersonEntity
    ) {
        val person = TestPeople.ANASTASIA_RIGLER
        every { deleteConstraints.assess(any()) } answers { Unit }
        every { mapper.toSnoozle(person) } returns personEntity
        every { resource.delete(any<PersonEntity>()) } answers { Unit }

        service.delete(person)

        verifySequence {
            deleteConstraints.assess(person)
            mapper.toSnoozle(person)
            resource.delete(personEntity)
        }
    }

}