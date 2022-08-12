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

    @MockK lateinit var rawEventResultsCalculator: RawEventResultsCalculator
    @MockK lateinit var paxEventResultsCalculator: PaxEventResultsCalculator
    @MockK lateinit var clazzEventResultsCalculator: ClazzEventResultsCalculator

    @BeforeEach
    fun before() {
        subject = IndividualEventResultsCalculator(
            eventContext = TestEventContexts.Lscc2019Simplified.points1,
            overallEventResultsCalculators = listOf(
                rawEventResultsCalculator,
                paxEventResultsCalculator
            ),
            clazzEventResultsCalculator = clazzEventResultsCalculator
        )
    }

    @Test
    fun `It should calculate individual event results`() {
        val comprehensiveEventResults = TestComprehensiveEventResults.Lscc2019Simplified.points1
        every { rawEventResultsCalculator.calculate() } returns comprehensiveEventResults.overallEventResults[0]
        every { paxEventResultsCalculator.calculate() } returns comprehensiveEventResults.overallEventResults[1]
        every { clazzEventResultsCalculator.calculate() } returns comprehensiveEventResults.clazzEventResults

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(TestIndividualEventResults.Lscc2019Simplified.points1)
    }
}