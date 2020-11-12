package org.coner.trailer.io.mapper

import org.coner.trailer.Participant
import org.coner.trailer.datasource.crispyfish.GroupingMapper
import org.coner.trailer.datasource.snoozle.entity.ParticipantEntity

class ParticipantMapper(
        private val groupingMapper: GroupingMapper
) {

    fun toCoreSignage(snoozleSignage: ParticipantEntity.Signage): Participant.Signage {
        return Participant.Signage(
                groupingMapper.
        )
    }
}