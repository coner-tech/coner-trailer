package org.coner.trailer.render.kotlinxhtml

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport

class KotlinxHtmlGroupedResultsReportRenderer(
    columns: List<KotlinxHtmlResultsReportColumn>
) : KotlinxHtmlResultsReportRenderer<GroupedResultsReport>(columns) {

    override fun partial(event: Event, report: GroupedResultsReport): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("results-report", "results-report-${report.type.key}", "event-${event.id}")
            h2 { text(report.type.title) }
            table {
                classes = setOf("table", "table-striped", "caption-top")
                thead {
                    tr {
                        columns.forEach { column -> column.header(this, report.type) }
                    }
                }
                tbody {
                    report.groupingsToResultsMap.forEach { (grouping, participantResults) ->

                        tr {
                            td {
                                colSpan = "${columns.size}"
                                text(grouping.name)
                            }
                        }
                        for (result in participantResults) {
                            tr {
                                columns.forEach { column -> column.data(this, result) }
                            }
                        }
                    }
                }
            }

        }
    }
}