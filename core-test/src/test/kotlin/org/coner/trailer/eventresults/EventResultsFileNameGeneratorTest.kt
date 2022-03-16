package org.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.coner.trailer.TestEvents
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EventResultsFileNameGeneratorTest {

    lateinit var generator: EventResultsFileNameGenerator

    @BeforeEach
    fun before() {
        generator = EventResultsFileNameGenerator()
    }

    @Test
    fun `It should build a sensible filename for event results`() {
        val event = TestEvents.Lscc2019.points1

        val actual = generator.build(
            event = event,
            type = StandardEventResultsTypes.raw,
            extension = "txt"
        )

        val expected = "2019-03-03 2019 lscc points event #1 raw.txt"
        assertThat(actual).isEqualTo(expected)
    }
}