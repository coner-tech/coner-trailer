package tech.coner.trailer.datasource.crispyfish

import tech.coner.trailer.Participant
import tech.coner.trailer.Run
import tech.coner.trailer.Time
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.trailer.Signage

class CrispyFishRunMapper {

    fun toCore(
        cfRun: tech.coner.crispyfish.model.Run?,
        cfRunIndex: Int,
        participant: Participant?,
    ): Run {
        return cfRun?.let {
            Run(
                sequence = cfRunIndex + 1,
                participant = participant,
                cones = if (it.penaltyType == PenaltyType.CONE) {
                    cfRun.cones ?: 0
                } else {
                    0
                },
                didNotFinish = it.penaltyType == PenaltyType.DID_NOT_FINISH,
                disqualified = it.penaltyType == PenaltyType.DISQUALIFIED,
                rerun =  it.penaltyType == PenaltyType.RERUN,
                time = it.timeScratchAsString?.let { Time(it) }
            )
        }
            ?: createEmptyRun(cfRunIndex)
    }

    private fun createEmptyRun(index: Int) = Run(
        sequence = index + 1,
        signage = Signage(
            classing = null,
            number = null
        ),
        participant = null
    )
}