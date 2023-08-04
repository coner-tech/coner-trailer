package tech.coner.trailer.cli.command.eventpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.TestEventPointsCalculators

class EventPointsCalculatorSetCommandTest : BaseDataSessionCommandTest<EventPointsCalculatorSetCommand>() {

    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    override fun DirectDI.createCommand() = instance<EventPointsCalculatorSetCommand>()

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
