package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.EventView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventService
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class EventGetCommandTest : DIAware {

    lateinit var command: EventGetCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    @TempDir lateinit var root: Path
    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    private val service: EventService by instance()
    @MockK lateinit var view: EventView

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        val testConfigs = TestConfigurations(root)
        global = GlobalModel(
            environment = TestEnvironments.temporary(di, root, testConfigs.testConfiguration(), testConfigs.testDatabaseConfigurations.foo)
        )
        command = EventGetCommand(di, global)
            .context {
                console = testConsole
            }
    }

    @Test
    fun `It should get event by ID`() {
        val event = TestEvents.Lscc2019.points1
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        val viewRendered = "view rendered event ${event.id}"
        every { view.render(event) } returns viewRendered

        command.parse(arrayOf(
            "${event.id}"
        ))

        coVerifySequence {
            service.findByKey(event.id)
            view.render(event)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}