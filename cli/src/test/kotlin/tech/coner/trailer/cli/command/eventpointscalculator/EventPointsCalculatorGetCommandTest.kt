package tech.coner.trailer.cli.command.eventpointscalculator

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
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators

@ExtendWith(MockKExtension::class)
class EventPointsCalculatorGetCommandTest : DIAware {

    lateinit var command: EventPointsCalculatorGetCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: EventPointsCalculatorService by instance()
    @MockK lateinit var view: EventPointsCalculatorView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventPointsCalculatorGetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should get calculator by id`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findById(calculator.id) } returns calculator
        val viewRenders = "rendered ${calculator.name}"
        every { view.render(calculator) } returns viewRenders

        command.parse(arrayOf("--id", calculator.id.toString()))

        assertThat(testConsole.output).isEqualTo(viewRenders)
        verifySequence {
            service.findById(calculator.id)
            view.render(calculator)
        }
    }

    @Test
    fun `It should get calculator by name`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findByName(calculator.name) } returns calculator
        val viewRenders = "rendered ${calculator.id}"
        every { view.render(calculator) } returns viewRenders

        command.parse(arrayOf("--name", calculator.name))

        assertThat(testConsole.output).isEqualTo(viewRenders)
        verifySequence {
            service.findByName(calculator.name)
            view.render(calculator)
        }
    }
}
