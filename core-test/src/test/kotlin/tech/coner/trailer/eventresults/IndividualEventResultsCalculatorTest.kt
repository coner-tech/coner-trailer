package tech.coner.trailer.eventresults

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts

@ExtendWith(MockKExtension::class)
class IndividualEventResultsCalculatorTest {

    lateinit var subject: IndividualEventResultsCalculator

    val rawEventResultsCalculator = RawEventResultsCalculator()
    @MockK lateinit var paxEventResultsCalculator: PaxEventResultsCalculator
    @MockK lateinit var clazzEventResultsCalculator: ClazzEventResultsCalculator

    @Test
    fun `It should calculate for event created with no participants and no runs`() {
        val eventContext = TestEventContexts.LifecycleCases.Create.noParticipantsYet
        subject = IndividualEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculators = listOf(
                RawEventResultsCalculator.create(eventContext)
            )
        )
        TODO()
    }

    @Test
    fun `It should calculate for event created with some participants with some runs`() {
        TODO()
    }

    @Test
    fun `It should calculate for event created with some participants with all runs`() {
        TODO()
    }

    @Test
    fun `It should calculate for event created with all participants with all runs`() {
        TODO()
    }
}