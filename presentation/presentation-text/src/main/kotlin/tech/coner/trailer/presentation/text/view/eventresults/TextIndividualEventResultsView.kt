package tech.coner.trailer.presentation.text.view.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.presentation.model.eventresults.IndividualEventResultsModel

class TextIndividualEventResultsView : TextEventResultsView<IndividualEventResultsModel>() {

    override fun StringBuilder.appendModel(model: IndividualEventResultsModel) {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        val heading = mutableListOf("Name", "Signage", "Car").also {
            model.innerEventResultsTypes.items.forEach { eventResultsType ->
                it.add(eventResultsType.positionColumnHeading)
                it.add(eventResultsType.scoreColumnHeading)
            }
        }
        at.addRow(heading)
        at.addRule()
        model.resultsByIndividual.items.forEach { resultsByIndividual ->
            val resultsText = mutableListOf(
                resultsByIndividual.participant.fullName,
                resultsByIndividual.participant.signage,
                resultsByIndividual.participant.carModel

            )
                .apply {
                    resultsByIndividual.typedParticipantResults.forEach { typedParticipantResult ->
                        add(typedParticipantResult.position)
                        add(typedParticipantResult.score)
                    }
                }
            at.addRow(resultsText)
        }
        at.addRule()
        appendLine(at.render())
    }

}