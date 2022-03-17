package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.IndividualEventResultsRenderer

class HtmlIndividualEventResultsRenderer(
    private val staticColumns: List<HtmlEventResultsColumn>,
    private val dynamicTypeColumnFactory: HtmlIndividualEventResultsColumnRendererFactory
) : HtmlEventResultsRenderer<IndividualEventResults>(columns = emptyList()),
    IndividualEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(event: Event, results: IndividualEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-${results.type.key}", "event-${event.id}")
            h3 { text(results.type.title) }
            table {
                val dynamicColumns = dynamicTypeColumnFactory.factory(emptyList())
                classes = setOf("table", "table-striped", "primary")
                thead {
                    tr {
                        staticColumns.forEach { staticColumn ->
                            staticColumn.header(this, results.type)
                        }
                        results.innerEventResultsTypes.forEach { eventResultsType ->
                            dynamicColumns.forEach { dynamicColumn ->
                                dynamicColumn.header(this, eventResultsType)
                            }
                        }
                    }
                }
                tbody {
                    results.allByParticipant.forEach { (participant, individualParticipantResults) ->
                        tr {
                            staticColumns.forEach { staticColumn ->
                                staticColumn.data(this, individualParticipantResults.values.first())
                            }
                            individualParticipantResults.values.forEach { individualParticipantResult ->
                                dynamicColumns.forEach { dynamicColumn ->
                                    dynamicColumn.data(this, individualParticipantResult)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun headerStylesheet(event: Event, results: IndividualEventResults): String {
        return mutableListOf<HtmlEventResultsColumn>()
            .apply {
                addAll(staticColumns)
                addAll(dynamicTypeColumnFactory.factory(emptyList()))
            }
            .flatMap { it.buildStyles(event, results) }
            .distinct()
            .joinToString(separator = "\n")
    }
}