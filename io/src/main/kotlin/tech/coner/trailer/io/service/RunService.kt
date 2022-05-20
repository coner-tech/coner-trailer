package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.Run

class RunService(
    private val crispyFishRunService: CrispyFishRunService
) {

    fun list(event: Event): Result<List<Run>> {
        return when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> crispyFishRunService.list(event)
        }
    }
}