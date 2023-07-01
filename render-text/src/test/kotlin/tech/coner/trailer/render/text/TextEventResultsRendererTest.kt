package tech.coner.trailer.render.text

import assertk.assertThat
import assertk.assertions.startsWith
import org.junit.jupiter.api.Test
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.TestOverallRawEventResults
import tech.coner.trailer.render.text.eventresults.TextEventResultsRenderer

class TextEventResultsRendererTest {

    lateinit var renderer: TextEventResultsRenderer<EventResults>

    @Test
    fun `It should include header`() {
        renderer = object : TextEventResultsRenderer<EventResults>(
            signageRenderer = { "signage" },
            participantNameRenderer = { "participantName" },
            carModelRenderer = { "carModel" },
            participantResultScoreRenderer = { "participantResultScore" },
            participantResultDiffRenderer = { "participantResultDiff" }
        ) {
            override fun StringBuilder.renderPartial(model: EventResults) {
                // not a concern under test
            }
        }
        val eventResults = TestOverallRawEventResults.Lscc2019Simplified.points1
        val eventContext = eventResults.eventContext

        val actual = renderer.render(eventResults)

        assertThat(actual)
            .startsWith("""
                ${eventContext.event.name}
                ${eventContext.event.date}
                ${eventResults.type.title}
                
            """.trimIndent())
    }
}