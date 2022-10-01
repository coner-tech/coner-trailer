package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Policy
import kotlin.coroutines.CoroutineContext

class ParticipantService(
    coroutineContext: CoroutineContext,
    private val crispyFishParticipantService: CrispyFishParticipantService
) : CoroutineContext by coroutineContext {

    suspend fun list(event: Event): Result<List<Participant>> {
        return when (event.policy.requireAuthoritativeParticipantDataSource()) {
            Policy.DataSource.CrispyFish -> crispyFishParticipantService.list(event)
        }
            .map { it.sortedWith(Participant.Sorts.signage) }
    }


}