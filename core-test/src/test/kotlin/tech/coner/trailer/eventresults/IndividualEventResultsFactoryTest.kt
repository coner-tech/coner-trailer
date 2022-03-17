package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IndividualEventResultsFactoryTest {

    lateinit var subject: IndividualEventResultsFactory

    @BeforeEach
    fun before() {
        subject = IndividualEventResultsFactory()
    }

    @Test
    fun `It should build individual event results`() {
        val comprehensiveEventResults = ComprehensiveEventResults(
            runCount = 5,
            overallEventResults = listOf(
                TestOverallRawEventResults.Lscc2019Simplified.points1,
                TestOverallPaxEventResults.Lscc2019Simplified.points1
            ),
            groupEventResults = listOf(
                TestClazzEventResults.Lscc2019Simplified.points1
            )
        )

        val actual = subject.factory(comprehensiveEventResults)

        assertThat(actual).isEqualTo(TestIndividualEventResults.Lscc2019Simplified.points1)
    }
}