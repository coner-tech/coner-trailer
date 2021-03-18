package org.coner.trailer.eventresults

class AutocrossFinalScoreFactory : FinalScoreFactory {
    override fun factory(resultRuns: List<ResultRun>): Score? {
        return resultRuns.mapNotNull { it.score }
            .minOrNull()
    }
}