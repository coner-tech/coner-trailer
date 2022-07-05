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
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators

@ExtendWith(MockKExtension::class)
class EventPointsCalculatorSetCommandTest : DIAware {

    lateinit var command: EventPointsCalculatorSetCommand

    override val di = DI.lazy {
        import(testCliktModule)
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
        command = EventPointsCalculatorSetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should set all properties of calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findById(calculator.id) } returns calculator
        val set = calculator.copy(
                name = "set",
                didNotStartPoints = 0,
                didNotFinishPoints = 1,
                positionToPoints = mapOf(
                        1 to 5,
                        2 to 4,
                        3 to 3
                ),
                defaultPoints = 2
        )
        every { service.update(eq(set)) } answers { Unit }
        val viewRendered = "view rendered ${set.name}"
        every { view.render(eq(set)) } returns viewRendered

        command.parse(arrayOf(
                set.id.toString(),
                "--name", set.name,
                "--did-not-start-points", set.didNotStartPoints.toString(),
                "--did-not-finish-points", set.didNotFinishPoints.toString(),
                "--position-to-points", "1", set.positionToPoints[1].toString(),
                "--position-to-points", "2", set.positionToPoints[2].toString(),
                "--position-to-points", "3", set.positionToPoints[3].toString(),
                "--default-points", set.defaultPoints.toString()
        ))

        verifySequence {
            service.findById(set.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should only rename a calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findById(calculator.id) } returns calculator
        val set = calculator.copy(
                name = "set"
        )
        every { service.update(eq(set)) } answers { Unit }
        val viewRendered = "view rendered ${set.name}"
        every { view.render(eq(set)) } returns viewRendered

        command.parse(arrayOf(
                set.id.toString(),
                "--name", set.name
        ))

        verifySequence {
            service.findById(set.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}
