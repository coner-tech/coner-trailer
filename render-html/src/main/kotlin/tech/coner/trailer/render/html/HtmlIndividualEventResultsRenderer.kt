package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.IndividualEventResultsRenderer

class HtmlIndividualEventResultsRenderer(
    private val staticColumns: List<HtmlParticipantColumn> = listOf(
        HtmlParticipantColumn.Name(),
        HtmlParticipantColumn.Signage(),
        HtmlParticipantColumn.CarModel(),
    ),
    private val dynamicColumns: List<HtmlEventResultsColumn> = listOf(
        HtmlIndividualEventResultsColumn.Position(),
        HtmlIndividualEventResultsColumn.Score(),
        HtmlIndividualEventResultsColumn.MobilePositionScore()
    )
) : HtmlEventResultsRenderer<IndividualEventResults>(columns = emptyList()),
    IndividualEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(eventContext: EventContext, results: IndividualEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-${results.type.key}", "event-${eventContext.event.id}")
            h3 { text(results.type.title) }
            table {
                classes = setOf("table", "table-striped", "primary")
                thead {
                    tr {
                        staticColumns.forEach { staticColumn ->
                            staticColumn.header(this)
                        }
                        results.innerEventResultsTypes.forEach { eventResultsType ->
                            dynamicColumns.forEach { dynamicColumn ->
                                dynamicColumn.header(this, eventResultsType)
                            }
                        }
                    }
                }
                tbody {
                    results.resultsByIndividual.forEach { (participant, resultsByType) ->
                        tr {
                            staticColumns.forEach { staticColumn ->
                                staticColumn.data(this, participant)
                            }
                            resultsByType.values.forEach { individualParticipantResult ->
                                dynamicColumns.forEach { dynamicColumn ->
                                    if (individualParticipantResult != null) {
                                        dynamicColumn.data(this, individualParticipantResult)
                                    } else {
                                        td { /* empty */ }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun headerStylesheet(event: Event, results: IndividualEventResults): String {
        return dynamicColumns
            .flatMap { it.buildStyles(event, results) }
            .distinct()
            .joinToString(separator = "\n")
    }

    override fun HEAD.specificScripts(eventContext: EventContext, results: IndividualEventResults) {
        // no-op TODO("Not yet implemented")
    }
}