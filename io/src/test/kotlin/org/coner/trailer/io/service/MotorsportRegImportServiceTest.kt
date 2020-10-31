package org.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import created
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.Person
import org.coner.trailer.TestPeople
import org.coner.trailer.client.motorsportreg.model.TestMembers
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import updated

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
        val toCreate = TestMembers.BRANDY_HUFF
        val toUpdate = TestMembers.REBECCA_JACKSON.copy(
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
        val toCreate = TestMembers.BRANDY_HUFF
        val toUpdate = TestMembers.REBECCA_JACKSON.copy(
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
}