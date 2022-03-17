package tech.coner.trailer.eventresults

class RallycrossFinalScoreFactory : FinalScoreFactory {
    override fun score(resultRuns: List<ResultRun>): Score {
        return Score(resultRuns.sumOf { run -> run.score.value })
    }

    override fun bestRun(resultRuns: List<ResultRun>): ResultRun? {
        TODO("Not yet implemented")
    }
}