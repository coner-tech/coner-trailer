package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.IndividualEventResultsRenderer

class TextIndividualEventResultsRenderer : TextEventResultsRenderer<IndividualEventResults>(columns = emptyList()),
    IndividualEventResultsRenderer<String, () -> String> {

    override fun partial(event: Event, results: IndividualEventResults): () -> String = {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        val heading = mutableListOf("Name", "Signage", "Car").also {
            results.innerEventResultsTypes.forEach { eventResultsType ->
                it.add(eventResultsType.positionColumnHeading)
                it.add(eventResultsType.scoreColumnHeading)
            }
        }
        at.addRow(heading)
        at.addRule()
        results.allByParticipant.forEach { (participant, individualParticipantResults) ->
            val resultsText = mutableListOf(
                renderName(participant),
                participant.signage?.classingNumber ?: "",
                participant.car?.model ?: ""

            )
                .apply {
                    individualParticipantResults.values.forEach { individualParticipantResult ->
                        add("${individualParticipantResult?.position ?: ""}")
                        add(individualParticipantResult?.let(::renderScoreColumnValue) ?: "")
                    }
                }
            at.addRow(resultsText)
        }
        at.addRule()
        at.render()
    }

}