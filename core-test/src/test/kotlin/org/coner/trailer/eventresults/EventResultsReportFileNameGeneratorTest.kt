package org.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.coner.trailer.TestEvents
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EventResultsReportFileNameGeneratorTest {

    lateinit var generator: EventResultsReportFileNameGenerator

    @BeforeEach
    fun before() {
        generator = EventResultsReportFileNameGenerator()
    }

    @Test
    fun `It should build a sensible filename for event results`() {
        val event = TestEvents.Lscc2019.points1

        val actual = generator.build(
            event = event,
            type = StandardResultsTypes.overallRawTime,
            extension = "txt"
        )

        val expected = "2019-03-03 2019 lscc points event #1 overall-raw-time.txt"
        assertThat(actual).isEqualTo(expected)
    }
}