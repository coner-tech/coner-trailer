package tech.coner.trailer.io.service

import kotlin.coroutines.CoroutineContext
import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.Run

class RunService(
    coroutineContext: CoroutineContext,
    private val crispyFishRunService: CrispyFishRunService
) : CoroutineContext by coroutineContext {

    suspend fun list(event: Event): Result<List<Run>> {
        return when (event.policy.requireAuthoritativeRunDataSource()) {
            Policy.DataSource.CrispyFish -> crispyFishRunService.list(event)
        }
    }

    suspend fun latest(event: Event, count: Int): Result<List<Run>> = when (event.policy.requireAuthoritativeRunDataSource()) {
        Policy.DataSource.CrispyFish -> crispyFishRunService.list(event)
    }
        .map { it.sortedBy(Run::sequence) }
        .map { it.takeLast(count) }
}