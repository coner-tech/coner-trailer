package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.EventResultsRenderer

abstract class TextEventResultsRenderer<ER : EventResults>(
) : EventResultsRenderer<ER, String, () -> String> {

    override fun render(eventContext: EventContext, results: ER): String {
        return buildString {
            appendHeader(eventContext.event, results)
            appendLine(partial(eventContext, results).invoke())
        }
    }

    private fun StringBuilder.appendHeader(event: Event, results: ER) {
        appendLine(event.name)
        appendLine(event.date)
        appendLine(results.type.title)
        appendLine()
    }

    protected fun createAsciiTable() = AsciiTable()
        .also { it.renderer.cwc = CWC_LongestLine() }

    protected fun AsciiTable.appendData(participantResult: ParticipantResult) {
        addRow(
            "${participantResult.position}",
            participantResult.participant.signage?.classingNumber ?: "",
            renderName(participantResult.participant),
            participantResult.participant.car?.model ?: "",
            renderScoreColumnValue(participantResult),
            renderDiffColumnValue(participantResult, participantResult.diffFirst),
            renderDiffColumnValue(participantResult, participantResult.diffPrevious)
        )
    }

    protected fun AsciiTable.appendHeader() {
        addRule()
        addRow(
            "Pos.",
            "Signage",
            "Name",
            "Car",
            "Score",
            "Diff. 1st",
            "Diff. Prev.",
        )
        addRule()
    }

    private fun renderDiffColumnValue(participantResult: ParticipantResult, diff: Time?) = when (participantResult.position) {
        1 -> ""
        else -> render(diff)
    }

}