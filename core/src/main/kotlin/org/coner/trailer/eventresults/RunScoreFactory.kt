package org.coner.trailer.eventresults

import org.coner.trailer.Run

interface RunScoreFactory {
    fun score(run: Run): Score


    fun Run.requireTime() = requireNotNull(time) {
        "Run lacks time. Not eligible for scoring."
    }

    fun Run.requireParticipantSignageGrouping() = requireNotNull(participant?.signage?.grouping) {
        "Run lacks participant grouping. Not eligible for scoring."
    }
}