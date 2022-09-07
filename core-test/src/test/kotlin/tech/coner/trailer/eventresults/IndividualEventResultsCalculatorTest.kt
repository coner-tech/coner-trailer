package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import io.mockk.junit5.MockKExtension
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
        // Event.Lifecycle.CREATE cases
        CREATE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Create.noParticipantsYet,
            expected = TestIndividualEventResults.Lifecycles.Create.noParticipantsYet
        ),
        CREATE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Create.runsWithoutSignage,
            expected = TestIndividualEventResults.Lifecycles.Create.runsWithoutSignage
        ),
        CREATE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Create.runsWithoutParticipants,
            expected = TestIndividualEventResults.Lifecycles.Create.runsWithoutParticipants
        ),
        CREATE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.participantsWithoutRuns,
            expected = TestIndividualEventResults.Lifecycles.Create.participantsWithoutRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Create.someParticipantsWithSomeRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Create.someParticipantsWithAllRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Create.allParticipantsWithSomeRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Create.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Create.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.PRE cases
        PRE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Pre.noParticipantsYet,
            expected = TestIndividualEventResults.Lifecycles.Pre.noParticipantsYet
        ),
        PRE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Pre.runsWithoutSignage,
            expected = TestIndividualEventResults.Lifecycles.Pre.runsWithoutSignage
        ),
        PRE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Pre.runsWithoutParticipants,
            expected = TestIndividualEventResults.Lifecycles.Pre.runsWithoutParticipants
        ),
        PRE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.participantsWithoutRuns,
            expected = TestIndividualEventResults.Lifecycles.Pre.participantsWithoutRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Pre.someParticipantsWithSomeRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Pre.someParticipantsWithAllRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Pre.allParticipantsWithSomeRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Pre.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Pre.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.ACTIVE cases
        ACTIVE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Active.noParticipantsYet,
            expected = TestIndividualEventResults.Lifecycles.Active.noParticipantsYet
        ),
        ACTIVE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Active.runsWithoutSignage,
            expected = TestIndividualEventResults.Lifecycles.Active.runsWithoutSignage
        ),
        ACTIVE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Active.runsWithoutParticipants,
            expected = TestIndividualEventResults.Lifecycles.Active.runsWithoutParticipants
        ),
        ACTIVE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.participantsWithoutRuns,
            expected = TestIndividualEventResults.Lifecycles.Active.participantsWithoutRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Active.someParticipantsWithSomeRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Active.someParticipantsWithAllRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Active.allParticipantsWithSomeRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Active.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Active.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.POST cases
        POST_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.Lifecycles.Post.noParticipantsYet,
            expected = TestIndividualEventResults.Lifecycles.Post.noParticipantsYet
        ),
        POST_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.Lifecycles.Post.runsWithoutSignage,
            expected = TestIndividualEventResults.Lifecycles.Post.runsWithoutSignage
        ),
        POST_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.Lifecycles.Post.runsWithoutParticipants,
            expected = TestIndividualEventResults.Lifecycles.Post.runsWithoutParticipants
        ),
        POST_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.participantsWithoutRuns,
            expected = TestIndividualEventResults.Lifecycles.Post.participantsWithoutRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Post.someParticipantsWithSomeRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Post.someParticipantsWithAllRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.Lifecycles.Post.allParticipantsWithSomeRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.Lifecycles.Post.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.Lifecycles.Post.allParticipantsWithAllRuns
        ),
    }

    @ParameterizedTest
    @EnumSource
    fun `It should calculate expected results for events in various states`(params: Fixtures) {
        val subject = factory(params.eventContext)

        val actual = subject.calculate()

        assertThat(actual).all {
            resultsByParticipant().hasSize(params.expected.resultsByIndividual.size)
            isEqualTo(params.expected)
        }
    }

    private fun factory(eventContext: EventContext): IndividualEventResultsCalculator {
        val finalScoreFactory = AutocrossFinalScoreFactory()
        return IndividualEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculators = listOf(
                RawEventResultsCalculator(eventContext, finalScoreFactory),
                PaxEventResultsCalculator(eventContext, finalScoreFactory)
            ),
            clazzEventResultsCalculator = ClazzEventResultsCalculator(eventContext, finalScoreFactory)
        )
    }
}