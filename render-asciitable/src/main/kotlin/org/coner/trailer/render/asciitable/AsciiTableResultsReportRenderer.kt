package org.coner.trailer.render.asciitable

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event
import org.coner.trailer.eventresults.ResultsReport
import org.coner.trailer.render.ResultsReportRenderer

abstract class AsciiTableResultsReportRenderer<RR : ResultsReport>(
    protected val columns: List<AsciiTableResultsReportColumn>
) : ResultsReportRenderer<RR, String, (AsciiTable) -> Unit> {

    override fun render(event: Event, report: RR): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        renderHeader(at, event, report)
        partial(event, report).invoke(at)
        at.addRule()
        return at.render()
    }

    private fun renderHeader(at: AsciiTable, event: Event, report: RR) {
        at.addRule()
        val eventNameRow = expandToRow(event.name)
        val eventReportTypeRow = expandToRow(report.type.title)
        at.addRow(eventNameRow)
        at.addRow(eventReportTypeRow)
        at.addRule()
        for (column in columns) {
            at.addRow(columns.map { column.header.invoke(report.type) })
        }
    }

    protected fun expandToRow(vararg columns: String): Array<String?> {
        return arrayOfNulls<String>(this.columns.size).apply {
            columns.forEachIndexed { index, column ->
                set(index, column)
            }
        }
    }
}