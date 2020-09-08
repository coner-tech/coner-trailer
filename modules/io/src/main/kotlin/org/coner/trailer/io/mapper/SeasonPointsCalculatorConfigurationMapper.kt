package org.coner.trailer.io.mapper

import org.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration

class SeasonPointsCalculatorConfigurationMapper(
        private val participantEventResultPointsCalculatorService: ParticipantEventResultPointsCalculatorService,
        private val rankingSortService: RankingSortService
) {

    fun fromSnoozle(snoozle: SeasonPointsCalculatorConfigurationEntity): SeasonPointsCalculatorConfiguration {
        return SeasonPointsCalculatorConfiguration(
                id = snoozle.id,
                name = snoozle.name,
                resultsTypeToCalculatorMap = snoozle.resultsTypeToCalculatorMap.map { (key, value) ->
                    val resultType = checkNotNull(StandardResultsTypes.fromKey(key)) {
                        "Results type with key not found: $key"
                    }
                    val participantEventResultPointsCalculator = participantEventResultPointsCalculatorService.findById(value)
                    resultType to participantEventResultPointsCalculator
                }.toMap(),
                rankingSort = rankingSortService.findById(snoozle.rankingSortId),
                takeTopEventScores = snoozle.takeTopEventScores
        )
    }

    fun toSnoozle(core: SeasonPointsCalculatorConfiguration): SeasonPointsCalculatorConfigurationEntity {
        return SeasonPointsCalculatorConfigurationEntity(
                id = core.id,
                name = core.name,
                resultsTypeToCalculatorMap = core.resultsTypeToCalculatorMap.map { it.key.key to it.value.id }.toMap(),
                rankingSortId = core.rankingSort.id,
                takeTopEventScores = core.takeTopEventScores
        )
    }

}
