package tech.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*

@ExtendWith(MockKExtension::class)
class EventPointsCalculatorDeleteCommandTest : DIAware {

    lateinit var command: EventPointsCalculatorDeleteCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
    }
    override val diContext = diContext { command.diContext.value }

    private val service: EventPointsCalculatorService by instance()

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventPointsCalculatorDeleteCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should delete calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { service.findById(calculator.id) } returns calculator
        every { service.delete(calculator) } answers { Unit }

        command.parse(arrayOf(calculator.id.toString()))

        verifySequence {
            service.findById(calculator.id)
            service.delete(calculator)
        }
    }

}