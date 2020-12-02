package org.coner.trailer.io.mapper

import org.coner.trailer.Event
import org.coner.trailer.Participant
import org.coner.trailer.datasource.snoozle.entity.ParticipantEntity

class ParticipantMapper(
        private val crispyFishGroupingService: CrispyFishGroupingService
) {

    fun toCoreSignage(coreEvent: Event, snoozleSignage: ParticipantEntity.Signage): Participant.Signage {
        return Participant.Signage(
                grouping = crispyFishGroupingService.find()
        )
    }
}