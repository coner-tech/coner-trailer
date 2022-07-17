package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.scoped
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.*

val mockkEventResultsModule = DI.Module("mockk for tech.coner.trailer.eventresults") {
    bind { scoped(EventResultsSessionScope).multiton { _: EventContext ->
        mockk<RawEventResultsCalculator>()
    } }
    bind { scoped(EventResultsSessionScope).multiton { _: EventContext ->
        mockk<PaxEventResultsCalculator>()
    } }
    bind { scoped(EventResultsSessionScope).multiton { _: EventContext ->
        mockk<ClazzEventResultsCalculator>()
    } }
    bind { scoped(EventResultsSessionScope).multiton { _: EventContext ->
        mockk<TopTimesEventResultsCalculator>()
    } }
    bind { scoped(EventResultsSessionScope).multiton { _: EventContext ->
        mockk<ClazzEventResultsCalculator>()
    } }
    bind { scoped(EventResultsSessionScope).multiton { _: EventContext ->
        mockk<IndividualEventResultsCalculator>()
    } }
}