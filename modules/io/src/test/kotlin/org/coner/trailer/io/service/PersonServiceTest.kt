package org.coner.trailer.io.service

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.datasource.snoozle.PersonResource
import org.coner.trailer.io.constraint.PersonPersistConstraints
import org.coner.trailer.io.mapper.PersonMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PersonServiceTest {

    lateinit var service: PersonService

    @MockK lateinit var persistConstraints: PersonPersistConstraints
    @MockK lateinit var mapper: PersonMapper
    @MockK lateinit var resource: PersonResource

    @BeforeEach
    fun before() {
        service = PersonService(
                persistConstraints = persistConstraints,
                resource = resource,
                mapper = mapper
        )
    }

    @Test
    fun `It should create person`() {
        TODO()
    }

    @Test
    fun `It should find person by ID`() {
        TODO()
    }

    @Test
    fun `It should list people`() {
        TODO()
    }

    @Test
    fun `It should search people with equals filters`() {
        TODO()
    }

    @Test
    fun `It should search people with contains filters`() {
        TODO()
    }

}