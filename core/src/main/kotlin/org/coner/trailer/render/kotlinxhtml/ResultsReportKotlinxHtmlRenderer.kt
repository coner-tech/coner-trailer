package org.coner.trailer.render.kotlinxhtml

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.ResultsReport
import org.coner.trailer.render.ResultsReportColumn
import org.coner.trailer.render.ResultsReportRenderer

abstract class ResultsReportKotlinxHtmlRenderer<RR : ResultsReport>(
    protected val columns: List<ResultsReportColumn>
) : ResultsReportRenderer<RR, String, HtmlBlockTag.() -> Unit>, KotlinxHtmlRenderer {

    override fun render(event: Event, report: RR): String = createHTMLDocument()
        .html {
            fun titleText() = "${event.name} - ${event.date} - ${report.type.title}"
            head {
                bootstrapMetaViewport()
                bootstrapLinkCss()
                title { + titleText() }
                style { unsafe { raw(headerStylesheet(report)) } }
            }
            body {
                id = "event-results-report"
                classes = setOf("container-xxl")
                h2 { + event.name }
                partial(report)()
            }
        }.serialize()

    private fun headerStylesheet(report: RR): String {
        return columns
            .flatMap { it.buildStyles(report) }
            .distinct()
            .joinToString(separator = "\n")
    }
}