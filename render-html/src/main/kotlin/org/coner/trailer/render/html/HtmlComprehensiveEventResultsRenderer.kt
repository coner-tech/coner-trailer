package org.coner.trailer.render.html

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.ComprehensiveEventResults
import org.coner.trailer.eventresults.EventResults
import org.coner.trailer.eventresults.GroupEventResults
import org.coner.trailer.eventresults.OverallEventResults
import org.coner.trailer.render.ComprehensiveEventResultsRenderer

class HtmlComprehensiveEventResultsRenderer(
    columns: List<HtmlEventResultsColumn>,
    private val overallRenderer: HtmlOverallEventResultsRenderer,
    private val groupRenderer: HtmlGroupEventResultsRenderer
) : HtmlEventResultsRenderer<ComprehensiveEventResults>(columns),
    ComprehensiveEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(event: Event, results: ComprehensiveEventResults): HtmlBlockTag.() -> Unit = {
        ul {
            classes = setOf("nav", "nav-tabs", "bg-secondary")
            role = "tablist"
            results.all.forEachIndexed { index, eventResults ->
                tab(results = eventResults, selected = index == 0)
            }
        }
        div {
            classes = setOf("tab-content")
            id = "comprehensive-event-results-tab-content"
            results.all.forEachIndexed { index, eventResults ->
                tabPane(event = event, results = eventResults, selected = index == 0)
            }
        }
    }

    private fun UL.tab(results: EventResults, selected: Boolean) {
        li {
            id = "comprehensive-event-results-tabs"
            classes = setOf("nav-item")
            role = "presentation"
            button {
                classes = mutableSetOf("nav-link").apply {
                    if (selected) add("active")
                }
                id = "${results.type.key}-tab"
                attributes["data-bs-toggle"] = "tab"
                attributes["data-bs-target"] = "#${results.type.key}"
                type = ButtonType.button
                role = "tab"
                attributes["aria-selected"] = selected.toString()
                text(results.type.titleShort)
            }
        }
    }

    private fun DIV.tabPane(event: Event, results: EventResults, selected: Boolean) {
        div {
            classes = mutableSetOf("tab-pane").apply {
                if (selected) addAll(arrayOf("show", "active"))
            }
            id = results.type.key
            role = "tabpanel"
            attributes["aria-labelledby"] = "${results.type.key}-tab"
            when (results) {
                is OverallEventResults -> overallRenderer.partial(event, results).invoke(this)
                is GroupEventResults -> groupRenderer.partial(event, results).invoke(this)
                else -> throw IllegalArgumentException("unable to handle results type: ${results.type.key}")
            }
        }
    }
}
