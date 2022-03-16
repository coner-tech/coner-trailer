package org.coner.trailer.datasource.crispyfish

import org.coner.trailer.Participant
import org.coner.trailer.Run
import org.coner.trailer.Time
import tech.coner.crispyfish.model.PenaltyType

class CrispyFishRunMapper {

    fun toCore(
        cfRun: tech.coner.crispyfish.model.Run,
        cfRunIndex: Int,
        participant: Participant,
    ): Run {
        return Run(
            sequence = cfRunIndex + 1,
            participant = participant,
            cones = if (cfRun.penaltyType == PenaltyType.CONE) {
                cfRun.cones ?: 0
            } else {
                0
            },
            didNotFinish = cfRun.penaltyType == PenaltyType.DID_NOT_FINISH,
            disqualified = cfRun.penaltyType == PenaltyType.DISQUALIFIED,
            rerun =  cfRun.penaltyType == PenaltyType.RERUN,
            time = cfRun.timeScratchAsString?.let { Time(it) }
        )
    }
}