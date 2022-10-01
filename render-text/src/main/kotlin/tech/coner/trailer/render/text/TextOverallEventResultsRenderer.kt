package tech.coner.trailer.render.text

import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.OverallEventResultsRenderer

class TextOverallEventResultsRenderer(
) : TextEventResultsRenderer<OverallEventResults>(),
    OverallEventResultsRenderer<String, () -> String> {

    override fun partial(eventContext: EventContext, results: OverallEventResults): () -> String = {
        val at = createAsciiTable()
        at.appendHeader()
        for (participantResult in results.participantResults) {
            at.appendData(participantResult)
        }
        at.addRule()
        at.render()
    }
}