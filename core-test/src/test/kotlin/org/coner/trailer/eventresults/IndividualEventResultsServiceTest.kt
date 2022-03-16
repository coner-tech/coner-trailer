package org.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isSameAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.TestEvents
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class IndividualEventResultsServiceTest {

    lateinit var service: IndividualEventResultsService

    @MockK lateinit var comprehensiveEventResultsService: ComprehensiveEventResultsService
    @MockK lateinit var individualEventResultsFactory: IndividualEventResultsFactory

    @BeforeEach
    fun before() {
        service = IndividualEventResultsService(
            comprehensiveEventResultsService = comprehensiveEventResultsService,
            individualEventResultsFactory = individualEventResultsFactory
        )
    }

    @Test
    fun `It should generate individual results`() {
        val event = TestEvents.Lscc2019Simplified.points1
        val comprehensiveEventResults = TestComprehensiveEventResults.Lscc2019.points1
        every { comprehensiveEventResultsService.build(any()) } returns comprehensiveEventResults
        val individualEventResults: IndividualEventResults = TestIndividualEventResults.Lscc2019Simplified.points1
        every { individualEventResultsFactory.factory(any()) } returns individualEventResults

        val actual: IndividualEventResults = service.build(event)

        assertThat(actual).isSameAs(individualEventResults)
        verifySequence {
            comprehensiveEventResultsService.build(event)
        }
    }
}
