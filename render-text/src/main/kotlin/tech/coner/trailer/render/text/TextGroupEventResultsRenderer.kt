package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Class
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.GroupEventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.GroupEventResultsRenderer

class TextGroupEventResultsRenderer(
    columns: List<TextEventResultsColumn>
) : TextEventResultsRenderer<GroupEventResults>(columns),
    GroupEventResultsRenderer<String, () -> String> {

    override fun partial(event: Event, results: GroupEventResults): () -> String = {
        val sb = StringBuilder()
        for ((group, participantResults) in results.groupParticipantResults) {
            appendGroupResults(results, group, participantResults, sb)
        }
        appendTopTimes(results, sb)
        sb.toString()
    }

    private fun appendGroupResults(results: GroupEventResults, group: Class, participantResults: List<ParticipantResult>, sb: StringBuilder) {
        sb.appendLine(group.name)
        val at = createAsciiTableWithHeaderRow(results)
        for (result in participantResults) {
            at.addRow(columns.map { column -> column.data.invoke(result) })
        }
        at.addRule()
        sb.appendLine(at.render())
    }

    private fun appendTopTimes(results: GroupEventResults, sb: StringBuilder) {
        sb.appendLine()
        sb.appendLine("Top Times")
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Category", "Signage", "Name", "Time")
        at.addRule()
        for ((parent, topParticipantResult) in results.parentClassTopTimes) {
            at.addRow(parent.name, topParticipantResult.participant.signage?.classingNumber, renderName(topParticipantResult.participant), render(topParticipantResult.personalBestRun!!.run))
        }
        at.addRule()
        sb.appendLine(at.render())
    }
}