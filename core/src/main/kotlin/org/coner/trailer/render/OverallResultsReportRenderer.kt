package org.coner.trailer.render

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.coner.trailer.eventresults.OverallResultsReport

class OverallResultsReportRenderer(
    private val columns: List<OverallResultsReportColumn>
) : Renderer {

    fun renderContentOnly(report: OverallResultsReport) = createHTML()
        .div {
            id = "overall-results-report"
            table {
                caption { text(report.type.title) }
                thead {
                    tr {
                        columns.forEach { column -> column.header(this, report.type) }
                    }
                }
                tbody {
                    for (result in report.participantResults) {
                        tr {
                            columns.forEach { column -> column.data(this, result) }
                        }
                    }
                }
            }
        }
}