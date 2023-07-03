package tech.coner.trailer.render.text.view

import assertk.assertThat
import assertk.assertions.startsWith
import org.junit.jupiter.api.Test
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.TestOverallRawEventResults
import tech.coner.trailer.render.text.view.eventresults.TextEventResultsViewRenderer

class TextEventResultsViewRendererTest {

    lateinit var renderer: TextEventResultsViewRenderer<EventResults>

    @Test
    fun `It should include header`() {
        renderer = object : TextEventResultsViewRenderer<EventResults>(
            signagePropertyRenderer = { "signage" },
            participantNamePropertyRenderer = { "participantName" },
            carModelPropertyRenderer = { "carModel" },
            participantResultScoreRenderer = { "participantResultScore" },
            participantResultDiffPropertyRenderer = { "participantResultDiff" }
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