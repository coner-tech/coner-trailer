package org.coner.trailer.render.kotlinxhtml

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.render.Renderer
import org.coner.trailer.render.ResultsReportColumn

class GroupedResultsReportKotlinxHtmlRenderer(
    columns: List<ResultsReportColumn>
) : ResultsReportKotlinxHtmlRenderer<GroupedResultsReport>(columns) {

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