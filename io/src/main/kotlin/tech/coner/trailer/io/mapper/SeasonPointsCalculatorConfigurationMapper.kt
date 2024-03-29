package tech.coner.trailer.io.mapper

import tech.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration

class SeasonPointsCalculatorConfigurationMapper(
        private val eventPointsCalculatorService: EventPointsCalculatorService,
        private val rankingSortService: RankingSortService
) {

    fun fromSnoozle(snoozle: SeasonPointsCalculatorConfigurationEntity): SeasonPointsCalculatorConfiguration {
        return SeasonPointsCalculatorConfiguration(
                id = snoozle.id,
                name = snoozle.name,
                eventResultsTypeToEventPointsCalculator = snoozle.resultsTypeKeyToEventPointsCalculatorId.map { (key, value) ->
                    val resultType = checkNotNull(StandardEventResultsTypes.fromKey(key)) {
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
                resultsTypeKeyToEventPointsCalculatorId = core.eventResultsTypeToEventPointsCalculator.map { it.key.key to it.value.id }.toMap(),
                rankingSortId = core.rankingSort.id
        )
    }

}
