package tech.coner.trailer.render.text.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.eventresults.EventResultsRenderer
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.internal.*

abstract class TextEventResultsRenderer<ER : EventResults>(
    private val signageRenderer: SignageRenderer,
    private val participantNameRenderer: ParticipantNameRenderer,
    private val carModelRenderer: CarModelRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer,
    private val participantResultDiffRenderer: ParticipantResultDiffRenderer,
) : EventResultsRenderer<ER>,
    PartialRenderer<ER, StringBuilder> {

    override fun render(model: ER): String {
        return buildString {
            appendHeader(model)
            renderPartial(model)
        }
    }

    private fun StringBuilder.appendHeader(results: ER) {
        appendLine(results.eventContext.event.name)
        appendLine(results.eventContext.event.date)
        appendLine(results.type.title)
        appendLine()
    }

    protected fun createAsciiTable() = AsciiTable()
        .also { it.renderer.cwc = CWC_LongestLine() }

    protected fun AsciiTable.appendData(participantResult: ParticipantResult) {
        addRow(
            "${participantResult.position}",
            signageRenderer(participantResult.participant.signage),
            participantNameRenderer(participantResult.participant),
            carModelRenderer(participantResult.participant.car),
            participantResultScoreRenderer(participantResult),
            participantResultDiffRenderer(participantResult, ParticipantResult::diffFirst),
            participantResultDiffRenderer(participantResult, ParticipantResult::diffPrevious),
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
}