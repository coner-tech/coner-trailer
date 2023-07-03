package tech.coner.trailer.eventresults

interface EventResultsCalculator<ER : EventResults> {
    fun calculate(): ER

    operator fun invoke() = calculate()
}