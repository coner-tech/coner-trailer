package tech.coner.trailer.render.text.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Participant
import tech.coner.trailer.render.view.ParticipantsViewRenderer
import tech.coner.trailer.render.property.SignagePropertyRenderer

class TextParticipantsViewRenderer(
    private val signagePropertyRenderer: SignagePropertyRenderer
) : ParticipantsViewRenderer {
    override fun render(model: Collection<Participant>): String {
        val at = AsciiTable()
            .apply { renderer.cwc = CWC_LongestLine() }
        at.addRule()
        at.addRow("Signage", "First Name", "Last Name", "Member ID", "Car Model", "Car Color")
        at.addRule()
        for (participant in model) {
            at.addRow(
                signagePropertyRenderer(participant.signage),
                participant.firstName ?: "",
                participant.lastName ?: "",
                participant.person?.clubMemberId ?: "",
                participant.car?.model ?: "",
                participant.car?.color ?: "",
            )
        }
        at.addRule()
        return at.render()
    }
}