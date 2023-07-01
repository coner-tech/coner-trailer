package tech.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.DI
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.CrispyFishRegistrationTableView
import tech.coner.trailer.cli.view.PeopleMapKeyTableView
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.di.Format
import tech.coner.trailer.io.payload.EventHealthCheckOutcome
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.render.RunsRenderer

class EventCheckCommandTest : BaseDataSessionCommandTest<EventCheckCommand>() {

    private val service: EventService by instance()
    private val registrationTableView: CrispyFishRegistrationTableView by instance()
    private val peopleMapKeyTableView: PeopleMapKeyTableView by instance()
    private val runRendererFactory: (Policy) -> RunsRenderer by factory(Format.TEXT)
   lateinit var runRenderer: RunsRenderer

    override fun createCommand(di: DI, global: GlobalModel) = EventCheckCommand(di, global)

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
        coEvery { service.findByKey(checkId) } returns Result.success(check)
        coEvery { service.check(check) } returns result
        every { registrationTableView.render(any()) } returns "registrationTableView rendered"
        every { peopleMapKeyTableView.render(any()) } returns "peopleMapKeyTableView rendered"
        runRenderer = runRendererFactory(check.policy)
        every { runRenderer.render(any()) } returns "textRunRenderer rendered"

        command.parse(arrayOf("$checkId"))

        coVerifySequence {
            service.findByKey(checkId)
            service.check(check)
            registrationTableView.render(listOf(result.unmappedMotorsportRegPersonMatches.single().first))
            registrationTableView.render(result.unmappedClubMemberIdNullRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdNotFoundRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdAmbiguousRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdMatchButNameMismatchRegistrations)
            registrationTableView.render(result.unmappedExactMatchRegistrations)
            peopleMapKeyTableView.render(result.unusedPeopleMapKeys)
            runRenderer.render(result.runsWithInvalidSignage)
        }
        assertThat(testConsole.output).all {
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
        val context: CrispyFishEventMappingContext = mockk()
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
        coEvery { service.findByKey(checkId) } returns Result.success(check)
        coEvery { service.check(check) } returns result

        command.parse(arrayOf("$checkId"))

        coVerifySequence {
            service.findByKey(checkId)
            service.check(check)
        }
        assertThat(testConsole.output).isEmpty()
    }

    @Test
    fun `It should throw when event is missing crispy fish metadata`() {
        val checkId = UUID.randomUUID()
        val check: Event = mockk {
            every { id } returns checkId
            every { crispyFish } returns null
        }
        coEvery { service.findByKey(checkId) } returns Result.success(check)
        coEvery { service.check(check) } throws IllegalStateException()

        assertThrows<IllegalStateException> {
            command.parse(arrayOf("$checkId"))
        }

        coVerifySequence {
            service.findByKey(checkId)
            service.check(check)
        }
        assertThat(testConsole.output).isEmpty()
    }
}