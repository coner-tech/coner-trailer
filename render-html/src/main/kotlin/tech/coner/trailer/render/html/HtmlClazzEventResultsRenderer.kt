package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.render.ClazzEventResultsRenderer

class HtmlClazzEventResultsRenderer(
    columns: List<HtmlEventResultsColumn>
) : HtmlEventResultsRenderer<ClazzEventResults>(columns),
    ClazzEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(event: Event, results: ClazzEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-${results.type.key}", "event-${event.id}")
            table {
                classes = setOf("table", "table-striped", "primary")
                thead {
                    tr {
                        columns.forEach { column -> column.header(this, results.type) }
                    }
                }
                tbody {
                    results.groupParticipantResults.forEach { (group, participantResults) ->

                        tr {
                            classes = setOf("table-striped-exempt")
                            td {
                                classes = setOf("display-6", "bg-secondary", "text-white")
                                colSpan = "${columns.size}"
                                text(group.name)
                            }
                        }
                        for (result in participantResults) {
                            tr {
                                columns.forEach { column -> column.data(this, result) }
                            }
                        }
                    }
                }
            }
        }
    }
}