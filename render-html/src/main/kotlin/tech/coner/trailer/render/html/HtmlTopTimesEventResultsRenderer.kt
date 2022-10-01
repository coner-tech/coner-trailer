package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.TopTimesEventResultsRenderer
import tech.coner.trailer.render.html.util.template
import tech.coner.trailer.render.json.JsonTopTimesEventResultsRenderer

class HtmlTopTimesEventResultsRenderer(
    private val jsonRenderer: JsonTopTimesEventResultsRenderer
) : HtmlEventResultsRenderer<TopTimesEventResults>(emptyList()),
    TopTimesEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(eventContext: EventContext, results: TopTimesEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-toptimes", "event-${eventContext.event.id}")
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
                            text(eventContext.event.policy.topTimesEventResultsMethod.scoreColumnHeading)
                        }
                    }
                }
                tbody {
                }
            }
            template {
                id = "toptimes-event-result-table-row-template"
                unsafe { raw("""
                    <tr>
                        <td class="category" />
                        <td class="name" />
                        <td class="score" />
                    </tr>
                """.trimIndent()) }
            }
        }

    }

    override fun HEAD.specificScripts(eventContext: EventContext, results: TopTimesEventResults) {
        script { unsafe { raw("""
            if (typeof allResults == 'undefined') allResults = {};
            allResults.toptimes = ${jsonRenderer.render(eventContext, results)};
            
            class TopTimesEventResultTableRowBinding {
                constructor() {
                    const template = document.querySelector('#toptimes-event-result-table-row-template');
                    this.root = template.content.cloneNode(true);
                    this.category = this.root.querySelector("td.category");
                    this.name = this.root.querySelector("td.name");
                    this.score = this.root.querySelector("td.score");
                }
            }
            
            document.addEventListener("DOMContentLoaded", function() {
                // setup ${results.type.key} content
                useTemplate(function() {
                    // setup tbody
                    const tbody = document.querySelector("section.event-results-toptimes tbody");
                    const topTimes = allResults.toptimes.results.topTimes;
                    let binding = null;
                    let topTime = null;
                    for (category in topTimes) {
                        binding = new TopTimesEventResultTableRowBinding();
                        topTime = topTimes[category];
                        binding.category.textContent = category;
                        binding.name.textContent = topTime.participant.firstName + " " + topTime.participant.lastName;
                        binding.score.textContent = topTime.score.time;
                        tbody.appendChild(binding.root);
                    }
                });
            });
        """.trimIndent()) }}
    }
}