package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.Run

class RunService(
    private val crispyFishRunService: CrispyFishRunService
) {

    fun list(event: Event): Result<List<Run>> {
        return when (event.policy.authoritativeRunDataSource) {
            Policy.DataSource.CrispyFish -> crispyFishRunService.list(event)
        }
    }
}