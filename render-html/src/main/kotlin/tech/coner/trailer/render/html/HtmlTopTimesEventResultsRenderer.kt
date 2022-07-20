package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.TopTimesEventResultsRenderer

class HtmlTopTimesEventResultsRenderer : HtmlEventResultsRenderer<TopTimesEventResults>(emptyList()),
    TopTimesEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(event: Event, results: TopTimesEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-${results.type.key}", "event-${event.id}")
            table {
                classes = setOf("table", "table-striped", "primary")
                thead {
                    tr {
                        th {
                            classes = setOf("category")
                            scope = ThScope.col
                            text("Category")
                        }
                        th {
                            classes = setOf("name")
                            scope = ThScope.col
                            text("Name")
                        }
                        th {
                            classes = setOf("score")
                            scope = ThScope.col
                            text(event.policy.topTimesEventResultsMethod.scoreColumnHeading)
                        }
                    }
                }
                tbody {
                    results.topTimes.forEach { (category, participantResult) ->
                        tr {
                            td {
                                classes = setOf("category")
                                text(category.name)
                            }
                            td {
                                classes = setOf("name")
                                text(renderName(participantResult.participant))
                            }
                            td {
                                classes = setOf("score")
                                text(renderScoreColumnValue(participantResult))
                            }
                        }
                    }
                }
            }
        }

    }
}