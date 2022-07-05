package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.Run
import kotlin.coroutines.CoroutineContext

class RunService(
    coroutineContext: CoroutineContext,
    private val crispyFishRunService: CrispyFishRunService
) : CoroutineContext by coroutineContext {

    suspend fun list(event: Event): Result<List<Run>> {
        return when (event.policy.authoritativeRunDataSource) {
            Policy.DataSource.CrispyFish -> crispyFishRunService.list(event)
        }
    }
}