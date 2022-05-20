package tech.coner.trailer.io.service

import assertk.assertThat
import assertk.assertions.isSameAs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import tech.coner.trailer.Policy
import tech.coner.trailer.TestEvents
import tech.coner.trailer.eventresults.TestClazzEventResults
import tech.coner.trailer.eventresults.TestOverallRawEventResults
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class EventResultsServiceImplTest {

    lateinit var service: EventResultsServiceImpl

    @MockK lateinit var crispyFishEventResultsService: CrispyFishEventResultsServiceImpl

    @BeforeEach
    fun before() {
        service = EventResultsServiceImpl(
            crispyFishEventResultsService = crispyFishEventResultsService
        )
    }

    @Test
    fun `It should delegate building raw results from crispy fish run source`() {
        val event = TestEvents.Lscc2019Simplified.points1
        check(event.policy.authoritativeRunSource == Policy.RunSource.CrispyFish)
        val results = TestOverallRawEventResults.Lscc2019Simplified.points1
        every { crispyFishEventResultsService.buildRawResults(any()) } returns results

        val actual = service.buildRawResults(event)

        verifySequence {
            crispyFishEventResultsService.buildRawResults(event)
        }
        assertThat(actual).isSameAs(results)
    }

    @Test
    fun `It should delegate building pax results from crispy fish run source`() {
        val event = TestEvents.Lscc2019Simplified.points1
        check(event.policy.authoritativeRunSource == Policy.RunSource.CrispyFish)
        val results = TestOverallRawEventResults.Lscc2019Simplified.points1
        every { crispyFishEventResultsService.buildPaxResults(any()) } returns results

        val actual = service.buildPaxResults(event)

        verifySequence {
            crispyFishEventResultsService.buildPaxResults(event)
        }
        assertThat(actual).isSameAs(results)
    }

    @Test
    fun `It should delegate building class results from crispy fish run source`() {
        val event = TestEvents.Lscc2019Simplified.points1
        check(event.policy.authoritativeRunSource == Policy.RunSource.CrispyFish)
        val results = TestClazzEventResults.Lscc2019Simplified.points1
        every { crispyFishEventResultsService.buildClassResults(any()) } returns results

        val actual = service.buildClassResults(event)

        verifySequence {
            crispyFishEventResultsService.buildClassResults(event)
        }
        assertThat(actual).isSameAs(results)
    }

}