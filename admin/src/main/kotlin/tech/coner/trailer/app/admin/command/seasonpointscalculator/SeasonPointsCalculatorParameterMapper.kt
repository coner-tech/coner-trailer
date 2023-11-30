package tech.coner.trailer.app.admin.command.seasonpointscalculator

import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.EventPointsCalculator

class SeasonPointsCalculatorParameterMapper(
        private val eventPointsCalculatorService: EventPointsCalculatorService
) {

    fun fromParameter(parameter: List<Pair<String, String>>): Map<EventResultsType, EventPointsCalculator> {
        return parameter.associate { (resultsTypeKey, eventPointsCalculatorName) ->
            val resultsType = requireNotNull(StandardEventResultsTypes.fromKey(resultsTypeKey)) {
                "Results type with key not found: $resultsTypeKey"
            }
            val eventPointsCalculator = eventPointsCalculatorService.findByName(eventPointsCalculatorName)
            resultsType to eventPointsCalculator
        }
    }

}