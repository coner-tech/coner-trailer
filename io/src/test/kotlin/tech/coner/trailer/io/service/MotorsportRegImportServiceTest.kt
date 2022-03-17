package tech.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import created
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import tech.coner.trailer.Person
import tech.coner.trailer.TestPeople
import tech.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import tech.coner.trailer.lastName
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import updated
import java.util.function.Predicate
import kotlin.streams.toList

@ExtendWith(MockKExtension::class)
class MotorsportRegImportServiceTest {

    lateinit var service: MotorsportRegImportService

    @MockK lateinit var personService: PersonService
    @MockK lateinit var motorsportRegMemberService: MotorsportRegMemberService
    lateinit var motorsportRegPersonMapper: MotorsportRegPersonMapper

    @BeforeEach
    fun before() {
        motorsportRegPersonMapper = MotorsportRegPersonMapper()
        service = MotorsportRegImportService(
                personService = personService,
                motorsportRegMemberService = motorsportRegMemberService,
                motorsportRegPersonMapper = motorsportRegPersonMapper
        )
    }

    @Test
    fun `It should really import members as people`() {
        val toCreate = tech.coner.trailer.client.motorsportreg.model.TestMembers.BRANDY_HUFF
        val toUpdate = tech.coner.trailer.client.motorsportreg.model.TestMembers.REBECCA_JACKSON.copy(
                lastName = "Updated"
        )
        val members = listOf(toCreate, toUpdate)
        every { motorsportRegMemberService.list() } returns members
        val people = listOf(TestPeople.REBECCA_JACKSON)
        every { personService.list() } returns people
        justRun { personService.update(any()) }
        justRun { personService.create(any()) }

        val actual = service.importMembersAsPeople(dry = false)

        assertThat(actual).all {
            created().all {
                hasSize(1)
                index(0).isEqualToIgnoringGivenProperties(TestPeople.BRANDY_HUFF, Person::id)
            }
            updated().all {
                hasSize(1)
                index(0).all {
                    isEqualToIgnoringGivenProperties(TestPeople.REBECCA_JACKSON, Person::lastName)
                }
            }
        }
        verifySequence {
            motorsportRegMemberService.list()
            personService.list()
            personService.update(any())
            personService.create(any())
        }
    }

    @Test
    fun `It should dry-run import members as people`() {
        val toCreate = tech.coner.trailer.client.motorsportreg.model.TestMembers.BRANDY_HUFF
        val toUpdate = tech.coner.trailer.client.motorsportreg.model.TestMembers.REBECCA_JACKSON.copy(
                lastName = "Updated"
        )
        val members = listOf(toCreate, toUpdate)
        every { motorsportRegMemberService.list() } returns members
        val people = listOf(TestPeople.REBECCA_JACKSON)
        every { personService.list() } returns people

        val actual = service.importMembersAsPeople(dry = true)

        assertThat(actual).all {
            created().all {
                hasSize(1)
                index(0).isEqualToIgnoringGivenProperties(TestPeople.BRANDY_HUFF, Person::id)
            }
            updated().all {
                hasSize(1)
                index(0).all {
                    isEqualToIgnoringGivenProperties(TestPeople.REBECCA_JACKSON, Person::lastName)
                }
            }
        }
        verifySequence {
            motorsportRegMemberService.list()
            personService.list()
        }
    }

    @Test
    fun `It should import single member as person`() {
        val toUpdate = tech.coner.trailer.client.motorsportreg.model.TestMembers.BRANDY_HUFF
        every {
            motorsportRegMemberService.findById(toUpdate.id)
        } returns toUpdate.copy(
                lastName = "Updated"
        )
        val personSearchFilterSlot = slot<Predicate<Person>>()
        every {
            personService.search(capture(personSearchFilterSlot))
        } answers {
            TestPeople.all.stream().filter(personSearchFilterSlot.captured).toList()
        }
        justRun { personService.update(any()) }

        val actual = service.importSingleMemberAsPerson(
                motorsportRegMemberId = toUpdate.id,
                dry = false
        )

        assertThat(actual).all {
            created().isEmpty()
            updated().all {
                hasSize(1)
                index(0).all {
                    isEqualToIgnoringGivenProperties(TestPeople.BRANDY_HUFF, Person::lastName)
                    lastName().isEqualTo("Updated")
                }
            }
        }
        verifySequence {
            motorsportRegMemberService.findById(toUpdate.id)
            personService.search(personSearchFilterSlot.captured)
            personService.update(any())
        }
    }

    @Test
    fun `It should dry-run import single member as person`() {
        val toCreate = tech.coner.trailer.client.motorsportreg.model.TestMembers.REBECCA_JACKSON
        every { motorsportRegMemberService.findById(toCreate.id) } returns toCreate
        every { personService.search(any()) } returns emptyList()
        justRun { personService.create(any()) }

        val actual = service.importSingleMemberAsPerson(
                motorsportRegMemberId = toCreate.id,
                dry = true
        )

        assertThat(actual).all {
            created().all {
                hasSize(1)
                index(0).all {
                    isEqualToIgnoringGivenProperties(TestPeople.REBECCA_JACKSON, Person::id)
                }
            }
            updated().isEmpty()
        }
        verifySequence {
            motorsportRegMemberService.findById(toCreate.id)
            personService.search(any())
        }
    }
}