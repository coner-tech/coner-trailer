package tech.coner.trailer.eventresults

import tech.coner.trailer.Run

class RunEligibilityQualifier {

    fun check(run: Run, participantResultRunIndex: Int, maxRunCount: Int): Boolean {
        require(participantResultRunIndex >= 0) {
            "participantResultRunIndex must be greater than or equal to  0, but was $participantResultRunIndex"
        }
        return when {
            participantResultRunIndex >= maxRunCount -> false
            run.participant == null -> false
            run.time == null -> false
            run.rerun -> false
            run.disqualified -> false
            else -> true
        }
    }
}
