package tech.coner.trailer.eventresults

import tech.coner.trailer.Run

interface RunScoreFactory {
    fun score(run: Run): Score


    fun Run.requireTime() = requireNotNull(time) {
        "Run lacks time. Not eligible for scoring."
    }

    fun Run.requireParticipantClassing() = requireNotNull(participant?.signage?.classing) {
        "Run lacks participant classing. Not eligible for scoring."
    }
}