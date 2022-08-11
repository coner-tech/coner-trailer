package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.*

class MockkEventResultsFixture {

    val rawCalculator: RawEventResultsCalculator by lazy { mockk() }
    val paxCalculator: PaxEventResultsCalculator by lazy { mockk() }
    val clazzCalculator: ClazzEventResultsCalculator by lazy { mockk() }
    val topTimesCalculator: TopTimesEventResultsCalculator by lazy { mockk() }
    val comprehensiveCalculator: ComprehensiveEventResultsCalculator by lazy { mockk() }
    val individualCalculator: IndividualEventResultsCalculator by lazy { mockk() }

    val module = DI.Module("mockk for tech.coner.trailer.eventresults") {
        bind {
            scoped(EventResultsSessionScope)
                .factory { _: EventContext ->
                    rawCalculator
                }
        }
        bind {
            scoped(EventResultsSessionScope)
                .factory { _: EventContext ->
                    paxCalculator
                }
        }
        bind {
            scoped(EventResultsSessionScope)
                .factory { _: EventContext ->
                    clazzCalculator
                }
        }
        bind {
            scoped(EventResultsSessionScope)
                .factory { _: EventContext ->
                    topTimesCalculator
                }
        }
        bind {
            scoped(EventResultsSessionScope)
                .factory { _: EventContext ->
                    comprehensiveCalculator
                }
        }
        bind {
            scoped(EventResultsSessionScope)
                .factory { _: EventContext ->
                    individualCalculator
                }
        }
    }
}
