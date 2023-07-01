package tech.coner.trailer.render.text.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.eventresults.IndividualEventResultsRenderer
import tech.coner.trailer.render.internal.*

class TextIndividualEventResultsRenderer(
    private val signageRenderer: SignageRenderer,
    private val participantNameRenderer: ParticipantNameRenderer,
    private val carModelRenderer: CarModelRenderer,
    participantResultDiffRenderer: ParticipantResultDiffRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer
) : TextEventResultsRenderer<IndividualEventResults>(
    signageRenderer = signageRenderer,
    participantNameRenderer = participantNameRenderer,
    carModelRenderer = carModelRenderer,
    participantResultDiffRenderer = participantResultDiffRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer
), IndividualEventResultsRenderer, PartialRenderer<IndividualEventResults, StringBuilder> {

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
                participantNameRenderer(participant),
                signageRenderer(participant.signage),
                carModelRenderer(participant.car)

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