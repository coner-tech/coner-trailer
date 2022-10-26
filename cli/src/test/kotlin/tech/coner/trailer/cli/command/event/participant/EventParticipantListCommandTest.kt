package tech.coner.trailer.cli.command.event.participant

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.di.Format
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.render.ParticipantRenderer

@ExtendWith(MockKExtension::class)
class EventParticipantListCommandTest : DIAware,
    CoroutineScope {

    override val coroutineContext = Dispatchers.Main + Job()

    lateinit var command: EventParticipantListCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkServiceModule)
        bindFactory { _: Format -> renderer }
    }
    override val diContext = diContext { command.diContext.value }

    private val eventService: EventService by instance()
    private val participantService: ParticipantService by instance()
    @MockK lateinit var renderer: ParticipantRenderer

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventParticipantListCommand(di, global)
            .context { console = testConsole }
    }

    @AfterEach
    fun after() {
        cancel()
    }

    @Test
    fun `It should list event participants`() {
        val event = TestEvents.Lscc2019.points1
        val participants = listOf(
            TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
            TestParticipants.Lscc2019Points1.JIMMY_MCKENZIE
        )
        coEvery { eventService.findByKey(any()) } returns Result.success(event)
        coEvery { participantService.list(any()) } returns Result.success(participants)
        val render = "participantRenderer rendered participants"
        every { renderer.render(participants) } returns render

        command.parse(arrayOf("${event.id}"))

        coVerifySequence {
            eventService.findByKey(event.id)
            participantService.list(event)
            renderer.render(participants)
        }
        assertThat(testConsole).output().isEqualTo(render)
    }
}