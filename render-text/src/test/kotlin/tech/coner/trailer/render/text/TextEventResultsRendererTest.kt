package tech.coner.trailer.render.text

import assertk.assertThat
import assertk.assertions.startsWith
import org.junit.jupiter.api.Test
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.TestOverallRawEventResults

class TextEventResultsRendererTest {

    lateinit var renderer: TextEventResultsRenderer<EventResults>

    @Test
    fun `It should include header`() {
        renderer = object : TextEventResultsRenderer<EventResults>() {
            override fun partial(eventContext: EventContext, results: EventResults): () -> String = {
                ""
            }
        }
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val eventResults = TestOverallRawEventResults.Lscc2019Simplified.points1

        val actual = renderer.render(eventContext, eventResults)

        assertThat(actual)
            .startsWith("""
                ${eventContext.event.name}
                ${eventContext.event.date}
                ${eventResults.type.title}
                
            """.trimIndent())
    }
}