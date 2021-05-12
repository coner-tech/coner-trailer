package org.coner.trailer.render

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.eventresults.OverallResultsReport

class OverallResultsReportHtmlRenderer(
    private val columns: List<ResultsReportColumn>
) : Renderer {

    fun render(report: OverallResultsReport): String = createHTMLDocument()
        .html {
            body {
                partial(report)()
            }
        }.serialize()

    fun partial(report: OverallResultsReport): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("results-report", report.type.key)
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