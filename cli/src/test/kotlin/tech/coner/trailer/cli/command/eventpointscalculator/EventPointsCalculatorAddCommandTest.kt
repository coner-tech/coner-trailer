package tech.coner.trailer.cli.command.eventpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators

class EventPointsCalculatorAddCommandTest : BaseDataSessionCommandTest<EventPointsCalculatorAddCommand>() {

    private val constraints: EventPointsCalculatorPersistConstraints by instance()
    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = EventPointsCalculatorAddCommand(di, global)

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
