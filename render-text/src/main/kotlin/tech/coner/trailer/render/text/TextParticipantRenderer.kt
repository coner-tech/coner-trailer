package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Participant
import tech.coner.trailer.render.ParticipantRenderer

class TextParticipantRenderer : ParticipantRenderer {
    override fun render(participants: List<Participant>): String {
        val at = AsciiTable()
            .apply { renderer.cwc = CWC_LongestLine() }
        at.addRule()
        at.addRow("Signage", "First Name", "Last Name", "Member ID", "Car Model", "Car Color")
        at.addRule()
        for (participant in participants) {
            at.addRow(
                participant.signage?.classingNumber ?: "",
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