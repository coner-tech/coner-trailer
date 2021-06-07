package org.coner.trailer.render

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.Event
import org.coner.trailer.eventresults.ResultsReport

class StandaloneReportHtmlRenderer : KotlinxHtmlRenderer {

    fun renderEventResults(event: Event, resultsReport: ResultsReport, resultsPartial: HtmlBlockTag.() -> Unit): String = createHTMLDocument()
        .html {
            fun titleText() = "${event.name} - ${event.date} - ${resultsReport.type.title}"
            head {
                bootstrapMetaViewport()
                bootstrapLinkCss()
                title { + titleText() }
                style { unsafe { raw("""
                    .time {
                        font-family: monospace;
                    }
                    ol.runs {
                        padding: 0;
                        display: grid;
                        grid-template-columns: repeat(${resultsReport.runCount}, 1fr);
                        list-style-type: none;
                        list-style-position: inside;
                    }
                    ol.runs li {
                        min-width: 110px;
                    }
                """.trimIndent()) } }
            }
            body {
                id = "standalone-event-results"
                classes = setOf("container-xxl")
                h2 { + event.name }
                resultsPartial(this)
            }
        }.serialize()
}