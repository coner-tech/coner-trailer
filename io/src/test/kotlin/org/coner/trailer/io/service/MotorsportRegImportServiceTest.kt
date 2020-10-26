package org.coner.trailer.io.service

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MotorsportRegImportServiceTest {

    lateinit var service: MotorsportRegImportService

    @MockK lateinit var personService: PersonService
    @MockK lateinit var motorsportRegMemberService: MotorsportRegMemberService
    @MockK lateinit var motorsportRegPersonMapper: MotorsportRegPersonMapper

    @BeforeEach
    fun before() {
        service = MotorsportRegImportService(
                personService = personService,
                motorsportRegMemberService = motorsportRegMemberService,
                motorsportRegPersonMapper = motorsportRegPersonMapper
        )
    }

    @Test
    fun `It should really import members as people`() {
        TODO()
    }

    @Test
    fun `It should dry-run import members as people`() {
        TODO()
    }
}