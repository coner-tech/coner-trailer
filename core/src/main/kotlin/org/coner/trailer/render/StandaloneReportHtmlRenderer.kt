package org.coner.trailer.render

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.Event
import org.coner.trailer.eventresults.ResultsReport

class StandaloneReportHtmlRenderer : Renderer {

    fun renderEventResults(event: Event, resultsReport: ResultsReport, resultsPartial: HtmlBlockTag.() -> Unit): String = createHTMLDocument()
        .html {
            fun titleText() = "${event.name} - ${event.date} - ${resultsReport.type.title}"
            head {
                bootstrapMetaViewport()
                bootstrapLinkCss()
                title { + titleText() }
            }
            body {
                id = "standalone-event-results"
                classes = setOf("container-md")
                h2 { + event.name }
                resultsPartial(this)
            }
        }.serialize()
}