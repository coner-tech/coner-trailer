package org.coner.trailer.cli.command.eventpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.AbstractCommandTest
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.di.EnvironmentScope
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.seasonpoints.TestEventPointsCalculators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import java.util.logging.Logger.global

@ExtendWith(MockKExtension::class)
class EventPointsCalculatorAddCommandTest : DIAware {

    lateinit var command: EventPointsCalculatorAddCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val constraints: EventPointsCalculatorPersistConstraints by instance()
    private val service: EventPointsCalculatorService by instance()
    @MockK lateinit var view: EventPointsCalculatorView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventPointsCalculatorAddCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should create calculator`() {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator
        every { constraints.hasUniqueName(calculator.id, calculator.name) } returns true
        every { service.create(eq(calculator)) } answers { Unit }
        val viewRenders = "created ${calculator.id}"
        every { view.render(eq(calculator)) } returns viewRenders

        command.parse(arrayOf(
            "--id", calculator.id.toString(),
            "--name", calculator.name,
            "--position-to-points", "1", "9",
            "--position-to-points", "2", "6",
            "--position-to-points", "3", "4",
            "--position-to-points", "4", "3",
            "--position-to-points", "5", "2",
            "--default-points", "1"
        ))

        verifySequence {
            service.create(eq(calculator))
            view.render(eq(calculator))
        }
        assertThat(testConsole.output).isEqualTo(viewRenders)
    }
}
