package tech.coner.trailer.presentation.text.view.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.presentation.model.eventresults.TopTimesEventResultsModel

class TextTopTimesEventResultsView : TextEventResultsView<TopTimesEventResultsModel>() {

    override fun StringBuilder.appendModel(model: TopTimesEventResultsModel) {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Category", "Name", model.eventResultsTypeScoreColumnHeading)
        at.addRule()
        model.topTimes.items.forEach { topTime ->
            at.addRow(
                topTime.classParentName,
                topTime.participantName,
                topTime.score
            )
        }
        at.addRule()
        appendLine(at.render())
    }
}