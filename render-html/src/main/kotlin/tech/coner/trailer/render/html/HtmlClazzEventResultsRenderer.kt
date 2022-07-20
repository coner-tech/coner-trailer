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
            h3 { text("Top Times") }
            table {
                classes = setOf("table", "table-striped", "secondary")
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
//                tbody {
//                    results.parentClassTopTimes.forEach { (group, topParticipantResult) ->
//                        tr {
//                            th {
//                                classes = setOf("category")
//                                scope = ThScope.row
//                                text(group.name)
//                            }
//                            td {
//                                classes = setOf("signage")
//                                text(topParticipantResult.participant.signage?.classingNumber ?: "")
//                            }
//                            td {
//                                classes = setOf("name")
//                                text(renderName(topParticipantResult.participant))
//                            }
//                            td {
//                                classes = setOf("time")
//                                text(topParticipantResult.personalBestRun?.run?.let { render(it) } ?: "")
//                            }
//                        }
//                    }
//                }
            }
        }
    }
}