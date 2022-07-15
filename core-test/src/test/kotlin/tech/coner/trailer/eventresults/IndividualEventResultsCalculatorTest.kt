package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.*

@ExtendWith(MockKExtension::class)
class IndividualEventResultsCalculatorTest {

    lateinit var subject: IndividualEventResultsCalculator

    @MockK lateinit var comprehensiveEventResultsCalculator: ComprehensiveEventResultsCalculator

    @BeforeEach
    fun before() {

        subject = IndividualEventResultsCalculator(
            eventContext = TestEventContexts.Lscc2019Simplified.points1,
            comprehensiveEventResultsCalculator = comprehensiveEventResultsCalculator
        )
    }

    @Test
    fun `It should build individual event results`() {
        val comprehensiveEventResults = ComprehensiveEventResults(
            eventContext = TestEventContexts.Lscc2019Simplified.points1,
            runCount = 5,
            overallEventResults = listOf(
                TestOverallRawEventResults.Lscc2019Simplified.points1,
                TestOverallPaxEventResults.Lscc2019Simplified.points1
            ),
            clazzEventResults = TestClazzEventResults.Lscc2019Simplified.points1,
            topTimesEventResults = TestTopTimesEventResults.Lscc2019Simplified.points1
        )
        every { comprehensiveEventResultsCalculator.calculate() } returns comprehensiveEventResults

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(TestIndividualEventResults.Lscc2019Simplified.points1)
    }
}