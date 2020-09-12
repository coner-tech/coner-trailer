package org.coner.trailer.io.mapper

import org.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration

class SeasonPointsCalculatorConfigurationMapper(
        private val eventPointsCalculatorService: EventPointsCalculatorService,
        private val rankingSortService: RankingSortService
) {

    fun fromSnoozle(snoozle: SeasonPointsCalculatorConfigurationEntity): SeasonPointsCalculatorConfiguration {
        return SeasonPointsCalculatorConfiguration(
                id = snoozle.id,
                name = snoozle.name,
                resultsTypeToEventPointsCalculator = snoozle.resultsTypeKeyToEventPointsCalculatorId.map { (key, value) ->
                    val resultType = checkNotNull(StandardResultsTypes.fromKey(key)) {
                        "Results type with key not found: $key"
                    }
                    val eventPointsCalculator = eventPointsCalculatorService.findById(value)
                    resultType to eventPointsCalculator
                }.toMap(),
                rankingSort = rankingSortService.findById(snoozle.rankingSortId)
        )
    }

    fun toSnoozle(core: SeasonPointsCalculatorConfiguration): SeasonPointsCalculatorConfigurationEntity {
        return SeasonPointsCalculatorConfigurationEntity(
                id = core.id,
                name = core.name,
                resultsTypeKeyToEventPointsCalculatorId = core.resultsTypeToEventPointsCalculator.map { it.key.key to it.value.id }.toMap(),
                rankingSortId = core.rankingSort.id
        )
    }

}
