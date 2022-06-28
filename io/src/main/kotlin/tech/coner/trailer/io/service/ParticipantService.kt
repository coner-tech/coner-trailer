package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Policy

class ParticipantService(
    private val crispyFishParticipantService: CrispyFishParticipantService
) {

    fun list(event: Event): Result<List<Participant>> {
        return when (event.policy.authoritativeParticipantDataSource) {
            Policy.DataSource.CrispyFish -> crispyFishParticipantService.list(event)
        }
            .map { it.sortedWith(Participant.Sorts.signage) }
    }


}