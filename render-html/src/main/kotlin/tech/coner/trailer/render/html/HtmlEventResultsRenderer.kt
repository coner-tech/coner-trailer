package tech.coner.trailer.render.html

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.render.EventResultsRenderer

abstract class HtmlEventResultsRenderer<ER : EventResults>(
    protected val columns: List<HtmlEventResultsColumn>
) : EventResultsRenderer<ER, String, HtmlBlockTag.() -> Unit>, HtmlRenderer {

    override fun render(eventContext: EventContext, results: ER): String = createHTMLDocument()
        .html {
            fun titleText() = "${eventContext.event.name} - ${InputType.date} - ${results.type.title}"
            head {
                installBootstrap()
                title { + titleText() }
                style { unsafe { raw(headerStylesheet(eventContext.event, results)) } }
                installConerTrailerStylesheet()
                commonScripts()
                specificScripts(eventContext, results)
            }
            body {
                id = "event-results"
                classes = setOf("container-xl")
                renderNavbar(eventContext.event, results)
                partial(eventContext, results)()
                renderFooter()
                standardEventResultTableTemplate()
                standardEventResultTableRowTemplate()
            }
        }
        .serialize()

    private fun BODY.renderNavbar(event: Event, results: ER) {
        nav {
            classes = setOf("navbar", "navbar-expand", "navbar-dark", "bg-secondary")
            div {
                classes = setOf("container-fluid")
                renderBrand(event)
                renderText(event, results)
            }
        }
    }

    private fun DIV.renderBrand(event: Event) {
        span {
            classes = setOf("navbar-brand", "mb-0", "h1")
            text(event.policy.club.name)
        }
    }

    private fun BODY.renderFooter() {
        footer {
            classes = setOf("container-fluid", "bg-secondary", "text-white")
            text("Generated by ")
            a(href = "https://github.com/coner-tech/coner-trailer") {
                text("Coner Trailer")
            }
        }
    }

    private fun HEAD.commonScripts() {
        script { unsafe { raw("""
            class StandardEventResultTableBinding {
                constructor() {
                    const template = document.querySelector('#standard-event-result-table-template');
                    this.root = template.content.cloneNode(true);
                    this.score = this.root.querySelector("span.score");
                }
            }
            
            class StandardEventResultTableRowBinding {
                constructor() {
                    const template = document.querySelector('#standard-event-result-table-row-template');
                    this.root = template.content.cloneNode(true);
                    this.position = this.root.querySelector("span.position");
                    this.signage = this.root.querySelector("span.signage");
                    this.name = this.root.querySelector("span.name");
                    this.car = this.root.querySelector("span.car");
                    this.score = this.root.querySelector("span.score");
                }
            }
            
            var canUseTemplate = null;
            function useTemplate(fn) {
                if (canUseTemplate != false || 'content' in document.createElement('template')) {
                    if (canUseTemplate == null) {
                        canUseTemplate = true;
                    }
                    fn()
                } else {
                    if (canUseTemplate == null) {
                        canUseTemplate = false;
                        alert("This browser is not supported.");
                    }
                }
            }
        """.trimIndent()) } }
    }

    abstract fun HEAD.specificScripts(eventContext: EventContext, results: ER)

    protected fun SECTION.setupResultsContent(results: ER, rendered: String) {
        script {
            unsafe { raw ("""
                document.addEventListener("DOMContentLoaded", function() {
                    if (typeof results == 'undefined') results = {};
                    results.${results.type.key} = $rendered;
                    
                    if ('content' in document.createElement('template')) {
                        const tbody = document.querySelector("section.event-results-${results.type.key} tbody");
                        const template = document.querySelector('#table-data-row-template');
                        
                        const participantResults = results.${results.type.key}.results.participantResults
                        let clone = null;
                        let participantResult = null;
                        let td = null;
                        for (i in participantResults) {
                            clone = template.content.cloneNode(true);
                            participantResult = participantResults[i];
                            
                            td = clone.querySelectorAll("td");
                            clone.querySelector("span.position").textContent = participantResult.position;
                            clone.querySelector("span.signage").textContent = participantResult.participant.signage;
                            clone.querySelector("span.name").textContent = participantResult.participant.firstName + " " + participantResult.participant.lastName;
                            clone.querySelector("span.car").textContent = participantResult.participant.car?.model;
                            clone.querySelector("span.score").textContent = participantResult.score.time;
                            
                            tbody.appendChild(clone);
                        }
                    } else {
                        alert("This browser is not supported.");
                    }
                });
        """.trimIndent()) } }
    }

    private fun BODY.standardEventResultTableTemplate() {
        unsafe { raw("""
            <template id="standard-event-result-table-template">
                <table class="table table-striped primary">
                    <thead>
                        <tr>
                            <th class="position-signage" scope="col">
                                Position / Signage
                            </th>
                            <th class="name-car" scope="col">
                                Name / Car
                            </th>
                            <th class="score-more" scope="col">
                                <span class="score"/>
                                <span class="more" />
                            </th>
                        </tr>
                    </thead>
                    <tbody />
                </table>
            </template>
        """.trimIndent()) }
    }

    private fun BODY.standardEventResultTableRowTemplate() {
        unsafe { raw("""
            <template id="standard-event-result-table-row-template">
               <tr>
                   <td class="position-signage-cell">
                       <span class="position" />
                       <span class="signage" />
                   </td>
                   <td class="name-car-cell">
                       <span class="name" />
                       <span class="car" />
                   </td>
                   <td class="score-more-cell">
                       <span class="score" />
                       <span class="more" />
                   </td>
               </tr>
            </template>
        """.trimIndent()) }
    }

    protected open fun DIV.renderText(event: Event, results: ER) {
        span {
            classes = setOf("navbar-text", "text-white")
            text(event.name)
        }
    }

    open fun headerStylesheet(event: Event, results: ER): String {
        return columns
            .flatMap { it.buildStyles(event, results) }
            .distinct()
            .joinToString(separator = "\n")
    }
}