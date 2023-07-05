package tech.coner.trailer.render.text.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Event
import tech.coner.trailer.Person
import tech.coner.trailer.render.property.EventCrispyFishClassDefinitionFilePropertyRenderer
import tech.coner.trailer.render.property.EventCrispyFishEventControlFilePropertyRenderer
import tech.coner.trailer.render.property.EventDatePropertyRenderer
import tech.coner.trailer.render.property.EventIdPropertyRenderer
import tech.coner.trailer.render.property.EventLifecyclePropertyRenderer
import tech.coner.trailer.render.property.EventMotorsportRegIdPropertyRenderer
import tech.coner.trailer.render.property.EventNamePropertyRenderer
import tech.coner.trailer.render.property.PersonIdPropertyRenderer
import tech.coner.trailer.render.property.PolicyIdPropertyRenderer
import tech.coner.trailer.render.property.PolicyNamePropertyRenderer
import tech.coner.trailer.render.property.SignagePropertyRenderer
import tech.coner.trailer.render.view.EventViewRenderer

class TextEventViewRenderer(
    private val asciiTableFactory: () -> AsciiTable,
    private val eventIdPropertyRenderer: EventIdPropertyRenderer,
    private val eventNamePropertyRenderer: EventNamePropertyRenderer,
    private val eventDatePropertyRenderer: EventDatePropertyRenderer,
    private val eventLifecyclePropertyRenderer: EventLifecyclePropertyRenderer,
    private val eventCrispyFishEventControlFilePropertyRenderer: EventCrispyFishEventControlFilePropertyRenderer,
    private val eventCrispyFishClassDefinitionFilePropertyRenderer : EventCrispyFishClassDefinitionFilePropertyRenderer,
    private val eventMotorsportRegIdPropertyRenderer: EventMotorsportRegIdPropertyRenderer,
    private val policyIdPropertyRenderer: PolicyIdPropertyRenderer,
    private val policyNamePropertyRenderer: PolicyNamePropertyRenderer,
    private val signagePropertyRenderer: SignagePropertyRenderer,
    private val personIdPropertyRenderer: PersonIdPropertyRenderer
) : EventViewRenderer {

    override fun render(model: Event) = """
            ID:         ${eventIdPropertyRenderer(model)}
            Name:       ${eventNamePropertyRenderer(model)}
            Date:       ${eventDatePropertyRenderer(model)}
            Lifecycle:  ${eventLifecyclePropertyRenderer(model)}
            Crispy Fish:
                    Event Control File:     ${eventCrispyFishEventControlFilePropertyRenderer(model)}
                    Class Definition File:  ${eventCrispyFishClassDefinitionFilePropertyRenderer(model)}
                    People Map:             ${renderPeopleMap(model)}
            MotorsportReg:
                    ID:     ${eventMotorsportRegIdPropertyRenderer(model)}
            Policy:
                    ID:     ${policyIdPropertyRenderer(model.policy)}
                    Name:   ${policyNamePropertyRenderer(model.policy)}
    """.trimIndent()

    private fun renderPeopleMap(model: Event): String {
        val content = if (model.crispyFish?.peopleMap?.isNotEmpty() == true) {
            val at = asciiTableFactory()
            at.renderer.cwc = CWC_LongestLine()
            at.addRule()
            at.addRow("Signage", "Person ID")
            at.addRule()
            model.crispyFish?.peopleMap?.forEach { (key: Event.CrispyFishMetadata.PeopleMapKey, person: Person) ->
                at.addRow(
                    signagePropertyRenderer(key.signage, model.policy),
                    personIdPropertyRenderer(person)
                )
            }
            at.addRule()
            at.render()
        } else {
            "Empty"
        }
        return buildString {
            appendLine()
            append(content)
        }.prependIndent(" ".repeat(24))
    }
}