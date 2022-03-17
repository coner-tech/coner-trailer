package tech.coner.trailer.render.text

import assertk.assertThat
import assertk.assertions.startsWith
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.TestOverallRawEventResults
import org.junit.jupiter.api.Test

class TextEventResultsRendererTest {

    lateinit var renderer: TextEventResultsRenderer<EventResults>

    @Test
    fun `It should include header`() {
        renderer = object : TextEventResultsRenderer<EventResults>(emptyList()) {
            override fun partial(event: Event, results: EventResults): () -> String = {
                ""
            }
        }
        val event = TestEvents.Lscc2019Simplified.points1
        val eventResults = TestOverallRawEventResults.Lscc2019Simplified.points1

        val actual = renderer.render(event, eventResults)

        assertThat(actual)
            .startsWith("""
                ${event.name}
                ${event.date}
                ${eventResults.type.title}
                
            """.trimIndent())
    }
}