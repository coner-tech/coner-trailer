package tech.coner.trailer.io.verifier

import tech.coner.trailer.Participant
import tech.coner.trailer.Run

class RunWithInvalidSignageVerifier {
    fun verify(
        allParticipants: List<Participant>,
        allRuns: List<Run>
    ): List<Run> {
        val validSignages = allParticipants
            .mapNotNull { it.signage }
            .toSet()
        return allRuns.filter { it.signage != null && !validSignages.contains(it.signage) }
    }
}