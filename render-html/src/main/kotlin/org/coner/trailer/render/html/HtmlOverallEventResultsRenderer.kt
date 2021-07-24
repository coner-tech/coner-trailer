package org.coner.trailer.render.html

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallEventResults

class HtmlOverallEventResultsRenderer(
    columns: List<HtmlEventResultsColumn>
) : HtmlEventResultsRenderer<OverallEventResults>(columns) {

    override fun partial(event: Event, results: OverallEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", results.type.key, "event-${event.id}")
            h2 { text(results.type.title) }
            table {
                classes = setOf("table", "table-striped", "caption-top", "event-results-table-primary")
                caption { text(results.type.title) }
                thead {
                    tr {
                        columns.forEach { column -> column.header(this, results.type) }
                    }
                }
                tbody {
                    for (result in results.participantResults) {
                        tr {
                            columns.forEach { column -> column.data(this, result) }
                        }
                    }
                }
            }
        }
    }
}