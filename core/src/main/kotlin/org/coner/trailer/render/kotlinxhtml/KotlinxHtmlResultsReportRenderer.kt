package org.coner.trailer.render.kotlinxhtml

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.Event
import org.coner.trailer.eventresults.ResultsReport
import org.coner.trailer.render.ResultsReportRenderer

abstract class KotlinxHtmlResultsReportRenderer<RR : ResultsReport>(
    protected val columns: List<KotlinxHtmlResultsReportColumn>
) : ResultsReportRenderer<RR, String, HtmlBlockTag.() -> Unit>, KotlinxHtmlRenderer {

    override fun render(event: Event, report: RR): String = createHTMLDocument()
        .html {
            fun titleText() = "${event.name} - ${event.date} - ${report.type.title}"
            head {
                bootstrapMetaViewport()
                bootstrapLinkCss()
                title { + titleText() }
                style { unsafe { raw(headerStylesheet(event, report)) } }
            }
            body {
                id = "event-results-report"
                classes = setOf("container-xl")
                h2 { + event.name }
                partial(event, report)()
            }
        }.serialize()

    private fun headerStylesheet(event: Event, report: RR): String {
        return columns
            .flatMap { it.buildStyles(event, report) }
            .distinct()
            .joinToString(separator = "\n")
    }
}