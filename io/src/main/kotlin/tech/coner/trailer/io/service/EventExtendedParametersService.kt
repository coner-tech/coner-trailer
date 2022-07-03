package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.EventExtendedParameters
import tech.coner.trailer.Policy

class EventExtendedParametersService(
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService
) {
    fun load(event: Event): Result<EventExtendedParameters> = runCatching {
        when (event.policy.authoritativeParticipantDataSource) {
            Policy.DataSource.CrispyFish -> EventExtendedParameters(
                runsPerParticipant = crispyFishEventMappingContextService.load(event.requireCrispyFish()).runCount
            )
        }
    }
}