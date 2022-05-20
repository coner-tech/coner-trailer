package tech.coner.trailer.io.verification

import tech.coner.trailer.Run

class RunWithInvalidSignageVerifier {
    fun verify(allRuns: List<Run>): List<Run> {
        return allRuns.filter { it.signage != null && it.participant == null }
    }
}