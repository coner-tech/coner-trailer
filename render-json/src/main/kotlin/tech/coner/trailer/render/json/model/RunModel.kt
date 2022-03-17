package tech.coner.trailer.render.json.model

import tech.coner.trailer.Run

class RunModel(
    val sequence: Int,
    val participantSignage: String?,
    val cones: Int,
    val didNotFinish: Boolean,
    val disqualified: Boolean,
    val rerun: Boolean,
    val time: String?
) {

    constructor(run: Run) : this(
        sequence = run.sequence,
        participantSignage = run.participant?.signageClassingNumber,
        cones = run.cones,
        didNotFinish = run.didNotFinish,
        disqualified = run.disqualified,
        rerun = run.rerun,
        time = run.time?.value.toString()
    )
}