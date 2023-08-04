package tech.coner.trailer.presentation.text.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel

class TextRunsView : TextCollectionView<RunModel, RunCollectionModel> {

    override fun invoke(model: RunCollectionModel): String {
        val at = AsciiTable().apply {
            renderer.cwc = CWC_LongestLine()
        }
        at.addRule()
        at.addRow("Sequence", "Signage", "Name", "Car Model", "Car Color", "Time", "Penalties", "Rerun")
        at.addRule()
        model.items.forEach { run ->
            at.addRow(
                run.sequence,
                run.signage,
                run.participantName,
                run.participantCarModel,
                run.participantCarColor,
                run.time,
                run.penalties,
                run.rerun
            )
        }
        at.addRule()
        return at.render()
    }
}