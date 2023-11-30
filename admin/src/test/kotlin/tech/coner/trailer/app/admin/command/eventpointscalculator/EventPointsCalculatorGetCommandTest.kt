package tech.coner.trailer.app.admin.command.eventpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.EventPointsCalculatorView
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators

class EventPointsCalculatorGetCommandTest : BaseDataSessionCommandTest<EventPointsCalculatorGetCommand>() {

    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    override fun DirectDI.createCommand() = instance<EventPointsCalculatorGetCommand>()

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
