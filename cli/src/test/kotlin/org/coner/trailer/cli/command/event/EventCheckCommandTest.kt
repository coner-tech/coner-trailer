package org.coner.trailer.cli.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.CrispyFishRegistrationTableView
import org.coner.trailer.cli.view.PeopleMapKeyTableView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.util.*
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventCheckCommandTest {

    lateinit var command: EventCheckCommand

    @MockK lateinit var service: EventService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var registrationTableView: CrispyFishRegistrationTableView
    @MockK lateinit var peopleMapKeyTableView: PeopleMapKeyTableView

    lateinit var useConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        useConsole = StringBufferConsole()
        command = EventCheckCommand(DI {
            bind<EventService>() with instance(service)
            bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
            bind<CrispyFishRegistrationTableView>() with instance(registrationTableView)
            bind<PeopleMapKeyTableView>() with instance(peopleMapKeyTableView)
        }).context {
            console = useConsole
        }
    }

    @Test
    fun `It should check event and report all problems`() {
        val checkId = UUID.randomUUID()
        val checkCrispyFish: Event.CrispyFishMetadata = mockk()
        val checkMotorsportReg: Event.MotorsportRegMetadata = mockk()
        val check: Event = mockk {
            every { id } returns checkId
            every { crispyFish } returns checkCrispyFish
            every { motorsportReg } returns checkMotorsportReg
        }
        val context: CrispyFishEventMappingContext = mockk()
        val result = EventService.CheckResult(
            unmappedMotorsportRegPersonMatches = listOf(mockk<Registration>() to mockk()),
            unmappedClubMemberIdNullRegistrations = listOf(mockk()),
            unmappedClubMemberIdNotFoundRegistrations = listOf(mockk()),
            unmappedClubMemberIdAmbiguousRegistrations = listOf(mockk()),
            unmappedClubMemberIdMatchButNameMismatchRegistrations = listOf(mockk()),
            unmappedExactMatchRegistrations = listOf(mockk()),
            unusedPeopleMapKeys = listOf(mockk())
        )
        every { service.findById(checkId) } returns check
        every { crispyFishEventMappingContextService.load(checkCrispyFish) } returns context
        every { service.check(check, context) } returns result
        every { registrationTableView.render(any()) } returns "registrationTableView rendered"
        every { peopleMapKeyTableView.render(any()) } returns "peopleMapKeyTableView rendered"

        command.parse(arrayOf("$checkId"))

        verifySequence {
            service.findById(checkId)
            crispyFishEventMappingContextService.load(checkCrispyFish)
            service.check(check, context)
            registrationTableView.render(listOf(result.unmappedMotorsportRegPersonMatches.single().first))
            registrationTableView.render(result.unmappedClubMemberIdNullRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdNotFoundRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdAmbiguousRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdMatchButNameMismatchRegistrations)
            registrationTableView.render(result.unmappedExactMatchRegistrations)
            peopleMapKeyTableView.render(result.unusedPeopleMapKeys)
        }
        assertThat(useConsole.output).all {
            contains("Found unmapped registration(s) with club member ID null")
            contains("Found unmapped registration(s) with club member ID not found")
            contains("Found unmapped registration(s) with club member ID ambiguous")
            contains("Found unmapped registration(s) with club member ID match but name mismatch")
            contains("Found unmapped registration(s) with exact matching people")
            contains("Found unused people map keys")
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
        val result = EventService.CheckResult(
            unmappedMotorsportRegPersonMatches = emptyList(),
            unmappedClubMemberIdNullRegistrations = emptyList(),
            unmappedClubMemberIdNotFoundRegistrations = emptyList(),
            unmappedClubMemberIdAmbiguousRegistrations = emptyList(),
            unmappedClubMemberIdMatchButNameMismatchRegistrations = emptyList(),
            unmappedExactMatchRegistrations = emptyList(),
            unusedPeopleMapKeys = emptyList()
        )
        every { service.findById(checkId) } returns check
        every { crispyFishEventMappingContextService.load(checkCrispyFish) } returns context
        every { service.check(check, context) } returns result

        command.parse(arrayOf("$checkId"))

        verifySequence {
            service.findById(checkId)
            crispyFishEventMappingContextService.load(checkCrispyFish)
            service.check(check, context)
        }
        assertThat(useConsole.output).isEmpty()
    }

    @Test
    fun `It should throw when event is missing crispy fish metadata`() {
        val checkId = UUID.randomUUID()
        val check: Event = mockk {
            every { id } returns checkId
            every { crispyFish } returns null
        }
        every { service.findById(checkId) } returns check

        assertThrows<IllegalStateException> {
            command.parse(arrayOf("$checkId"))
        }

        verifySequence {
            service.findById(checkId)
        }
        assertThat(useConsole.output).isEmpty()
    }
}