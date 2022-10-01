package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.OverallEventResultsRenderer
import tech.coner.trailer.render.json.JsonOverallEventResultsRenderer

class HtmlOverallEventResultsRenderer(
    private val jsonRenderer: JsonOverallEventResultsRenderer
) : HtmlEventResultsRenderer<OverallEventResults>(emptyList()),
    OverallEventResultsRenderer<String, HtmlBlockTag.() -> Unit> {

    override fun partial(eventContext: EventContext, results: OverallEventResults): HtmlBlockTag.() -> Unit = {
        section {
            classes = setOf("event-results", "event-results-${results.type.key}", "event-${eventContext.event.id}")
        }
    }

    override fun HEAD.specificScripts(eventContext: EventContext, results: OverallEventResults) {
        script { unsafe { raw("""
            if (typeof allResults == 'undefined') allResults = {};
            allResults.${results.type.key} = ${jsonRenderer.render(eventContext, results)};
            
            document.addEventListener("DOMContentLoaded", function() {
                // setup ${results.type.key} content
                useTemplate(function() {
                    // setup table
                    const section = document.querySelector("section.event-results-${results.type.key}.event-${eventContext.event.id}");
                    const binding = new StandardEventResultTableBinding();
                    binding.score.textContent = "${results.type.scoreColumnHeading}";
                    section.appendChild(binding.root);
                });
                useTemplate(function() {
                    // setup tbody
                    const tbody = document.querySelector("section.event-results-${results.type.key} tbody");
                    const participantResults = allResults.${results.type.key}.results.participantResults;
                    let binding = null;
                    let participantResult = null;
                    for (i in participantResults) {                    
                        binding = new StandardEventResultTableRowBinding();
                        participantResult = participantResults[i];
                        binding.position.textContent = participantResult.position;
                        binding.signage.textContent = participantResult.participant.signage;
                        binding.name.textContent = participantResult.participant.firstName + " " + participantResult.participant.lastName;
                        binding.car.textContent = participantResult.participant.car?.model;
                        binding.score.textContent = participantResult.score.time;
                        tbody.appendChild(binding.root);
                    }
                });
            });
        """.trimIndent()) } }
    }
}