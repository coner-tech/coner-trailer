package org.coner.trailer.eventresults

class AutocrossFinalScoreFactory : FinalScoreFactory {
    override fun score(resultRuns: List<ResultRun>): Score? {
        return bestRun(resultRuns)?.score
    }

    override fun bestRun(resultRuns: List<ResultRun>): ResultRun? {
        return resultRuns
            .filter { it.score != null }
            .minByOrNull { it.score!! }
    }
}