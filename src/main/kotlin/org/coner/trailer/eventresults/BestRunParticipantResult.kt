package org.coner.trailer.eventresults

import org.coner.trailer.Participant

class BestRunParticipantResult(
        position: Int,
        participant: Participant,
        val bestRun: ResultRun
) : ParticipantResult(position = position, participant = participant) {
}