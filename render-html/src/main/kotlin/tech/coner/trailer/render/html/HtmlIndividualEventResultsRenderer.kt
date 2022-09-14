package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.IndividualEventResultsRenderer
import tech.coner.trailer.render.html.util.template
import tech.coner.trailer.render.json.JsonIndividualEventResultsRenderer

class HtmlIndividualEventResultsRenderer(
    private val jsonRenderer: JsonIndividualEventResultsRenderer
) : HtmlEventResultsRenderer<IndividualEventResults>(columns = emptyList()),
    IndividualEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(eventContext: EventContext, results: IndividualEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-individual", "event-${eventContext.event.id}")
            h3 { text(results.type.title) }
            table {
                classes = setOf("table", "table-striped", "primary")
                thead {
                    tr {
                        th {
                            classes = setOf("name")
                            scope = ThScope.col
                            text("Name")
                        }
                        th {
                            classes = setOf("signage")
                            scope = ThScope.col
                            text("Signage")
                        }
                        th {
                            classes = setOf("car")
                            scope = ThScope.col
                            text("Car")
                        }
                        th {
                            // intentionally left blank
                        }
                    }
                }
                tbody {

                }
            }
            template {
                id = "individual-event-result-table-row-template"
                unsafe { raw("""
                    <tr>
                        <td class="name" />
                        <td class="signage "/>
                        <td class="car" />
                        <td>
                            <button
                                class="btn btn-secondary detail"
                                type="button" 
                                data-bs-toggle="modal" 
                                data-bs-target="#individual-event-results-detail-modal"
                                data-results-index="">
                                +
                            </button>
                        </td>
                    </tr>
                """.trimIndent()) }
            }
            div {
                id = "individual-event-results-detail-modal"
                classes = setOf("modal")
                div {
                    classes = setOf("modal-dialog")
                    div {
                        classes = setOf("modal-content")
                        div {
                            classes = setOf("modal-header")
                            h5 {
                                classes = setOf("modal-title", "name")
                            }
                            button {
                                type = ButtonType.button
                                classes = setOf("btn-close")
                                attributes["data-bs-dismiss"] = "modal"
                                attributes["aria-label"] = "Close"
                            }
                        }
                        div {
                            classes = setOf("modal-body")
                            h4 {
                                classes = setOf("name")
                            }
                        }
                        div {
                            classes = setOf("modal-footer")
                            button {
                                type = ButtonType.button
                                classes = setOf("btn", "btn-primary")
                                attributes["data-bs-dismiss"] = "modal"
                                text("Close")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun HEAD.specificScripts(eventContext: EventContext, results: IndividualEventResults) {
        script { unsafe { raw("""
            if (typeof allResults == 'undefined') allResults = {};
            allResults.individual = ${jsonRenderer.render(eventContext, results)};
            
            class IndividualEventResultTableRowBinding {
                constructor() {
                    const template = document.querySelector('#individual-event-result-table-row-template');
                    this.root = template.content.cloneNode(true);
                    this.name = this.root.querySelector("td.name");
                    this.signage = this.root.querySelector("td.signage");
                    this.car = this.root.querySelector("td.car");
                    this.detail = this.root.querySelector("button.detail");
                }
            }
            
            class IndividualEventResultsDetailModalBinding {
                constructor() {
                    this.root = document.getElementById("individual-event-results-detail-modal");
                    this.name = this.root.querySelector(".name");
                }
            }
            
            document.addEventListener("DOMContentLoaded", function() {
                // setup individual content
                useTemplate(function() {
                    // setup tbody
                    const tbody = document.querySelector("section.event-results-individual tbody");
                    const allByParticipant = allResults.individual.results.allByParticipant;
                    let binding = null;
                    let participant = null;
                    for (i in allByParticipant) {
                        binding = new IndividualEventResultTableRowBinding();
                        participant = allByParticipant[i].participant;
                        binding.name.textContent = participant.firstName + " " + participant.lastName;
                        binding.signage.textContent = participant.signage;
                        binding.car.textContent = participant.car?.model;
                        binding.detail.dataset.resultsIndex = i;
                        tbody.appendChild(binding.root);
                    }
                });
            });
            
            document.addEventListener("DOMContentLoaded", function() {
                // setup modal
                const individualEventResultsDetailModalBinding = new IndividualEventResultsDetailModalBinding();
                individualEventResultsDetailModalBinding.root.addEventListener("show.bs.modal", function (event) {
                    const button = event.relatedTarget;
                    const resultsIndex = button.dataset.resultsIndex;
                    const individualParticipantResult = allResults.individual.results.allByParticipant[resultsIndex];
                    const participant = individualParticipantResult.participant;
                    const binding = individualEventResultsDetailModalBinding;
                    binding.name.textContent = participant.firstName + " " + participant.lastName;
                });
            });
        """.trimIndent()) } }
    }
}