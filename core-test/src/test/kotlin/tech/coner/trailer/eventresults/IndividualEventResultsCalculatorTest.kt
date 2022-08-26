package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts

@ExtendWith(MockKExtension::class)
class IndividualEventResultsCalculatorTest {

    enum class Fixtures(
        val eventContext: EventContext,
        val expected: IndividualEventResults
    ) {
        CREATE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Create.noParticipantsYet,
            expected = TestIndividualEventResults.LifecyclePhases.Create.noParticipantsYet
        ),
        CREATE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Create.runsWithoutParticipants,
            expected = TestIndividualEventResults.LifecyclePhases.Create.runsWithoutParticipants
        ),
        CREATE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Create.someParticipantsWithSomeRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Create.someParticipantsWithAllRuns
        ),
//        CREATE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
//            eventContext = TestEventContexts.LifecycleCases.Create.allParticipantsWithAllRuns,
//            expected = TestIndividualEventResults.LifecyclePhases.Create.allParticipantsWithAllRuns
//        )
    }

    @ParameterizedTest
    @EnumSource
    fun `It should calculate expected results for events in various states`(params: Fixtures) {
        val subject = factory(params.eventContext)

        val actual = subject.calculate()

        assertThat(actual).isEqualTo(params.expected)
    }

    /*

    @Test
    fun `It should calculate for event created with all participants with all runs`() {
        TODO()
    }

     */

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