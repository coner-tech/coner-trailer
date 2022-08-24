package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts

@ExtendWith(MockKExtension::class)
class IndividualEventResultsCalculatorTest {

    lateinit var subject: IndividualEventResultsCalculator


    @Test
    fun `It should calculate for event created with no participants and no runs`() {
        subject = factory(TestEventContexts.LifecycleCases.Create.noParticipantsYet)

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(TestIndividualEventResults.LifecyclePhases.Create.noParticipantsYet)
    }

    @Test
    fun `It should calculate for event created with runs but without participants`() {
        subject = factory(TestEventContexts.LifecycleCases.Create.runsWithoutParticipants)

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(TestIndividualEventResults.LifecyclePhases.Create.runsWithoutParticipants)
    }

    @Test
    fun `It should calculate for event created with some participants with some runs`() {
        subject = factory(TestEventContexts.LifecycleCases.Create.someParticipantsWithSomeRuns)

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(TestIndividualEventResults.LifecyclePhases.Create.someParticipantsWithSomeRuns)
    }

    @Test
    fun `It should calculate for event created with some participants with all runs`() {
        TODO()
    }

    @Test
    fun `It should calculate for event created with all participants with all runs`() {
        TODO()
    }

    private fun factory(eventContext: EventContext): IndividualEventResultsCalculator {
        val finalScoreFactory = AutocrossFinalScoreFactory()
        return IndividualEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculators = listOf(
                RawEventResultsCalculator(eventContext, AutocrossFinalScoreFactory()),
                PaxEventResultsCalculator(eventContext, AutocrossFinalScoreFactory())
            ),
            clazzEventResultsCalculator = ClazzEventResultsCalculator(eventContext, finalScoreFactory)
        )
    }
}