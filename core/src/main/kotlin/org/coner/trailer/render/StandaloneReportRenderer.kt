package org.coner.trailer.render

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.coner.trailer.Event
import org.coner.trailer.eventresults.ResultsReport

class StandaloneReportRenderer : Renderer {

    fun renderEventResults(event: Event, resultsReport: ResultsReport, resultsPartial: HtmlBlockTag.() -> Unit) = createHTML()
        .html {
            head {
                title { + "${resultsReport.type.title} - ${event.name} - ${event.date}" }
            }
            body {
                id = "standalone-event-results"
                h1 { + "${resultsReport.type.title} - ${event.name} - ${event.date}" }
                resultsPartial(this)
            }
        }
}