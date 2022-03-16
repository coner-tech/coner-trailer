package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event
import org.coner.trailer.Person

class EventView : View<Event> {

    override fun render(model: Event) = """
        ${model.name}
            ID:     ${model.id}
            Date:   ${model.date}
            Crispy Fish:
                    Event Control File:     ${model.crispyFish?.eventControlFile}
                    Class Definition File:  ${model.crispyFish?.classDefinitionFile}
                    People Map:             ${renderPeopleMap(model)}
            MotorsportReg:
                    ID:     ${model.motorsportReg?.id}
            Policy:
                    ID:     ${model.policy.id}
                    Name:   ${model.policy.name}
    """.trimIndent()

    private fun renderPeopleMap(model: Event): String {
        val content = if (model.crispyFish?.peopleMap?.isNotEmpty() == true) {
            val at = AsciiTable()
            at.renderer.cwc = CWC_LongestLine()
            at.addRule()
            at.addRow("Signage", "Person ID")
            at.addRule()
            model.crispyFish?.peopleMap?.forEach { (key: Event.CrispyFishMetadata.PeopleMapKey, person: Person) ->
                at.addRow("${key.classing.abbreviation} ${key.number}", "${person.id}")
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