package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.Person

class EventView : View<Event> {

    override fun render(model: Event) = """
        ${model.name}
            ID:     ${model.id}
            Date:   ${model.date}
            Crispy Fish:
                    Event Control File:     ${model.crispyFish?.eventControlFile}
                    Class Definition File:  ${model.crispyFish?.classDefinitionFile}
                    Force People:           ${renderForcePeople(model)}
    """.trimIndent()

    private fun renderForcePeople(model: Event): String {
        val content = if (model.crispyFish?.forcePeople?.isNotEmpty() == true) {
            val at = AsciiTable()
            at.renderer.cwc = CWC_LongestLine()
            at.addRow("Signage", "Person ID")
            model.crispyFish?.forcePeople?.forEach { (signage: Participant.Signage, person: Person) ->
                val grouping = when (val grouping = signage.grouping) {
                    is Grouping.Singular -> grouping.abbreviation
                    is Grouping.Paired -> "${grouping.pair.first.abbreviation} ${grouping.pair.second.abbreviation}"
                }
                at.addRow("$grouping ${signage.number}", "${person.id}")
            }
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