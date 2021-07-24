package org.coner.trailer.render.html

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupEventResults

class HtmlGroupedEventResultsRenderer(
    columns: List<HtmlEventResultsColumn>
) : HtmlEventResultsRenderer<GroupEventResults>(columns) {

    override fun partial(event: Event, results: GroupEventResults): HtmlBlockTag.() -> Unit = {
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
                    results.groupParticipantResults.forEach { (group, participantResults) ->

                        tr {
                            td {
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
            h2 { text("Top Times") }
            table {
                classes = setOf("table", "table-striped")
                thead {
                    tr {
                        th {
                            scope = ThScope.col
                            text("Category")
                        }
                        th {
                            scope = ThScope.col
                            text("Signage")
                        }
                        th {
                            scope = ThScope.col
                            text("Name")
                        }
                        th {
                            scope = ThScope.col
                            text("Time")
                        }
                    }
                }
                tbody {
                    results.parentClassTopTimes.forEach { (group, topParticipantResult) ->
                        tr {
                            th {
                                classes = setOf("category")
                                scope = ThScope.row
                                text(group.name)
                            }
                            td {
                                classes = setOf("signage")
                                text(topParticipantResult.participant.signageClassingNumber ?: "")
                            }
                            td {
                                classes = setOf("name")
                                text(renderName(topParticipantResult.participant))
                            }
                            td {
                                classes = setOf("time")
                                text(topParticipantResult.personalBestRun?.run?.let { render(it) } ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}