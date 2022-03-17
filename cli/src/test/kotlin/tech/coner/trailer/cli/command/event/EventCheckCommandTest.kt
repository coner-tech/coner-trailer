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
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.crispyfish.model.Registration
import java.util.*

@ExtendWith(MockKExtension::class)
class EventCheckCommandTest : DIAware {

    lateinit var command: EventCheckCommand

    override val di: DI = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { registrationTableView }
        bindInstance { peopleMapKeyTableView }
    }
    override val diContext = diContext { command.diContext.value }

    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    @MockK lateinit var registrationTableView: CrispyFishRegistrationTableView
    @MockK lateinit var peopleMapKeyTableView: PeopleMapKeyTableView

    private val service: EventService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()

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
        val context: CrispyFishEventMappingContext = mockk()
        val result = EventService.CheckResult(
            unmappable = emptyList(),
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
        assertThat(testConsole.output).all {
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
            unmappable = emptyList(),
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

        assertThrows<IllegalStateException> {
            command.parse(arrayOf("$checkId"))
        }

        verifySequence {
            service.findById(checkId)
        }
        assertThat(testConsole.output).isEmpty()
    }
}