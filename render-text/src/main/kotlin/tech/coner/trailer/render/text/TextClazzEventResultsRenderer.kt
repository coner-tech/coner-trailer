package tech.coner.trailer.render.text

import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.render.ClazzEventResultsRenderer

class TextClazzEventResultsRenderer(
) : TextEventResultsRenderer<ClazzEventResults>(),
    ClazzEventResultsRenderer<String, () -> String> {

    override fun partial(eventContext: EventContext, results: ClazzEventResults): () -> String = {
        val sb = StringBuilder()
        results.groupParticipantResults.forEach { (group, participantResults) ->
            sb.appendLine(group.name)
            val at = createAsciiTable()
            at.appendHeader()
            for (participantResult in participantResults) {
                at.appendData(participantResult)
            }
            at.addRule()
            sb.appendLine(at.render())
        }
        sb.toString()
    }
}