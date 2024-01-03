package tech.coner.trailer.app.admin.command.eventpointscalculator

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
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

        val testResult = command.test(arrayOf("--id", calculator.id.toString()))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRenders)
        }
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

        val testResult = command.test(arrayOf("--name", calculator.name))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRenders)
        }
        verifySequence {
            service.findByName(calculator.name)
            view.render(calculator)
        }
    }
}
