package org.coner.trailer.render.html

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedEventResults

class HtmlGroupedEventResultsRenderer(
    columns: List<HtmlEventResultsColumn>
) : HtmlEventResultsRenderer<GroupedEventResults>(columns) {

    override fun partial(event: Event, results: GroupedEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-${results.type.key}", "event-${event.id}")
            h2 { text(results.type.title) }
            table {
                classes = setOf("table", "table-striped", "caption-top")
                thead {
                    tr {
                        columns.forEach { column -> column.header(this, results.type) }
                    }
                }
                tbody {
                    results.groupingsToResultsMap.forEach { (grouping, participantResults) ->

                        tr {
                            td {
                                colSpan = "${columns.size}"
                                text(grouping.name)
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