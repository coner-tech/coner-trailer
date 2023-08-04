package tech.coner.trailer.presentation.text.view.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.presentation.model.eventresults.EventResultsModel
import tech.coner.trailer.presentation.model.eventresults.ParticipantResultModel
import tech.coner.trailer.presentation.text.view.TextPartial
import tech.coner.trailer.presentation.text.view.TextView

abstract class TextEventResultsView<ERM : EventResultsModel<*>> : TextView<ERM>,
    TextPartial<StringBuilder, ERM> {

    override fun invoke(model: ERM): String {
        return buildString {
            appendHeader(model)
            appendModel(model)
        }
    }

    private fun StringBuilder.appendHeader(model: ERM) {
        appendLine(model.eventName)
        appendLine(model.eventDate)
        appendLine(model.eventResultsTypeTitle)
        appendLine()
    }

    protected fun createAsciiTable() = AsciiTable()
        .also { it.renderer.cwc = CWC_LongestLine() }

    protected fun AsciiTable.appendData(model: ParticipantResultModel) {
        addRow(
            model.position,
            model.signage,
            model.name,
            model.carModel,
            model.score,
            model.diffFirst,
            model.diffPrevious,
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