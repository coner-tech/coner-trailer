package org.coner.trailer.render.asciitable

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.eventresults.ResultsReport
import org.coner.trailer.render.ResultsReportRenderer

abstract class AsciiTableResultsReportRenderer<RR : ResultsReport>(
    protected val columns: List<AsciiTableResultsReportColumn>
) : ResultsReportRenderer<RR, String, () -> String> {

    override fun render(event: Event, report: RR): String {
        return buildString {
            appendReportHeader(event, report)
            append(partial(event, report).invoke())
            appendLine()
        }
    }

    private fun StringBuilder.appendReportHeader(event: Event, report: RR) {
        appendLine(event.name)
        appendLine(event.date)
        appendLine(report.type.title)
        appendLine()
    }

    protected fun createAsciiTableWithHeaderRow(report: RR): AsciiTable {
        return AsciiTable().also { at ->
            at.renderer.cwc = CWC_LongestLine()
            at.addRule()
            at.addRow(columns.map { column -> column.header(report.type) })
            at.addRule()
        }
    }

}