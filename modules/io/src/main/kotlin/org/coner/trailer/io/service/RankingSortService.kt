package org.coner.trailer.io.service

import org.coner.trailer.datasource.snoozle.RankingSortResource
import org.coner.trailer.io.mapper.RankingSortMapper

class RankingSortService(
        private val resource: RankingSortResource,
        private val mapper: RankingSortMapper
) {
}