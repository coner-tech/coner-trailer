package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import assertk.assertions.isNotZero
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.Run
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.CrispyFishRegistrationTableView
import tech.coner.trailer.app.admin.view.PeopleMapKeyTableView
import tech.coner.trailer.io.payload.EventHealthCheckOutcome
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel
import tech.coner.trailer.presentation.text.view.TextCollectionView
import java.util.*

class EventCheckCommandTest : BaseDataSessionCommandTest<EventCheckCommand>() {

    private val eventService: EventService by instance()
    private val eventContextService: EventContextService by instance()
    private val registrationTableView: CrispyFishRegistrationTableView by instance()
    private val peopleMapKeyTableView: PeopleMapKeyTableView by instance()
    private val runCollectionModelAdapter: Adapter<Pair<Event, Collection<Run>>, RunCollectionModel> by instance()
    private val runsTextView: TextCollectionView<RunModel, RunCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<EventCheckCommand>()

    @Test
    fun `It should check event and report fixable problems`() {
        val checkId = UUID.randomUUID()
        val checkPolicy: Policy = mockk()
        val checkCrispyFish: Event.CrispyFishMetadata = mockk()
        val checkMotorsportReg: Event.MotorsportRegMetadata = mockk()
        val check: Event = mockk {
            every { id } returns checkId
            every { policy } returns checkPolicy
            every { crispyFish } returns checkCrispyFish
            every { motorsportReg } returns checkMotorsportReg
        }
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val result = EventHealthCheckOutcome(
            unmappable = emptyList(),
            unmappedMotorsportRegPersonMatches = listOf(mockk<Registration>() to mockk()),
            unmappedClubMemberIdNullRegistrations = listOf(mockk()),
            unmappedClubMemberIdNotFoundRegistrations = listOf(mockk()),
            unmappedClubMemberIdAmbiguousRegistrations = listOf(mockk()),
            unmappedClubMemberIdMatchButNameMismatchRegistrations = listOf(mockk()),
            unmappedExactMatchRegistrations = listOf(mockk()),
            unusedPeopleMapKeys = listOf(mockk()),
            runsWithInvalidSignage = listOf(mockk())
        )
        coEvery { eventService.findByKey(checkId) } returns Result.success(check)
        coEvery { eventService.check(check) } returns result
        coEvery { eventContextService.load(check) } returns Result.success(eventContext)
        every { registrationTableView.render(any()) } returns "registrationTableView rendered"
        every { peopleMapKeyTableView.render(any()) } returns "peopleMapKeyTableView rendered"
        val runsWithInvalidSignageModel: RunCollectionModel = mockk()
        every { runCollectionModelAdapter(any()) } returns runsWithInvalidSignageModel
        every { runsTextView(any()) } returns "runsTextView rendered"

        val testResult = command.test(arrayOf("$checkId"))

        coVerifySequence {
            eventService.findByKey(checkId)
            eventContextService.load(check)
            eventService.check(check)
            registrationTableView.render(listOf(result.unmappedMotorsportRegPersonMatches.single().first))
            registrationTableView.render(result.unmappedClubMemberIdNullRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdNotFoundRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdAmbiguousRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdMatchButNameMismatchRegistrations)
            registrationTableView.render(result.unmappedExactMatchRegistrations)
            peopleMapKeyTableView.render(result.unusedPeopleMapKeys)
            runCollectionModelAdapter(check to result.runsWithInvalidSignage)
            runsTextView(runsWithInvalidSignageModel)
        }
        confirmVerified(
            eventService,
            eventContextService,
            registrationTableView,
            peopleMapKeyTableView,
            runCollectionModelAdapter,
            runsTextView
        )
        assertThat(testResult).stdout().all {
            contains("Found unmapped registration(s) with club member ID null")
            contains("Found unmapped registration(s) with club member ID not found")
            contains("Found unmapped registration(s) with club member ID ambiguous")
            contains("Found unmapped registration(s) with club member ID match but name mismatch")
            contains("Found unmapped registration(s) with exact matching people")
            contains("Found unused people map keys")
            contains("Found runs with invalid signage")
        }
    }

    @Test
    fun `It should check event and finish silently if there are no problems`() {
        val checkId = UUID.randomUUID()
        val checkCrispyFish: Event.CrispyFishMetadata = mockk()
        val check: Event = mockk {
            every { id } returns checkId
            every { crispyFish } returns checkCrispyFish
        }
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        coEvery { eventContextService.load(any()) } returns Result.success(eventContext)
        val result = EventHealthCheckOutcome(
            unmappable = emptyList(),
            unmappedMotorsportRegPersonMatches = emptyList(),
            unmappedClubMemberIdNullRegistrations = emptyList(),
            unmappedClubMemberIdNotFoundRegistrations = emptyList(),
            unmappedClubMemberIdAmbiguousRegistrations = emptyList(),
            unmappedClubMemberIdMatchButNameMismatchRegistrations = emptyList(),
            unmappedExactMatchRegistrations = emptyList(),
            unusedPeopleMapKeys = emptyList(),
            runsWithInvalidSignage = emptyList()
        )
        coEvery { eventService.findByKey(checkId) } returns Result.success(check)
        coEvery { eventService.check(check) } returns result

        val testResult = command.test(arrayOf("$checkId"))

        coVerifySequence {
            eventService.findByKey(checkId)
            eventContextService.load(check)
            eventService.check(check)
        }
        confirmVerified(eventService, eventContextService)
        assertThat(testResult).stdout().isEmpty()
    }

    @Test
    fun `It should throw when event is missing crispy fish metadata`() {
        val checkId = UUID.randomUUID()
        val check: Event = mockk {
            every { id } returns checkId
            every { crispyFish } returns null
        }
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        coEvery { eventService.findByKey(any()) } returns Result.success(check)
        coEvery { eventContextService.load(any()) } returns Result.success(eventContext)
        val exception = IllegalStateException()
        coEvery { eventService.check(any()) } throws exception
        arrangeDefaultErrorHandling()

        val testResult = command.test(arrayOf("$checkId"))

        assertThat(testResult).statusCode().isNotZero()
        verifyDefaultErrorHandlingInvoked(testResult, exception)
        coVerifySequence {
            eventService.findByKey(checkId)
            eventContextService.load(check)
            eventService.check(check)
        }
        assertThat(testResult).stdout().isEmpty()
    }
}