package tech.coner.trailer.io.mapper

import tech.coner.trailer.Season
import tech.coner.trailer.datasource.snoozle.entity.SeasonEntity
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService

class SeasonMapper(
        private val seasonPointsCalculatorConfigurationService: SeasonPointsCalculatorConfigurationService
) {

    fun toSnoozle(core: Season): SeasonEntity {
        return SeasonEntity(
                id = core.id,
                name = core.name,
                seasonEventIds = core.seasonEvents.map { it.id }.toList(),
                seasonPointsCalculatorConfigurationId = core.seasonPointsCalculatorConfiguration.id,
                takeScoreCountForPoints = core.takeScoreCountForPoints
        )
    }

    fun toCore(snoozle: SeasonEntity): Season {
        return Season(
                id = snoozle.id,
                name = snoozle.name,
                seasonEvents = emptyList(), // https://github.com/caeos/coner-trailer/issues/28
                seasonPointsCalculatorConfiguration = seasonPointsCalculatorConfigurationService.findById(snoozle.seasonPointsCalculatorConfigurationId),
                takeScoreCountForPoints = snoozle.takeScoreCountForPoints
        )
    }
}