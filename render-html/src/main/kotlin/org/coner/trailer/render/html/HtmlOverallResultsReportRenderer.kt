package org.coner.trailer.render.html

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallResultsReport

class HtmlOverallResultsReportRenderer(
    columns: List<HtmlResultsReportColumn>
) : HtmlResultsReportRenderer<OverallResultsReport>(columns) {

    override fun partial(event: Event, report: OverallResultsReport): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("results-report", report.type.key, "event-${event.id}")
            h2 { text(report.type.title) }
            table {
                classes = setOf("table", "table-striped", "caption-top")
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
}