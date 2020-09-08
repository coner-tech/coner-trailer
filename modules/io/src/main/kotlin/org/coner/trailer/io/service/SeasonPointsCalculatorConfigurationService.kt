package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.SeasonPointsCalculatorConfigurationResource
import org.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration

class SeasonPointsCalculatorConfigurationService(
        private val resource: SeasonPointsCalculatorConfigurationResource,
        private val mapper: SeasonPointsCalculatorConfigurationMapper
) {

    fun create(seasonPointsCalculatorConfiguration: SeasonPointsCalculatorConfiguration) {
        resource.create(mapper.toSnoozle(seasonPointsCalculatorConfiguration))
    }
}