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
        at.addRow(expandToRow(event.name))
        at.addRow(expandToRow(report.type.title))
        at.addRow(columns.map { column -> column.header.invoke(report.type) })
        partial(event, report).invoke(at)
        at.addRule()
        return at.render()
    }

    protected fun expandToRow(vararg texts: String): Collection<String?> {
        return columns.indices
            .map { index -> texts.getOrElse(index) { "" } }
            .reversed()
    }

}