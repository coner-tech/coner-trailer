package tech.coner.trailer.presentation.json.internal.model

import tech.coner.trailer.Run
import tech.coner.trailer.Signage

class RunModel(
    val sequence: Int,
    val participantSignage: Signage?,
    val cones: Int,
    val didNotFinish: Boolean,
    val disqualified: Boolean,
    val rerun: Boolean,
    val time: String?
) {

    constructor(run: Run) : this(
        sequence = run.sequence,
        participantSignage = run.participant?.signage,
        cones = run.cones,
        didNotFinish = run.didNotFinish,
        disqualified = run.disqualified,
        rerun = run.rerun,
        time = run.time?.value.toString()
    )
}