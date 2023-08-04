package tech.coner.trailer.presentation.text.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.presentation.model.ParticipantModel
import tech.coner.trailer.presentation.model.ParticipantCollectionModel

class TextParticipantsView : TextCollectionView<ParticipantModel, ParticipantCollectionModel> {
    override fun invoke(model: ParticipantCollectionModel): String {
        val at = AsciiTable()
            .apply { renderer.cwc = CWC_LongestLine() }
        at.addRule()
        at.addRow("Signage", "First Name", "Last Name", "Member ID", "Car Model", "Car Color")
        at.addRule()
        for (participant in model.items) {
            at.addRow(
                participant.signage,
                participant.firstName,
                participant.lastName,
                participant.clubMemberId,
                participant.carModel,
                participant.carColor,
            )
        }
        at.addRule()
        return at.render()
    }
}