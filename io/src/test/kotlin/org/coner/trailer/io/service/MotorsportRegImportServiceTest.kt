package org.coner.trailer.io.service

import assertk.all
import assertk.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.TestPeople
import org.coner.trailer.client.motorsportreg.model.TestMembers
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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
        val members = listOf(
                toCreate,
                toUpdate
        )
        every { motorsportRegMemberService.list() } returns members
        val people = listOf(
                TestPeople.BRANDY_HUFF,
                TestPeople.REBECCA_JACKSON
        )
        every { personService.list() } returns people
        justRun { personService.update(any()) }
        justRun { personService.create(any()) }

        val actual = service.importMembersAsPeople(dry = false)

        assertThat(actual).all {

        }
        TODO()
    }

    @Test
    fun `It should dry-run import members as people`() {
        TODO()
    }
}