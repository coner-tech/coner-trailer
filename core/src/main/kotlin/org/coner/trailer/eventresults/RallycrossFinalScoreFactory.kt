package org.coner.trailer.eventresults

class RallycrossFinalScoreFactory : FinalScoreFactory {
    override fun factory(resultRuns: List<ResultRun>): Score {
        return Score(resultRuns.sumOf { run -> run.score.value })
    }
}