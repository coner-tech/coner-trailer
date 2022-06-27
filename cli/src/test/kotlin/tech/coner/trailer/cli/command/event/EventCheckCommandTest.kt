package tech.coner.trailer.cli.command.event

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
import tech.coner.trailer.Event
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.CrispyFishRegistrationTableView
import tech.coner.trailer.cli.view.PeopleMapKeyTableView
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.io.payload.EventHealthCheckOutcome
import tech.coner.trailer.render.text.TextRunsRenderer
import java.util.*

@ExtendWith(MockKExtension::class)
class EventCheckCommandTest : DIAware {

    lateinit var command: EventCheckCommand

    override val di: DI = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { registrationTableView }
        bindInstance { peopleMapKeyTableView }
        bindInstance { textRunsRenderer }
    }
    override val diContext = diContext { command.diContext.value }

    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    @MockK lateinit var registrationTableView: CrispyFishRegistrationTableView
    @MockK lateinit var peopleMapKeyTableView: PeopleMapKeyTableView
    @MockK lateinit var textRunsRenderer: TextRunsRenderer

    private val service: EventService by instance()

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventCheckCommand(di, global)
            .context {
                console = testConsole
            }
    }

    @Test
    fun `It should check event and report fixable problems`() {
        val checkId = UUID.randomUUID()
        val checkCrispyFish: Event.CrispyFishMetadata = mockk()
        val checkMotorsportReg: Event.MotorsportRegMetadata = mockk()
        val check: Event = mockk {
            every { id } returns checkId
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
        every { service.findById(checkId) } returns check
        every { service.check(check) } returns result
        every { registrationTableView.render(any()) } returns "registrationTableView rendered"
        every { peopleMapKeyTableView.render(any()) } returns "peopleMapKeyTableView rendered"
        every { textRunsRenderer.render(runs = any()) } returns "textRunRenderer rendered"

        command.parse(arrayOf("$checkId"))

        verifySequence {
            service.findById(checkId)
            service.check(check)
            registrationTableView.render(listOf(result.unmappedMotorsportRegPersonMatches.single().first))
            registrationTableView.render(result.unmappedClubMemberIdNullRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdNotFoundRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdAmbiguousRegistrations)
            registrationTableView.render(result.unmappedClubMemberIdMatchButNameMismatchRegistrations)
            registrationTableView.render(result.unmappedExactMatchRegistrations)
            peopleMapKeyTableView.render(result.unusedPeopleMapKeys)
            textRunsRenderer.render(result.runsWithInvalidSignage)
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
        every { service.findById(checkId) } returns check
        every { service.check(check) } returns result

        command.parse(arrayOf("$checkId"))

        verifySequence {
            service.findById(checkId)
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
        every { service.findById(checkId) } returns check
        every { service.check(check) } throws IllegalStateException()

        assertThrows<IllegalStateException> {
            command.parse(arrayOf("$checkId"))
        }

        verifySequence {
            service.findById(checkId)
            service.check(check)
        }
        assertThat(testConsole.output).isEmpty()
    }
}