package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.TestEvents
/*
@ExtendWith(MockKExtension::class)
class IndividualEventResultsServiceTest {

    lateinit var service: IndividualEventResultsService

    @MockK lateinit var comprehensiveEventResultsService: ComprehensiveEventResultsCalculator
    @MockK lateinit var individualEventResultsFactory: IndividualEventResultsCalculator

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

        assertThat(actual).isSameInstanceAs(individualEventResults)
        verifySequence {
            comprehensiveEventResultsService.build(event)
        }
    }
}
*/
