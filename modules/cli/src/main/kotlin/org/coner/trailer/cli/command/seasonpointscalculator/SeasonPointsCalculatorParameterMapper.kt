package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.output.TermUi.echo
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator

class SeasonPointsCalculatorParameterMapper(
        private val participantEventResultPointsCalculatorService: ParticipantEventResultPointsCalculatorService
) {

    fun fromParameter(parameter: List<Pair<String, String>>): Map<ResultsType, ParticipantEventResultPointsCalculator> {
        return parameter.map { (resultsTypeKey, participantEventResultPointsCalculatorName) ->
            val resultsType = StandardResultsTypes.fromKey(resultsTypeKey)
            if (resultsType == null) {
                echo("Results type with key not found: $resultsTypeKey")
                throw Abort()
            }
            val participantEventResultPointsCalculator = participantEventResultPointsCalculatorService.findByName(participantEventResultPointsCalculatorName)
            if (participantEventResultPointsCalculator == null) {
                echo("Participant event result points calculator with name not found: $participantEventResultPointsCalculatorName")
                throw Abort()
            }
            resultsType to participantEventResultPointsCalculator
        }.toMap()
    }

}