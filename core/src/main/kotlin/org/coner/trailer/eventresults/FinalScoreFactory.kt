package org.coner.trailer.eventresults

interface FinalScoreFactory {
    fun score(resultRuns: List<ResultRun>): Score?

    fun bestRun(resultRuns: List<ResultRun>): ResultRun?
}