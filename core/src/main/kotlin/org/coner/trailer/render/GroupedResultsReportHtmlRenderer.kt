package org.coner.trailer.render

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.eventresults.GroupedResultsReport

class GroupedResultsReportHtmlRenderer(
    private val columns: List<ResultsReportColumn>
) : Renderer {

    fun render(report: GroupedResultsReport): String = createHTMLDocument()
        .html {
            body {
                partial(report)()
            }
        }.serialize()

    fun partial(report: GroupedResultsReport): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("results-report", report.type.key)
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