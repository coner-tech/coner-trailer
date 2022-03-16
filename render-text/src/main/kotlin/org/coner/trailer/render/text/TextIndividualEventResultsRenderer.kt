package org.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event
import org.coner.trailer.eventresults.IndividualEventResults
import org.coner.trailer.render.IndividualEventResultsRenderer

class TextIndividualEventResultsRenderer : TextEventResultsRenderer<IndividualEventResults>(columns = emptyList()),
    IndividualEventResultsRenderer<String, () -> String> {

    override fun partial(event: Event, results: IndividualEventResults): () -> String = {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        val heading = mutableListOf("Name", "Signage", "Car").also {
            results.allByParticipant.values.first().keys.forEach { eventResultsType ->
                it.add(eventResultsType.positionColumnHeading)
                it.add(eventResultsType.scoreColumnHeading)
            }
        }
        at.addRow(heading)
        at.addRule()
        results.allByParticipant.forEach { (participant, individualParticipantResults) ->
            val resultsText = mutableListOf(renderName(participant), participant.signageClassingNumber ?: "", participant.car.model ?: "").also {
                individualParticipantResults.values.forEach { individualParticipantResult ->
                    it.add("${individualParticipantResult.position}")
                    it.add(renderScoreColumnValue(individualParticipantResult))
                }
            }
            at.addRow(resultsText)
        }
        at.addRule()
        at.render()
    }

}