package tech.coner.trailer.render.text.view.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer

abstract class TextEventResultsViewRenderer<ER : EventResults>(
    private val signagePropertyRenderer: SignagePropertyRenderer,
    private val participantNamePropertyRenderer: ParticipantNamePropertyRenderer,
    private val carModelPropertyRenderer: CarModelPropertyRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer,
    private val participantResultDiffPropertyRenderer: ParticipantResultDiffPropertyRenderer,
) : EventResultsViewRenderer<ER>,
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
            signagePropertyRenderer(participantResult.participant.signage),
            participantNamePropertyRenderer(participantResult.participant),
            carModelPropertyRenderer(participantResult.participant.car),
            participantResultScoreRenderer(participantResult),
            participantResultDiffPropertyRenderer(participantResult, ParticipantResult::diffFirst),
            participantResultDiffPropertyRenderer(participantResult, ParticipantResult::diffPrevious),
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