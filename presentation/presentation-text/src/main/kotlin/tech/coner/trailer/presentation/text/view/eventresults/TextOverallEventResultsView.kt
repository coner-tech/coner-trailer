package tech.coner.trailer.presentation.text.view.eventresults

import tech.coner.trailer.presentation.model.eventresults.OverallEventResultsModel

class TextOverallEventResultsView(
) : TextEventResultsView<OverallEventResultsModel>() {

    override fun StringBuilder.appendModel(model: OverallEventResultsModel) {
        val at = createAsciiTable()
        at.appendHeader()
        for (participantResult in model.participantResults.items) {
            at.appendData(participantResult)
        }
        at.addRule()
        appendLine(at.render())
    }
}