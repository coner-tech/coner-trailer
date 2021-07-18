package org.coner.trailer.render.text

import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupEventResults

class TextGroupedEventResultsRenderer(
    columns: List<TextEventResultsColumn>
) : TextEventResultsRenderer<GroupEventResults>(columns) {

    override fun partial(event: Event, results: GroupEventResults): () -> String = {
        val sb = StringBuilder()
        for ((group, participantResults) in results.groupParticipantResults) {
            sb.appendLine(group.name)
            val at = createAsciiTableWithHeaderRow(results)
            for (result in participantResults) {
                at.addRow(columns.map { column -> column.data.invoke(result) })
            }
            at.addRule()
            sb.appendLine(at.render())
        }
        sb.toString()
    }
}