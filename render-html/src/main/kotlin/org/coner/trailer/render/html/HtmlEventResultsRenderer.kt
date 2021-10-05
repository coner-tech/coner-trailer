package org.coner.trailer.render.html

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import org.coner.trailer.Event
import org.coner.trailer.eventresults.EventResults
import org.coner.trailer.render.EventResultsRenderer

abstract class HtmlEventResultsRenderer<ER : EventResults>(
    protected val columns: List<HtmlEventResultsColumn>
) : EventResultsRenderer<ER, String, HtmlBlockTag.() -> Unit>, HtmlRenderer {

    override fun render(event: Event, results: ER): String = createHTMLDocument()
        .html {
            fun titleText() = "${event.name} - ${event.date} - ${results.type.title}"
            head {
                bootstrapMetaViewport()
                bootstrapLinkCss()
                title { + titleText() }
                style { unsafe { raw(headerStylesheet(event, results)) } }
            }
            body {
                id = "event-results"
                classes = setOf("container-xl")
                h2 { + event.name }
                partial(event, results)()
            }
        }.serialize()

    protected open fun headerStylesheet(event: Event, results: ER): String {
        return columns
            .flatMap { it.buildStyles(event, results) }
            .distinct()
            .joinToString(separator = "\n")
    }
}