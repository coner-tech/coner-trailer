package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.*

val mockkEventResults = DI.Module("tech.coner.trailer.eventresults mockk") {
    bind<RawEventResultsCalculator> {
        scoped(EventResultsSessionScope).multiton { _: EventContext -> mockk() }
    }
    bind<PaxEventResultsCalculator> {
        scoped(EventResultsSessionScope).multiton { _: EventContext -> mockk() }
    }
    bind<ClazzEventResultsCalculator> {
        scoped(EventResultsSessionScope).multiton { _: EventContext -> mockk() }
    }
    bind<TopTimesEventResultsCalculator> {
        scoped(EventResultsSessionScope).multiton { _: EventContext -> mockk() }
    }
    bind<ComprehensiveEventResultsCalculator> {
        scoped(EventResultsSessionScope).multiton { _: EventContext -> mockk() }
    }
    bind<IndividualEventResultsCalculator> {
        scoped(EventResultsSessionScope).multiton { _: EventContext -> mockk() }
    }
}
