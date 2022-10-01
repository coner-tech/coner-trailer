package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.render.ClazzEventResultsRenderer
import tech.coner.trailer.render.html.util.template
import tech.coner.trailer.render.json.JsonClazzEventResultsRenderer

class HtmlClazzEventResultsRenderer(
    private val jsonRenderer: JsonClazzEventResultsRenderer
) : HtmlEventResultsRenderer<ClazzEventResults>(emptyList()),
    ClazzEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(eventContext: EventContext, results: ClazzEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-${results.type.key}", "event-${eventContext.event.id}")
            template {
                id = "clazz-name-event-result-table-row-binding"
                unsafe { raw("""
                    <tr class="table-striped-exempt clazz-name-row">
                        <td class="display-6 bg-secondary text-white clazz-name" colspan="3" />
                    </tr>
                """.trimIndent()) }
            }
        }
    }

    override fun HEAD.specificScripts(eventContext: EventContext, results: ClazzEventResults) {
        script { unsafe { raw("""
            if (typeof allResults == 'undefined') allResults = {};
            allResults.${results.type.key} = ${jsonRenderer.render(eventContext, results)};
            
            class ClazzNameEventResultTableRowBinding {
                constructor() {
                    const template = document.querySelector('#clazz-name-event-result-table-row-binding');
                    this.root = template.content.cloneNode(true);
                    this.clazzName = this.root.querySelector("td.clazz-name");
                }
            }
            
            document.addEventListener("DOMContentLoaded", function() {
                // setup ${results.type.key} content
                useTemplate(function() {
                    useTemplate(function() {
                    // setup table
                    const section = document.querySelector("section.event-results-${results.type.key}.event-${eventContext.event.id}");
                    
                    const binding = new StandardEventResultTableBinding();
                    binding.score.textContent = "${results.type.scoreColumnHeading}";
                    
                    section.appendChild(binding.root);
                });
                });
                useTemplate(function() {
                    // setup tbody
                    const tbody = document.querySelector("section.event-results-${results.type.key} tbody");
                    const classes = allResults.${results.type.key}.classes;
                    const clazzParticipantResults = allResults.${results.type.key}.results.clazzParticipantResults;
                    let clazzNameBinding = null;
                    for (clazzAbbreviation in clazzParticipantResults) {
                        clazzNameBinding = new ClazzNameEventResultTableRowBinding();
                        clazzNameBinding.clazzName.textContent = classes[clazzAbbreviation].name;
                        tbody.appendChild(clazzNameBinding.root);
                        
                        let participantResultsForClazz = clazzParticipantResults[clazzAbbreviation];
                        let participantResult = null;
                        for (i in participantResultsForClazz) {
                            participantResult = participantResultsForClazz[i];
                            
                            binding = new StandardEventResultTableRowBinding();
                            binding.position.textContent = participantResult.position;
                            binding.signage.textContent = participantResult.participant.signage;
                            binding.name.textContent = participantResult.participant.firstName + " " + participantResult.participant.lastName;
                            binding.car.textContent = participantResult.participant.car?.model;
                            binding.score.textContent = participantResult.score.time;
                            
                            tbody.appendChild(binding.root);
                        }
                    }
                });
            });
        """.trimIndent()) } }
    }
}