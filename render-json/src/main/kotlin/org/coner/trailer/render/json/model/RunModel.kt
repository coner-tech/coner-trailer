package org.coner.trailer.render.json.model

import org.coner.trailer.Run
import org.coner.trailer.Time
import org.coner.trailer.render.json.identifier.ParticipantIdentifier

class RunModel(
    val sequence: Int,
    val participantIdentifier: ParticipantIdentifier?,
    val cones: Int,
    val didNotFinish: Boolean,
    val disqualified: Boolean,
    val rerun: Boolean,
    val time: Time?
) {

    constructor(run: Run) : this(
        sequence = run.sequence,
        participantIdentifier = run.participant?.let { ParticipantIdentifier(it) },
        cones = run.cones,
        didNotFinish = run.didNotFinish,
        disqualified = run.disqualified,
        rerun = run.rerun,
        time = run.time
    )
}