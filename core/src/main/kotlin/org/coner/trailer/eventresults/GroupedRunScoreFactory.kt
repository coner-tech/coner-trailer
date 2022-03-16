package org.coner.trailer.eventresults

import org.coner.trailer.Run

class GroupedRunScoreFactory(
    private val rawTimes: RawTimeRunScoreFactory,
    private val paxTimes: PaxTimeRunScoreFactory
) : RunScoreFactory {
    override fun score(run: Run): Score {
        val classing = run.requireParticipantClassing()
        val factory = when (classing.group?.paxed == true) {
            true -> paxTimes
            false -> rawTimes
        }
        return factory.score(run)
    }

}