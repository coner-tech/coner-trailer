package org.coner.trailer.eventresults

import org.coner.trailer.Run

interface RunScoreFactory {
    fun score(run: Run): Score


    fun Run.requireTime() = requireNotNull(time) {
        "Run lacks time. Not eligible for scoring."
    }

    fun Run.requireParticipantClassing() = requireNotNull(participant?.classing) {
        "Run lacks participant classing. Not eligible for scoring."
    }
}