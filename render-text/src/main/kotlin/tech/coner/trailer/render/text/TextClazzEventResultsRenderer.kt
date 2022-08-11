package tech.coner.trailer.render.text

import tech.coner.trailer.Class
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.ClazzEventResultsRenderer

class TextClazzEventResultsRenderer(
    columns: List<TextEventResultsColumn>
) : TextEventResultsRenderer<ClazzEventResults>(columns),
    ClazzEventResultsRenderer<String, () -> String> {

    override fun partial(event: Event, results: ClazzEventResults): () -> String = {
        val sb = StringBuilder()
        for ((group, participantResults) in results.groupParticipantResults) {
            appendGroupResults(results, group, participantResults, sb)
        }
        sb.toString()
    }

    private fun appendGroupResults(
        results: ClazzEventResults,
        group: Class,
        participantResults: List<ParticipantResult>,
        sb: StringBuilder
    ) {
        sb.appendLine(group.name)
        val at = createAsciiTableWithHeaderRow(results)
        for (result in participantResults) {
            at.addRow(columns.map { column -> column.data.invoke(result) })
        }
        at.addRule()
        sb.appendLine(at.render())
    }


}