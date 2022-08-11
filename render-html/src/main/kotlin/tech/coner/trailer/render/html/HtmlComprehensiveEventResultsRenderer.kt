package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.render.ComprehensiveEventResultsRenderer

class HtmlComprehensiveEventResultsRenderer(
    columns: List<HtmlEventResultsColumn>,
    private val overallRenderer: HtmlOverallEventResultsRenderer,
    private val clazzRenderer: HtmlClazzEventResultsRenderer,
    private val topTimesRenderer: HtmlTopTimesEventResultsRenderer
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
                is ClazzEventResults -> clazzRenderer.partial(event, results).invoke(this)
                is TopTimesEventResults -> topTimesRenderer.partial(event, results).invoke(this)
                else -> throw IllegalArgumentException("unable to handle results type: ${results.type.key}")
            }
        }
    }
}
