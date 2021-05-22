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
            cones = if (cfRun.penaltyType == PenaltyType.CONE) cfRun.cones else null,
            didNotFinish = if (cfRun.penaltyType == PenaltyType.DID_NOT_FINISH) true else null,
            disqualified = if (cfRun.penaltyType == PenaltyType.DISQUALIFIED) true else null,
            rerun =  if (cfRun.penaltyType == PenaltyType.RERUN) true else null,
            time = cfRun.timeScratchAsString?.let { Time(it) }
        )
    }
}