package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.EventView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.constraint.EventPersistConstraints
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PolicyService
import java.nio.file.Path
import kotlin.io.path.createFile

@ExtendWith(MockKExtension::class)
class EventAddCommandTest : DIAware {

    lateinit var command: EventAddCommand

    override val di: DI = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    @TempDir lateinit var root: Path

    @MockK lateinit var view: EventView

    private val service: EventService by instance()
    private val policyService: PolicyService by instance()
    private val constraints: EventPersistConstraints by instance()

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        val testConfigs = TestConfigurations(root)
        global = GlobalModel(
            environment = TestEnvironments.temporary(
                di = di,
                root = root,
                configuration = testConfigs.testConfiguration(),
                databaseConfiguration = testConfigs.testDatabaseConfigurations.foo
            )
        )
        command = EventAddCommand(di, global)
            .context {
                console = testConsole
            }
    }

    @Test
    fun `It should create event`() {
        val create = TestEvents.Lscc2019.points1.copy(
            motorsportReg = Event.MotorsportRegMetadata(id = "motorsportreg-event-id")
        )
        every { policyService.findById(create.policy.id) } returns create.policy
        val crispyFishRoot = global.requireEnvironment().requireDatabaseConfiguration().crispyFishDatabase
        val crispyFishEventControlFile = crispyFishRoot.resolve(create.requireCrispyFish().eventControlFile)
            .also { it.createFile() }
        val crispyFishClassDefinitionFile = crispyFishRoot.resolve(create.requireCrispyFish().classDefinitionFile)
            .also { it.createFile() }
        every {
            service.create(
                id = create.id,
                name = create.name,
                date = create.date,
                crispyFishEventControlFile = create.requireCrispyFish().eventControlFile,
                crispyFishClassDefinitionFile = create.requireCrispyFish().classDefinitionFile,
                motorsportRegEventId = create.motorsportReg?.id,
                policy = create.policy
            )
        } returns create
        val viewRendered = "view rendered ${create.id} with crispy fish ${create.crispyFish}"
        every { view.render(create) } returns viewRendered

        command.parse(
            arrayOf(
                "--id", "${create.id}",
                "--name", create.name,
                "--date", "${create.date}",
                "--crispy-fish-event-control-file", "$crispyFishEventControlFile",
                "--crispy-fish-class-definition-file", "$crispyFishClassDefinitionFile",
                "--motorsportreg-event-id", "${create.motorsportReg?.id}",
                "--policy-id", "${create.policy.id}"
            )
        )

        verifySequence {
            policyService.findById(create.policy.id)
            service.create(
                id = create.id,
                name = create.name,
                date = create.date,
                crispyFishEventControlFile = create.requireCrispyFish().eventControlFile,
                crispyFishClassDefinitionFile = create.requireCrispyFish().classDefinitionFile,
                motorsportRegEventId = create.motorsportReg?.id,
                policy = create.policy
            )
            view.render(create)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}