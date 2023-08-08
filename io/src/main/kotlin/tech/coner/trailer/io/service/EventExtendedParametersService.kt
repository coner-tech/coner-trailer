package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.EventExtendedParameters
import tech.coner.trailer.Policy
import kotlin.coroutines.CoroutineContext

class EventExtendedParametersService(
    coroutineContext: CoroutineContext,
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService
) : CoroutineContext by coroutineContext {

    suspend fun load(event: Event): Result<EventExtendedParameters> = runCatching {
        when (event.policy.requireAuthoritativeParticipantDataSource()) {
            Policy.DataSource.CrispyFish -> EventExtendedParameters(
                runsPerParticipant = crispyFishEventMappingContextService.load(event, event.requireCrispyFish()).runCount
            )
        }
    }
}