package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Policy

class ClassService(
    private val crispyFishClassService: CrispyFishClassService
) {

    fun list(event: Event): Result<List<tech.coner.trailer.Class>> = runCatching {
        when (event.policy.authoritativeParticipantDataSource) {
            Policy.DataSource.CrispyFish -> crispyFishClassService.loadAllClasses(event.requireCrispyFish().classDefinitionFile)
        }
    }
}