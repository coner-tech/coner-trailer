package org.coner.trailer.eventresults

interface FinalScoreFactory {
    fun factory(resultRuns: List<ResultRun>): Score?
}