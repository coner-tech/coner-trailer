package tech.coner.trailer.render.text.view.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer

class TextIndividualEventResultsViewRenderer(
    private val signagePropertyRenderer: SignagePropertyRenderer,
    private val participantNamePropertyRenderer: ParticipantNamePropertyRenderer,
    private val carModelPropertyRenderer: CarModelPropertyRenderer,
    participantResultDiffPropertyRenderer: ParticipantResultDiffPropertyRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer
) : TextEventResultsViewRenderer<IndividualEventResults>(
    signagePropertyRenderer = signagePropertyRenderer,
    participantNamePropertyRenderer = participantNamePropertyRenderer,
    carModelPropertyRenderer = carModelPropertyRenderer,
    participantResultDiffPropertyRenderer = participantResultDiffPropertyRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer
), PartialRenderer<IndividualEventResults, StringBuilder> {

    override fun StringBuilder.renderPartial(model: IndividualEventResults) {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        val heading = mutableListOf("Name", "Signage", "Car").also {
            model.innerEventResultsTypes.forEach { eventResultsType ->
                it.add(eventResultsType.positionColumnHeading)
                it.add(eventResultsType.scoreColumnHeading)
            }
        }
        at.addRow(heading)
        at.addRule()
        model.resultsByIndividual.forEach { (participant, individualParticipantResults) ->
            val resultsText = mutableListOf(
                participantNamePropertyRenderer(participant),
                signagePropertyRenderer(participant.signage, model.eventContext.event.policy),
                carModelPropertyRenderer(participant.car)

            )
                .apply {
                    individualParticipantResults.values.forEach { individualParticipantResult ->
                        add("${individualParticipantResult?.position ?: ""}")
                        add(individualParticipantResult?.let { participantResultScoreRenderer(it) } ?: "" )
                    }
                }
            at.addRow(resultsText)
        }
        at.addRule()
        appendLine(at.render())
    }

}