package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.eventresults.IndividualEventResults.Comparators.allByParticipant

@ExtendWith(MockKExtension::class)
class IndividualEventResultsCalculatorTest {

    enum class Fixtures(
        val eventContext: EventContext,
        val expected: IndividualEventResults
    ) {
        // Event.Lifecycle.CREATE cases
        CREATE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Create.noParticipantsYet,
            expected = TestIndividualEventResults.LifecyclePhases.Create.noParticipantsYet
        ),
        CREATE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Create.runsWithoutSignage,
            expected = TestIndividualEventResults.LifecyclePhases.Create.runsWithoutSignage
        ),
        CREATE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Create.runsWithoutParticipants,
            expected = TestIndividualEventResults.LifecyclePhases.Create.runsWithoutParticipants
        ),
        CREATE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.participantsWithoutRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Create.participantsWithoutRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Create.someParticipantsWithSomeRuns
        ),
        CREATE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Create.someParticipantsWithAllRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Create.allParticipantsWithSomeRuns
        ),
        CREATE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Create.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Create.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.PRE cases
        PRE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Pre.noParticipantsYet,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.noParticipantsYet
        ),
        PRE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Pre.runsWithoutSignage,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.runsWithoutSignage
        ),
        PRE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Pre.runsWithoutParticipants,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.runsWithoutParticipants
        ),
        PRE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.participantsWithoutRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.participantsWithoutRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.someParticipantsWithSomeRuns
        ),
        PRE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.someParticipantsWithAllRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.allParticipantsWithSomeRuns
        ),
        PRE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Pre.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Pre.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.ACTIVE cases
        ACTIVE_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Active.noParticipantsYet,
            expected = TestIndividualEventResults.LifecyclePhases.Active.noParticipantsYet
        ),
        ACTIVE_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Active.runsWithoutSignage,
            expected = TestIndividualEventResults.LifecyclePhases.Active.runsWithoutSignage
        ),
        ACTIVE_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Active.runsWithoutParticipants,
            expected = TestIndividualEventResults.LifecyclePhases.Active.runsWithoutParticipants
        ),
        ACTIVE_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.participantsWithoutRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Active.participantsWithoutRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Active.someParticipantsWithSomeRuns
        ),
        ACTIVE_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Active.someParticipantsWithAllRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Active.allParticipantsWithSomeRuns
        ),
        ACTIVE_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Active.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Active.allParticipantsWithAllRuns
        ),
        // Event.Lifecycle.POST cases
        POST_NO_PARTICIPANTS_YET(
            eventContext = TestEventContexts.LifecycleCases.Post.noParticipantsYet,
            expected = TestIndividualEventResults.LifecyclePhases.Post.noParticipantsYet
        ),
        POST_RUNS_WITHOUT_SIGNAGE(
            eventContext = TestEventContexts.LifecycleCases.Post.runsWithoutSignage,
            expected = TestIndividualEventResults.LifecyclePhases.Post.runsWithoutSignage
        ),
        POST_RUNS_WITHOUT_PARTICIPANTS(
            eventContext = TestEventContexts.LifecycleCases.Post.runsWithoutParticipants,
            expected = TestIndividualEventResults.LifecyclePhases.Post.runsWithoutParticipants
        ),
        POST_PARTICIPANTS_WITHOUT_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.participantsWithoutRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Post.participantsWithoutRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.someParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Post.someParticipantsWithSomeRuns
        ),
        POST_SOME_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.someParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Post.someParticipantsWithAllRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_SOME_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.allParticipantsWithSomeRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Post.allParticipantsWithSomeRuns
        ),
        POST_ALL_PARTICIPANTS_WITH_ALL_RUNS(
            eventContext = TestEventContexts.LifecycleCases.Post.allParticipantsWithAllRuns,
            expected = TestIndividualEventResults.LifecyclePhases.Post.allParticipantsWithAllRuns
        ),
    }

    @ParameterizedTest
    @EnumSource
    fun `It should calculate expected results for events in various states`(params: Fixtures) {
        val subject = factory(params.eventContext)

        val actual = subject.calculate()

        assertThat(actual).all {
            allByParticipant().hasSize(params.expected.allByParticipant.size)
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