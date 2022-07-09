package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

interface EventResultsCalculator<ER : EventResults> {
    fun calculate(eventContext: EventContext): ER
}