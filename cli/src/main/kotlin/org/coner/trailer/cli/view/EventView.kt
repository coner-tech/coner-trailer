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
                    Force Signage To Person: 
                        ${renderForceSignageToPerson(model)}
    """.trimIndent()

    private fun renderForceSignageToPerson(model: Event): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRow("Signage", "Person ID")
        model.crispyFish?.forceParticipantSignageToPerson?.forEach { (signage: Participant.Signage, person: Person) ->
            val grouping = when (val grouping = signage.grouping) {
                is Grouping.Singular -> grouping.abbreviation
                is Grouping.Paired -> "${grouping.pair.first.abbreviation} ${grouping.pair.second.abbreviation}"
            }
            at.addRow("$grouping ${signage.number}", "${person.id}")
        }
        return buildString {
            appendLine()
            append(at.render())
        }.prependIndent(" ".repeat(24))
    }

}